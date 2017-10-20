package lp.agents;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SwingUtilities;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import lp.behaviors.AccessBehav;
import lp.behaviors.LogIONotifier;
import lp.gui.AccessGUI;
import lp.naai.AccessNAAI;
import lp.struct.Domain;

public class AccessAgent extends Agent implements AccessNAAI
{
	private AccessGUI gui;
	private AccessBehav behaviors;

	@Override
	protected void setup()
	{
		System.out.println("I am: "+getAID().getLocalName());
		try
		{
			EventQueue.invokeAndWait(() -> this.gui = new AccessGUI(this));
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}

		this.behaviors = new AccessBehav(this, gui);

		this.addBehaviour(new OpenApp());
		this.addBehaviour(new SubscribeStop());

		//register in df
		DFAgentDescription accessD = new DFAgentDescription();
		accessD.setName(this.getAID());
		ServiceDescription identity = new ServiceDescription();
		identity.setName("access");
		identity.setType("access");
		accessD.addServices(identity);
		ServiceDescription subscribeS = new ServiceDescription();
		subscribeS.setName("subscribe");
		subscribeS.setType("subscribe");
		accessD.addServices(subscribeS);
		ServiceDescription loginS = new ServiceDescription();
		loginS.setName("login");
		loginS.setType("login");
		accessD.addServices(loginS);
		try
		{
			DFService.register(this, accessD);
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void subscribe(String[] subscribeArgs)
	{
		this.addBehaviour(new Subscribe(subscribeArgs));
	}

	@Override
	public void login(String username, String password)
	{
		this.addBehaviour(new Login(username, password));
	}

	/**/
	public class OpenApp extends Behaviour
	{
		private int state;
		private boolean end;
		//String: domain name, Map: String: style name, Integer: nbrStuds
		private Map<String, Map<String, Integer>> domsStylesMap;
		private Map<String, DFAgentDescription[]> domsAIDsMap;
		private int AIDsCount;

		public OpenApp()
		{
			this.state = 3;
			this.end = false;
			this.domsStylesMap = new TreeMap<>();
			this.domsAIDsMap = new TreeMap<>();
			this.AIDsCount = 0;
		}

		@Override
		public void action()
		{
			switch (this.state)
			{
			case 0: // find the domains service, request domains
				DFAgentDescription domDesc = new DFAgentDescription();
				ServiceDescription domServ = new ServiceDescription();
				domServ.setName("domains");
				domDesc.addServices(domServ);

				try
				{
					DFAgentDescription[] result = DFService.search(myAgent, domDesc);
					if (result.length != 0)
					{
						ACLMessage doms = new ACLMessage(ACLMessage.REQUEST);
						doms.addReceiver(result[0].getName());
						doms.setConversationId("domains");
						myAgent.send(doms);
						state = 1;
					}
					else // no service found
					{
						end = true;
					}
				}
				catch (FIPAException fe)
				{
					fe.printStackTrace();
				}

				break;

			case 1: // receive domains, demand all students therein (AIDs)
				MessageTemplate temp = MessageTemplate.MatchConversationId("domains");
				ACLMessage doms = myAgent.receive(temp);

				if (doms != null)
				{
					if (doms.getPerformative() == ACLMessage.PROPOSE)
					{
						Iterator it = doms.getEnvelope().getAllProperties();
						List<Domain> domains = (List<Domain>) it.next();
						ACLMessage styleReq = new ACLMessage(ACLMessage.REQUEST);
						styleReq.setConversationId("get-style");

						domains.forEach(d -> {
							domsStylesMap.put(d.getName(), new TreeMap<>());
							//get all students therein, place them as receivers
							DFAgentDescription dfd = new DFAgentDescription();
							ServiceDescription sd = new ServiceDescription();
							sd.setName("stud-dom");
							sd.setType(d.getName());
							dfd.addServices(sd);

							try
							{
								DFAgentDescription[] studs = DFService.search(myAgent, dfd);
								domsAIDsMap.put(d.getName(), studs);
								Arrays.asList(studs).forEach(stud -> styleReq.addReceiver(stud.getName()));
							}
							catch (FIPAException fe)
							{
								fe.printStackTrace();
								end = true;
							}
						});

						// if nbrReceivers = 0, no need to send anything, jump to 3 immediately
						if (! styleReq.getAllReceiver().hasNext())
						{
							state = 3;
						}
						else // at least 1 receiver, we send
						{
							domsAIDsMap.forEach((d, dfa) -> AIDsCount += dfa.length);
							myAgent.send(styleReq);
							state = 2;
						}
					}
					else // failure, no domain found
					{
						end = true;
					}
				}
				else
				{
					block();
				}
				break;

			case 2: // receive styles
				MessageTemplate fltr = MessageTemplate.MatchConversationId("get-style");
				ACLMessage styleMsg = myAgent.receive(fltr);

				if (styleMsg != null)
				{
					if (styleMsg.getPerformative() == ACLMessage.PROPOSE)
					{
						String style = styleMsg.getContent();
						AID sender = styleMsg.getSender();
						String senderDomName = domsAIDsMap.keySet().stream().filter(
								key -> Arrays.asList(domsAIDsMap.get(key)).contains(sender)
						).findFirst().get();

						Integer nbrStud = domsStylesMap.get(senderDomName).get(style);
						nbrStud = nbrStud == null ? new Integer(1) : new Integer(nbrStud.intValue() + 1);
						domsStylesMap.get(senderDomName).put(style, nbrStud);
						AIDsCount--;
						if (AIDsCount == 0) // we have received all responses
						{
							state = 3;
						}
					}
					else // performative = failure, i.e. stud has not a style yet
					{

					}
				}
				else
				{
					block();
				}
				break;

			case 3: // make calculations, show the AccessGUI
				MessageTemplate openTemp = MessageTemplate.MatchConversationId("open-app");
				ACLMessage open = myAgent.receive(openTemp);
				if (open != null)
				{
					SwingUtilities.invokeLater(() -> {
						//gui.showStatistics(domsStylesMap);
						gui.setVisible(true);
						gui.revalidate();
					});
					end = true;
					System.out.println(open.getConversationId());
					ACLMessage reply = open.createReply();
					myAgent.send(reply);
				}
				else
				{
					block();
				}
				break;
			}
		}

		@Override
		public boolean done()
		{
			return end;
		}

	}/*------------------ end class OpenApp --------------------*/

	/* The Subscribe Behavior */
	public class Subscribe extends Behaviour
	{
		private String[] subscribeArgs;
		private boolean end;
		private int state;

		public Subscribe(String[] subscribeArgs)
		{
			this.subscribeArgs = subscribeArgs;
			this.end = false;
			this.state = 0;
		}

		@Override
		public void action()
		{
			switch (this.state)
			{
			case 0: //verifying the fields
				String firstName = (String)subscribeArgs[0];
				String lastName = (String)subscribeArgs[1];
				String email = (String)subscribeArgs[2];
				String domain = (String)subscribeArgs[3];

				String[] errs = areValid(firstName, lastName, email, domain);
				if (errs == null) //no error
				{
					DFAgentDescription subsDesc = new DFAgentDescription();
					ServiceDescription subsServ = new ServiceDescription();
					subsServ.setName("add-person");
					subsDesc.addServices(subsServ);

					try
					{
						DFAgentDescription[] offers = DFService.search(myAgent, subsDesc);
						if (offers.length != 0) //we can just choose the first offer
						{
							ACLMessage subscribe = new ACLMessage(ACLMessage.REQUEST);
							subscribe.addReceiver(offers[0].getName());
							subscribe.setConversationId("add-person");
							subscribe.setReplyWith("add-person"+System.currentTimeMillis());
							Envelope e = new Envelope();
							e.addProperties(new Property("subscribeArgs", subscribeArgs));
							subscribe.setEnvelope(e);
							myAgent.send(subscribe);
							state = 1;
						}
						else // no offer has been found (though it is impossible to be the case in our system)
						{
							SwingUtilities.invokeLater(() ->
							gui.showSubscribeErrors(new String[]{"No subscribe service is available"}));
							end = true;
						}
					}
					catch (FIPAException fe)
					{
						fe.printStackTrace();
						SwingUtilities.invokeLater(() ->
						gui.showSubscribeErrors(new String[]{"Unexpected error. Please retry"}));
						end = true;
					}
				}
				else // some fields are wrong
				{
					SwingUtilities.invokeLater(() -> gui.showSubscribeErrors(errs));
					end = true;
				}
				break;

			case 1: //receiving the response
				MessageTemplate temp = MessageTemplate.MatchConversationId("add-person");
				ACLMessage response = myAgent.receive(temp);
				if (response != null)
				{
					if (response.getPerformative() == ACLMessage.INFORM)
					{
						Iterator it = response.getEnvelope().getAllProperties();
						Property usrP = (Property) it.next();
						String username = (String) usrP.getValue();
						Property pwP = (Property) it.next();
						String password = (String) pwP.getValue();
						SwingUtilities.invokeLater(() ->
						gui.showMessage(response.getContent()+" username: "+username+"| password: "+password));
					}
					else
					{
						SwingUtilities.invokeLater(() ->
						gui.showSubscribeErrors(new String[]{ response.getContent() }));
					}
					end = true;
				}
				else
				{
					block();
				}
				break;
			}
		}

		public boolean done()
		{
			return end;
		}

		private String[] areValid(String fn, String ln, String em, String dom)
		{
			String[] errs = {"", "", "", ""};
			int nbrErrs = 0;

			if (fn == null || fn.length() < 2)
			{
				nbrErrs++;
				errs[0] = "First name should not contain less than 2 letters";
			}
			if (ln == null || ln.length() < 2)
			{
				nbrErrs++;
				errs[1] = "Last name should not contain less than 2 letters";
			}
			if (em == null || !em.matches("[a-z][[a-z][0-9]]*[@][a-z]+[.][a-z]+"))
			{
				nbrErrs++;
				errs[2] = "non valid email format";
			}
			if (dom == null)
			{
				nbrErrs++;
				errs[3] = "Domain name should be specified";
			}
			return nbrErrs > 0 ? errs : null;
		}
	} /*---------- end Subscribe Behavior class -----------------*/

	/* The Login Behavior*/
	public class Login extends Behaviour
	{
		private String username;
		private String password;
		private boolean end;
		private int state;

		public Login(String username, String password)
		{
			this.username = username;
			this.password = password;
			this.end = false;
			this.state = 0;
		}

		@Override
		public void action()
		{
			switch (this.state)
			{
			case 0: //verifying the fields
				String[] errs = areValid(username, password);
				if (errs == null) //no error
				{
					DFAgentDescription logDesc = new DFAgentDescription();
					ServiceDescription logServ = new ServiceDescription();
					logServ.setName("log-person");
					logDesc.addServices(logServ);

					try
					{
						DFAgentDescription[] offers = DFService.search(myAgent, logDesc);
						if (offers.length != 0) //we can just choose the first offer
						{
							ACLMessage login = new ACLMessage(ACLMessage.REQUEST);
							login.addReceiver(offers[0].getName());
							login.setConversationId("log-person");
							login.setReplyWith("log-person"+System.currentTimeMillis());
							Envelope e = new Envelope();
							e.addProperties(new Property("username", username));
							e.addProperties(new Property("password", password));
							login.setEnvelope(e);
							myAgent.send(login);
							state = 1;
						}
						else // no offer has been found (though it is impossible to be the case in our system)
						{
							SwingUtilities.invokeLater(() ->
							gui.showLoginErrors(new String[]{"No login service is available"}));
							end = true;
						}
					}
					catch (FIPAException fe)
					{
						fe.printStackTrace();
						SwingUtilities.invokeLater(() ->
						gui.showLoginErrors(new String[]{"Unexpected error. Please retry"}));
						end = true;
					}
				}
				else // some fields are wrong
				{
					SwingUtilities.invokeLater(() -> gui.showLoginErrors(errs));
					end = true;
				}
				break;

			case 1: //receiving the response
				MessageTemplate temp = MessageTemplate.MatchConversationId("log-person");
				ACLMessage response = myAgent.receive(temp);
				if (response != null)
				{
					if (response.getPerformative() == ACLMessage.FAILURE)
					{
						SwingUtilities.invokeLater(() ->
						gui.showLoginErrors(new String[]{response.getContent()}));
					}
					else // login is OK, so AccessAgent should hide GUI
					{
						SwingUtilities.invokeLater(() -> {
							gui.setVisible(false);
							gui.revalidate();
						});
					}
					end = true;
				}
				else
				{
					block();
				}
				break;
			}
		}

		@Override
		public boolean done()
		{
			return end;
		}

		private String[] areValid(String usr, String pw)
		{
			String[] errs = {"", ""};
			int nbrErrs = 0;

			if (usr == null || usr.isEmpty())
			{
				nbrErrs++;
				errs[0] = "Missing user name";
			}
			if (pw == null || pw.isEmpty())
			{
				nbrErrs++;
				errs[1] = "Missing pass word";
			}

			return nbrErrs > 0 ? errs : null;
		}

	} /*---------- end Login Behavior class -----------------*/

	/* Removes the Subscribe behavior when receives the
	 *  DomainAgent request to do that. */
	public class SubscribeStop extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("stop-subscribe");
			ACLMessage stop = myAgent.receive(temp);
			if (stop != null)
			{
				gui.disableSubscribe();
			}
			else
			{
				block();
			}
		}
	}
}
