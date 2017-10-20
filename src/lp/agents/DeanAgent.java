package lp.agents;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.leap.Iterator;
import jade.wrapper.StaleProxyException;
import lp.behaviors.DeanBehav;
import lp.behaviors.LogIONotifier;
import lp.gui.AccessGUI;
import lp.gui.DeanGUI;
import lp.naai.DeanNAAI;
import lp.struct.Aim;
import lp.struct.Chapter;
import lp.struct.Dean;
import lp.struct.Domain;
import lp.struct.DomainResp;
import lp.struct.LSI;
import lp.struct.LSIQuestion;
import lp.struct.LearningStyle;
import lp.struct.Module;
import lp.struct.Person;
import lp.struct.Profile;
import lp.struct.Question;
import lp.struct.Student;
import lp.struct.Teacher;
import lp.struct.Test;
import lp.struct.TestResult;

public class DeanAgent extends Agent implements DeanNAAI
{
	private DeanGUI gui;
	private DeanBehav behaviors;

	private Dean owner;
	private List<Aim> aims;
	private List<Domain> domains;
	private List<Person> members;
	private List<LSI> lsis;

	@Override
	protected void setup()
	{
		System.out.println("I am: "+getAID().getLocalName());

		try
		{
			EventQueue.invokeAndWait(() -> this.gui = new DeanGUI(this));
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}
		this.behaviors = new DeanBehav(this);
		// owner = get from DB
		// domains = bring all domains from DB
		// members = bring all members from DB
		fillMembers();
		fillLSI(); //filling the LSIs

		this.addBehaviour(new LogIONotifier(gui, null));
		this.addBehaviour(new Awakener());
		this.addBehaviour(new AllDomains());
		this.addBehaviour(new NewPerson());
		this.addBehaviour(new LogPerson());
		this.addBehaviour(new LSIProvider());

		//register in df
		DFAgentDescription deanD = new DFAgentDescription();
		deanD.setName(this.getAID());
		ServiceDescription identity = new ServiceDescription();
		identity.setName("dean");
		identity.setType(owner.getUsername());
		deanD.addServices(identity);
		ServiceDescription domS = new ServiceDescription();
		domS.setName("domains");
		domS.setType("domains");
		deanD.addServices(domS);
		ServiceDescription addS = new ServiceDescription();
		addS.setName("add-person");
		addS.setType("add-person");
		deanD.addServices(addS);
		ServiceDescription logS = new ServiceDescription();
		logS.setName("log-person");
		logS.setType("log-person");
		deanD.addServices(logS);
		ServiceDescription lsiS = new ServiceDescription();
		lsiS.setName("lsi");
		lsiS.setType("lsi");
		deanD.addServices(lsiS);

		try
		{
			DFService.register(this, deanD);
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void createDomain(Domain newDomain)
	{
		this.addBehaviour(new NewDomain(newDomain));
	}

	@Override
	public List<Domain> getDomains()
	{
		return this.domains;
	}

	// Just for testing method. To remove in the real system
	private void fillMembers()
	{
		owner = new Dean("Sol", "Bou", "dbs@g.c", "deanusr", "deanpass");
		Person dom = new DomainResp("Sol", "Bou", "dombs@g.c", "domusr", "dompass", new Domain("GL", ""));
		Person teach = new Teacher("Sol", "Bou", "bs@g.c", "tusr", "tpass",
				new Module("SDA", "", 60, new ArrayList<>(), new ArrayList<>(), Module.AVAIL));
		Person teach2 = new Teacher("Sol", "Bou", "bs@g.c", "tusr2", "tpass2",
				new Module("MINF", "", 60, new ArrayList<>(), new ArrayList<>(), Module.AVAIL));
		Person stud = new Student("Sol", "Bou", "bs@g.c", "susr", "spass",
				new Domain("GL", "conception"));
		Person stud2 = new Student("Sol", "Bou", "bs@g.c", "susr2", "spass2",
				new Domain("GL", "conception"));
		Profile p = new Profile();
		p.setRequisites(new ArrayList<>());
		LearningStyle ls = new LearningStyle(25, 18, 33, 24);
		TestResult kr = new TestResult(null, TestResult.LSI, ls);
		p.setKolbResult(kr);
		((Student) stud2).setProfile(p);
		/****/
		Person stud3 = new Student("Sol", "Bou", "bs@g.c", "susr3", "spass3",
				new Domain("GL", "conception"));
		Profile p1 = new Profile();
		p1.setRequisites(new ArrayList<>());
		LearningStyle ls1 = new LearningStyle(23, 33, 18, 26);
		TestResult kr1 = new TestResult(null, TestResult.LSI, ls1);
		p1.setKolbResult(kr1);
		((Student) stud3).setProfile(p1);

		members = new ArrayList<>();
		members.add(owner);
		members.add(dom);
		members.add(teach);
		members.add(teach2);
		members.add(stud);
		members.add(stud2);
		members.add(stud3);

		domains = new ArrayList<>();
		domains.add(new Domain("GL", ""));
		domains.add(new Domain("STIC", ""));
		domains.add(new Domain("SITW", ""));
		domains.add(new Domain("ACAD", ""));
	}

	/* The Behavior that awakens all the other agents */
	public class Awakener extends OneShotBehaviour
	{
		@Override
		public void action()
		{
			//Awaken the AccessAgent
			try
			{
				myAgent.getContainerController().createNewAgent("access",
						AccessAgent.class.getName(), null).start();
			}
			catch (StaleProxyException spe)
			{
				spe.printStackTrace();
			}

			// Awaken the members' agents
			members.stream().filter(m -> m.getRank() == Person.DOM).forEach(m ->
			{
				try
				{
					myAgent.getContainerController().createNewAgent(m.getUsername(),
					DomainAgent.class.getName(), new Object[]{(DomainResp) m}).start();
				}
				catch (StaleProxyException e)
				{
					e.printStackTrace();
				}
			});
			members.stream().filter(m -> m.getRank() == Person.TEACH).forEach(m ->
			{
				try
				{
					myAgent.getContainerController().createNewAgent(m.getUsername(),
					TeacherAgent.class.getName(), new Object[]{(Teacher) m}).start();
				}
				catch (StaleProxyException e)
				{
					e.printStackTrace();
				}
			});
			members.stream().filter(m -> m.getRank() == Person.STUD).forEach(m ->
			{
				try
				{
					myAgent.getContainerController().createNewAgent(m.getUsername(),
					StudentAgent.class.getName(), new Object[]{(Student) m}).start();
				}
				catch (StaleProxyException e)
				{
					e.printStackTrace();
				}
			});
		}
	} /*-------- end class Awakener ----------------------*/

	/* The Behavior that adds a new domain */
	public class NewDomain extends OneShotBehaviour
	{
		private Domain domain;

		public NewDomain(Domain domain)
		{
			this.domain = domain;
		}

		@Override
		public void action()
		{
			// after domain creation, we create/start a new DomainAgent
		}
	} /*-------- end class NewDomain ----------------------*/

	/* the behavior that allows a requester to concult all existing domains */
	public class AllDomains extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("domains");
			ACLMessage allDomains = myAgent.receive(temp);

			if (allDomains != null)
			{
				ACLMessage reply = allDomains.createReply();

				if (domains.isEmpty()) // no available domain
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("No available domain in this system");
				}
				else // at least 1 domain, send the list to requester
				{
					reply.setPerformative(ACLMessage.PROPOSE);
					Envelope e = new Envelope();
					e.addProperties(new Property("domains", domains));
					reply.setEnvelope(e);
					reply.setContent("list of domains");
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
		}
	}/*---------------- end class AllDomains ---------------*/

	/* The behavior that adds a new member when it receives a subscribe request */
	public class NewPerson extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("add-person");
			ACLMessage subscribe = myAgent.receive(temp);
			if (subscribe != null)
			{
				Property args = (Property) subscribe.getEnvelope().getAllProperties().next();
				Object[] subscribeArgs = (Object[]) args.getValue();
				String email = (String) subscribeArgs[2];
				ACLMessage reply = subscribe.createReply();

				boolean duplicated = members.stream().anyMatch(p -> email.equals(p.getEmail()));
				if (duplicated)
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("This email is already used by another member");
				}
				else // email is OK, we generate user&pw, register new student, create a new StudentAgent
				{
					String username = "";
					do { username = randomCode(10); } while (!isUnique(username));
					String domName = (String) subscribeArgs[3];
					Domain studyDomain = domains.stream().filter(d -> domName.equals(d.getName())).findFirst().get();
					Person person = new Student
							((String) subscribeArgs[0],
							 (String) subscribeArgs[1],
							 email, username, randomCode(10), studyDomain);
					members.add(person);

					try
					{
						// StudentAgent creation, we also send him his owner person as an arg
						myAgent.getContainerController().createNewAgent
						(username, StudentAgent.class.getName(), new Object[]{(Student) person}).start();
					}
					catch (StaleProxyException e)
					{
						e.printStackTrace();
					}
					// send an informing mail to the student
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("Welcome! You became a member now");
					Envelope e = new Envelope();
					Property usrP = new Property();
					usrP.setName("username");
					usrP.setValue(person.getUsername());
					e.addProperties(usrP);
					Property pwP = new Property();
					pwP.setName("password");
					pwP.setValue(person.getPassword());
					e.addProperties(pwP);
					reply.setEnvelope(e);
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
		}

		private String randomCode(int length)
		{
			String dict = "a0bcd1ef2ghi3jk4lmn5op6qr7st8uvw9xyz";
			String code = "";
			Random r = new Random();
			for (int i = 0; i < length; i++)
			{
				code += dict.charAt(r.nextInt(dict.length()));
			}
			return code;
		}

		/* True if the code is not found in member's username */
		private boolean isUnique(String code)
		{
			return !members.stream().anyMatch(p -> code.equals(p.getUsername()));
		}
	} /*--------------- end class NewPerson -------------*/

	/* The behavior that checks the member when it receives a login request */
	public class LogPerson extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("log-person");
			ACLMessage login = myAgent.receive(temp);
			if (login != null)
			{
				Iterator it = login.getEnvelope().getAllProperties();
				String username = (String) (((Property) it.next()).getValue());
				String password = (String) (((Property) it.next()).getValue());
				ACLMessage reply = login.createReply();

				Optional<Person> op = members.stream().filter(p -> username.equals(p.getUsername()) && password.equals(p.getPassword())).findFirst();
				if (op.isPresent()) // valid user
				{
					reply.setPerformative(ACLMessage.INFORM);
					reply.setContent("login ok");

					// notify the agent of this student by his presence
					DFAgentDescription dfd = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					//sd.setName("student");
					sd.setType(username);
					dfd.addServices(sd);

					try
					{
						DFAgentDescription[] result = DFService.search(myAgent, dfd);
						if (result.length != 0) // user found
						{
							System.out.println("stud AID = "+result[0].getName());
							ACLMessage yourOwnerHere = new ACLMessage(ACLMessage.INFORM);
							yourOwnerHere.addReceiver(result[0].getName());
							yourOwnerHere.setConversationId("login");
							myAgent.send(yourOwnerHere);
						}
						else // though impossible because we have already ensured the validity
						{
							System.out.println("this is the impossible else!");
						}
					}
					catch (FIPAException fe)
					{
						fe.printStackTrace();
					}
				}
				else // invalid user or pw
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("Invalid login arguments");
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
		}
	} /*--------------- end class LogPerson -------------*/

	/**/
	public class LSIProvider extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("get-lsi");
			ACLMessage lsiReq = myAgent.receive(temp);

			if (lsiReq != null)
			{
				ACLMessage reply = lsiReq.createReply();

				if (lsis.isEmpty()) // no available module
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("No available LSI");
				}
				else // lsi 1 at least
				{
					LSI lsi = lsis.get(new Random().nextInt(lsis.size()));

					reply.setPerformative(ACLMessage.INFORM);
					Envelope e = new Envelope();
					e.addProperties(new Property("lsi", lsi));
					reply.setEnvelope(e);
					reply.setContent("LSI");
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
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

	/* Just for testing */
	private void fillLSI()
	{
		List<String> styles = new ArrayList<>();
		styles.add("concrete");
		styles.add("reflective");
		styles.add("abstract");
		styles.add("active");

		List<String> chs = new ArrayList<>();
		chs.add("I like to see how I feel about it first");
		chs.add("I like to just start");
		chs.add("I like to think about why");
		chs.add("I like to watch and listen before I do it");
		LSIQuestion q1 = new LSIQuestion(1, 1, Question.QCM, "when I need to learn?", chs, styles, null);

		List<String> chs1 = new ArrayList<>();
		chs1.add("I just trust my hunches and feeling");
		chs1.add("I work hard to get things done");
		chs1.add("I rely on logical thinking");
		chs1.add("I listen and watch carefully");
		LSIQuestion q2 = new LSIQuestion(1, 1, Question.QCM, "I learn best when?", chs1, styles, null);

		List<String> chs2 = new ArrayList<>();
		chs2.add("I have feeling and reactions");
		chs2.add("I am usually the one responsible");
		chs2.add("I tend to reason things out first");
		chs2.add("I am quiet and reserved until comfortable");
		LSIQuestion q3 = new LSIQuestion(1, 1, Question.QCM, "When I am learning", chs2, styles, null);

		List<String> chs3 = new ArrayList<>();
		chs3.add("Feeling");
		chs3.add("Doing");
		chs3.add("Thinking");
		chs3.add("Watching");
		LSIQuestion q4 = new LSIQuestion(1, 1, Question.QCM, "I learn by", chs3, styles, null);

		List<LSIQuestion> questions = new ArrayList<>();
		questions.add(q1);
		questions.add(q2);
		questions.add(q3);
		questions.add(q4);
		lsis = new ArrayList<>();
		lsis.add(new LSI(questions));
	}
}
