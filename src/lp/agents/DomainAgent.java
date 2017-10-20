package lp.agents;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lp.behaviors.DomainBehav;
import lp.behaviors.LogIONotifier;
import lp.gui.DeanGUI;
import lp.gui.DomainGUI;
import lp.naai.DomainNAAI;
import lp.struct.Aim;
import lp.struct.Competence;
import lp.struct.DomainResp;
import lp.struct.LSI;
import lp.struct.LearningStyle;
import lp.struct.Module;
import lp.struct.TestResult;

public class DomainAgent extends Agent implements DomainNAAI
{
	private DomainGUI gui;
	private DomainBehav behaviors;

	private DomainResp owner;
	private Aim aim;
	private List<Module> modules;
	private List<Competence> domainCompetences;
	private LocalDateTime subscribeDelay;
	/* The arithmetic mean of all students' Kolb's results. */
	private TestResult globalKolbResult;

	@Override
	protected void setup()
	{
		
		System.out.println("I am: "+getAID().getLocalName());

		try
		{
			EventQueue.invokeAndWait(() -> this.gui = new DomainGUI(this));
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}
		this.behaviors = new DomainBehav(this, owner);
		this.owner = (DomainResp) this.getArguments()[0];
		// modules = bring all the domain's modules from the DB
		fill();

		this.addBehaviour(new LogIONotifier(gui, null));
		this.addBehaviour(new AllModules());

		/**//**/setDelay(); // just for test
		long remain = Timestamp.valueOf(subscribeDelay).toInstant().toEpochMilli() - System.currentTimeMillis();
		System.out.println("remain = "+remain);
		if (remain < 0)
		{
			remain = 15000;
		}
		this.addBehaviour(new GlobalKolbLauncher(this, remain));

		//register in df
		DFAgentDescription domD = new DFAgentDescription();
		domD.setName(this.getAID());
		ServiceDescription identity = new ServiceDescription();
		identity.setName("domainResp");
		identity.setType(owner.getUsername());
		domD.addServices(identity);
		ServiceDescription domain = new ServiceDescription();
		domain.setName("domain");
		domain.setType(owner.getDomain().getName());
		domD.addServices(domain);
		ServiceDescription modS = new ServiceDescription();
		modS.setName("modules");
		modS.setType("modules");
		domD.addServices(modS);

		try
		{
			DFService.register(this, domD);
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void createModule(Module newModule)
	{
		this.addBehaviour(new NewModule(newModule));
	}

	/* test method*/
	private void setDelay()
	{
		subscribeDelay = LocalDateTime.of(2017, 6, 6, 12, 41);
	}

	@Override
	public List<Module> getModules()
	{
		return this.modules;
	}

	/* The behavior that adds a new module */
	public class NewModule extends Behaviour
	{
		private Module module;
		private int state;
		private boolean end;

		public NewModule(Module module)
		{
			this.module = module;
			this.state = 0;
			this.end = false;
		}

		@Override
		public void action()
		{
			switch (this.state)
			{
			case 0: // validating the fields to store a new module
				break;
			case 1: // Negotiating to assign a teacher
				break;
			case 2: // waiting for a notification about the assigned teacher's contract
						// whether: course incomplete, remove teacher. Or, complete, allow module for study
				break;
			}
		}

		@Override
		public boolean done()
		{
			return end;
		}
	} /*------------- end class NewModule---------------*/

	/* The behavior that allows the requesters to consult the list of modules */
	public class AllModules extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("modules");
			ACLMessage allModules = myAgent.receive(temp);

			if (allModules != null)
			{
				ACLMessage reply = allModules.createReply();
				List<Module> avail = modules.stream().filter(m -> m.getState() == Module.AVAIL).collect(Collectors.toList());
				if (avail.isEmpty()) // no available module
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("No available module in this domain");
				}
				else // at least 1 module, send the list to requester
				{
					reply.setPerformative(ACLMessage.INFORM);
					Envelope e = new Envelope();
					e.addProperties(new Property("modules", avail));
					reply.setEnvelope(e);
					reply.setContent("list of modules");
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
		}
	} /*------------ end class AllModules -------------------*/

	/*  */
	public class Aim_requested extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("aim");
			ACLMessage msg = myAgent.receive(temp);
            if(msg != null){
            ACLMessage reply = msg.createReply();
            Envelope env = new Envelope();
            env.addProperties(new Property("aim",aim));
            reply.setEnvelope(env);
            myAgent.send(reply);
            }else{
            	block();
            }
			
		
		}
	}
	
	
	public class GlobalKolbLauncher extends WakerBehaviour
	{
		public GlobalKolbLauncher(DomainAgent agent, long period)
		{
			super(agent, period);
		}

		@Override
		protected void onWake()
		{
			myAgent.addBehaviour(new GlobalKolb());
		}
	}

	/*The behavior that stops the subscribe process,
	 *  calculate the global style for the subscribed students
	 *  when the delay has expired (period).*/
	public class GlobalKolb extends Behaviour
	{
		private List<TestResult> studsKolb;
		private int state;
		private boolean end;
		private int AIDsCount;

		public GlobalKolb()
		{
			this.studsKolb = new ArrayList<>();
			this.state = 0;
			this.end = false;
			this.AIDsCount = 0;

			globalKolbResult = new TestResult(null, TestResult.LSI, new LearningStyle(0, 0, 0, 0));
		}

		@Override
		public void action()
		{
			switch (state)
			{
			case 0:
				DFAgentDescription accessDesc = new DFAgentDescription();
				ServiceDescription accessServ = new ServiceDescription();
				accessServ.setName("access");
				accessServ.setType("access");
				accessDesc.addServices(accessServ);

				try
				{
					DFAgentDescription[] result = DFService.search(myAgent, accessDesc);
					if (result.length != 0)
					{
						ACLMessage stop = new ACLMessage(ACLMessage.REQUEST);
						stop.addReceiver(result[0].getName());
						stop.setConversationId("stop-subscribe");
						myAgent.send(stop);

						ACLMessage styleReq = new ACLMessage(ACLMessage.REQUEST);
						styleReq.setConversationId("get-style");
						//get all students in this domain, place them as receivers
						DFAgentDescription dfd = new DFAgentDescription();
						ServiceDescription sd = new ServiceDescription();
						sd.setName("stud-dom");
						sd.setType(owner.getDomain().getName());
						dfd.addServices(sd);

						try
						{
							DFAgentDescription[] studs = DFService.search(myAgent, dfd);
							AIDsCount = studs.length;
							Arrays.asList(studs).forEach(stud -> styleReq.addReceiver(stud.getName()));
						}
						catch (FIPAException fe)
						{
							fe.printStackTrace();
							end = true;
						}
						// if nbrReceivers = 0, no need to send anything
						if (! styleReq.getAllReceiver().hasNext())
						{
							end = true;
						}
						else // at least 1 receiver, we send
						{
							myAgent.send(styleReq);
							state = 1;
						}
					}
					else // no service found (impossible)
					{
						end = true;
					}
				}
				catch (FIPAException fe)
				{
					fe.printStackTrace();
				}
				break;

			case 1: // receive styles
				MessageTemplate fltr = MessageTemplate.MatchConversationId("get-style");
				ACLMessage styleMsg = myAgent.receive(fltr);

				if (styleMsg != null)
				{
					if (styleMsg.getPerformative() == ACLMessage.PROPOSE)
					{
						// In fact, style is not a string but a TestResult
						// we should change that
						Property pRes = (Property) styleMsg.getEnvelope().getAllProperties().next();
						studsKolb.add((TestResult) pRes.getValue());
						AIDsCount--;

						if (AIDsCount == 0) // we have received all responses
						{
							state = 2;
						}
					}
					else // performative = failure, i.e. stud has not a style yet
					{
						AIDsCount--;
					}
				}
				else
				{
					block();
				}
				break;

			case 2: //calculate global kolb


				LearningStyle ls = globalKolbResult.getResultPerc();
				studsKolb.forEach(tr -> {
					ls.setConcretePerc(ls.getConcretePerc() + tr.getResultPerc().getConcretePerc());
					ls.setReflectivePerc(ls.getReflectivePerc() + tr.getResultPerc().getReflectivePerc());
					ls.setAbstractPerc(ls.getAbstractPerc() + tr.getResultPerc().getAbstractPerc());
					ls.setActivePerc(ls.getActivePerc() + tr.getResultPerc().getActivePerc());
				});
				ls.setConcretePerc(ls.getConcretePerc() / studsKolb.size());
				ls.setReflectivePerc(ls.getReflectivePerc() / studsKolb.size());
				ls.setAbstractPerc(ls.getAbstractPerc() / studsKolb.size());
				ls.setActivePerc(ls.getActivePerc() / studsKolb.size());

				 String str ="Conrete : "+globalKolbResult.getResultPerc().getConcretePerc()+"%"+"\n"
				 		+"Reflective : "+ globalKolbResult.getResultPerc().getReflectivePerc()+"%"+"\n"
				        +"Abstract : "+ globalKolbResult.getResultPerc().getAbstractPerc()+"%"+"\n"
				        +"Active : "+globalKolbResult.getResultPerc().getActivePerc()+"%"+"\n";
				
                SwingUtilities.invokeLater(()->{
                	JOptionPane.showMessageDialog(null, str, "Kolb result", JOptionPane.INFORMATION_MESSAGE);
                });
				
				end = true;

				break;
			}
		}

		@Override
		public boolean done()
		{
			return end;
		}
	}

	@Override
	public void logout()
	{
		ACLMessage getOut = new ACLMessage(ACLMessage.INFORM);
		getOut.addReceiver(getAID());
		getOut.setConversationId("logout");
		send(getOut);
	}

	private void fill()
	{
		modules = new ArrayList<>();
		List<Competence> obj = new ArrayList<>();
		obj.add(new Competence("C++", Competence.MODERATE));
		obj.add(new Competence("Algo", Competence.BASIC));
		obj.add(new Competence("DB", Competence.STRONG));

		List<Competence> obj1 = new ArrayList<>();
		obj1.add(new Competence("Routing", Competence.MODERATE));
		List<Competence> obj2 = new ArrayList<>();
		obj2.add(new Competence("Logic Math", Competence.MODERATE));

		modules.add(new Module("SDA", "vv", 70, new ArrayList<>(), obj, Module.AVAIL));
		modules.add(new Module("POOJ", "jj", 60, new ArrayList<>(), obj, Module.AVAIL));
		modules.add(new Module("RES", "jk", 63, new ArrayList<>(), obj1, Module.AVAIL));
		modules.add(new Module("MINF", "lk", 71, new ArrayList<>(), obj2, Module.AVAIL));
	}
}
