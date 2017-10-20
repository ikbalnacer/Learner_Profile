package lp.behaviors;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import lp.agents.AccessAgent;
import lp.agents.DeanAgent;
import lp.agents.DomainAgent;
import lp.agents.StudentAgent;
import lp.agents.TeacherAgent;
import lp.struct.Domain;
import lp.struct.DomainResp;
import lp.struct.LSI;
import lp.struct.Person;
import lp.struct.Student;
import lp.struct.Teacher;

public class DeanBehav
{
	private DeanAgent responsible;

	public DeanBehav(DeanAgent responsible)
	{
		this.responsible = responsible;
	}

//	/* The Behavior that awakens all the other agents */
//	public class Awakener extends OneShotBehaviour
//	{
//		private List<Person> members;
//
//		public Awakener(List<Person> members)
//		{
//			this.members = members;
//		}
//
//		@Override
//		public void action()
//		{
//			//Awaken the AccessAgent
//			try
//			{
//				myAgent.getContainerController().createNewAgent("access",
//						AccessAgent.class.getName(), null).start();
//			}
//			catch (StaleProxyException spe)
//			{
//				spe.printStackTrace();
//			}
//
//			// Awaken the members' agents
//			members.forEach(m ->
//			{
//				try
//				{
//					switch(m.getRank())
//					{
//						case Person.STUD :
//							myAgent.getContainerController().createNewAgent(m.getUsername(),
//							StudentAgent.class.getName(), new Object[]{(Student) m}).start();
//							break;
//
//						case Person.TEACH :
//							myAgent.getContainerController().createNewAgent(m.getUsername(),
//							TeacherAgent.class.getName(), new Object[]{(Teacher) m}).start();
//							break;
//
//						case Person.DOM :
//							myAgent.getContainerController().createNewAgent(m.getUsername(),
//							DomainAgent.class.getName(), new Object[]{(DomainResp) m}).start();
//							break;
//					}
//				}
//				catch (StaleProxyException e)
//				{
//					e.printStackTrace();
//				}
//			});
//		}
//	} /*-------- end class Awakener ----------------------*/
//
//	/* The Behavior that adds a new domain */
//	public class NewDomain extends OneShotBehaviour
//	{
//		private Domain domain;
//
//		public NewDomain(Domain domain)
//		{
//			this.domain = domain;
//		}
//
//		@Override
//		public void action()
//		{
//			// after domain creation, we create/start a new DomainAgent
//		}
//	} /*-------- end class NewDomain ----------------------*/
//
//	/* the behavior that allows a requester to concult all existing domains */
//	public class AllDomains extends CyclicBehaviour
//	{
//		private List<Domain> domains;
//
//		public AllDomains(List<Domain> domains)
//		{
//			this.domains = domains;
//		}
//
//		@Override
//		public void action()
//		{
//			MessageTemplate temp = MessageTemplate.MatchConversationId("domains");
//			ACLMessage allDomains = myAgent.receive(temp);
//
//			if (allDomains != null)
//			{
//				ACLMessage reply = allDomains.createReply();
//
//				if (domains.isEmpty()) // no available domain
//				{
//					reply.setPerformative(ACLMessage.FAILURE);
//					reply.setContent("No available domain in this system");
//				}
//				else // at least 1 domain, send the list to requester
//				{
//					reply.setPerformative(ACLMessage.PROPOSE);
//					Envelope e = new Envelope();
//					e.addProperties(new Property("domains", domains));
//					reply.setEnvelope(e);
//					reply.setContent("list of domains");
//				}
//				myAgent.send(reply);
//			}
//			else
//			{
//				block();
//			}
//		}
//	}/*---------------- end class AllDomains ---------------*/
//
//	/* The behavior that adds a new member when it receives a subscribe request */
//	public class NewPerson extends CyclicBehaviour
//	{
//		private List<Person> members;
//		private List<Domain> domains;
//
//		 public NewPerson(List<Person> members, List<Domain> domains)
//		 {
//			 this.members = members;
//			 this.domains = domains;
//		 }
//
//		@Override
//		public void action()
//		{
//			MessageTemplate temp = MessageTemplate.MatchConversationId("add-person");
//			ACLMessage subscribe = myAgent.receive(temp);
//			if (subscribe != null)
//			{
//				Property args = (Property) subscribe.getEnvelope().getAllProperties().next();
//				Object[] subscribeArgs = (Object[]) args.getValue();
//				String email = (String) subscribeArgs[2];
//				ACLMessage reply = subscribe.createReply();
//
//				boolean duplicated = members.stream().anyMatch(p -> email.equals(p.getEmail()));
//				if (duplicated)
//				{
//					reply.setPerformative(ACLMessage.FAILURE);
//					reply.setContent("This email is already used by another member");
//				}
//				else // email is OK, we generate user&pw, register new student, create a new StudentAgent
//				{
//					String username = "";
//					do { username = randomCode(10); } while (!isUnique(username));
//					String domName = (String) subscribeArgs[3];
//					Domain studyDomain = domains.stream().filter(d -> domName.equals(d.getName())).findFirst().get();
//					Person person = new Student
//							((String) subscribeArgs[0],
//							 (String) subscribeArgs[1],
//							 email, username, randomCode(10), studyDomain);
//					members.add(person);
//
//					try
//					{
//						// StudentAgent creation, we also send him his owner person as an arg
//						myAgent.getContainerController().createNewAgent
//						(username, StudentAgent.class.getName(), new Object[]{(Student) person}).start();
//					}
//					catch (StaleProxyException e)
//					{
//						e.printStackTrace();
//					}
//					// send an informing mail to the student
//					reply.setPerformative(ACLMessage.INFORM);
//					reply.setContent("Welcome! You became a member now");
//					Envelope e = new Envelope();
//					Property usrP = new Property();
//					usrP.setName("username");
//					usrP.setValue(person.getUsername());
//					e.addProperties(usrP);
//					Property pwP = new Property();
//					pwP.setName("password");
//					pwP.setValue(person.getPassword());
//					e.addProperties(pwP);
//					reply.setEnvelope(e);
//				}
//				myAgent.send(reply);
//			}
//			else
//			{
//				block();
//			}
//		}
//
//		private String randomCode(int length)
//		{
//			String dict = "a0bcd1ef2ghi3jk4lmn5op6qr7st8uvw9xyz";
//			String code = "";
//			Random r = new Random();
//			for (int i = 0; i < length; i++)
//			{
//				code += dict.charAt(r.nextInt(dict.length()));
//			}
//			return code;
//		}
//
//		/* True if the code is not found in member's username */
//		private boolean isUnique(String code)
//		{
//			return !members.stream().anyMatch(p -> code.equals(p.getUsername()));
//		}
//	} /*--------------- end class NewPerson -------------*/
//
//	/* The behavior that checks the member when it receives a login request */
//	public class LogPerson extends CyclicBehaviour
//	{
//		private List<Person> members;
//
//		public LogPerson(List<Person> members)
//		{
//			this.members = members;
//		}
//
//		@Override
//		public void action()
//		{
//			MessageTemplate temp = MessageTemplate.MatchConversationId("log-person");
//			ACLMessage login = myAgent.receive(temp);
//			if (login != null)
//			{
//				Iterator it = login.getEnvelope().getAllProperties();
//				String username = (String) it.next();
//				String password = (String) it.next();
//				ACLMessage reply = login.createReply();
//
//				Optional<Person> op = members.stream().filter(p -> username.equals(p.getUsername()) && password.equals(p.getPassword())).findFirst();
//				if (op.isPresent()) // valid user
//				{
//					reply.setPerformative(ACLMessage.INFORM);
//					reply.setContent("login ok");
//
//					// notify the agent of this student by his presence
//					DFAgentDescription dfd = new DFAgentDescription();
//					ServiceDescription sd = new ServiceDescription();
//					sd.setName("student");
//					sd.setType(username);
//					dfd.addServices(sd);
//
//					try
//					{
//						DFAgentDescription[] result = DFService.search(myAgent, dfd);
//						if (result.length != 0) // user found
//						{
//							ACLMessage yourOwnerHere = new ACLMessage(ACLMessage.INFORM);
//							yourOwnerHere.addReceiver(result[0].getName());
//							yourOwnerHere.setConversationId("login");
//							myAgent.send(yourOwnerHere);
//						}
//						else // though impossible because we have already ensured the validity
//						{
//
//						}
//					}
//					catch (FIPAException fe)
//					{
//						fe.printStackTrace();
//					}
//				}
//				else // invalid user or pw
//				{
//					reply.setPerformative(ACLMessage.FAILURE);
//					reply.setContent("Invalid login arguments");
//				}
//				myAgent.send(reply);
//			}
//			else
//			{
//				block();
//			}
//		}
//	} /*--------------- end class LogPerson -------------*/
//
//	/**/
//	public class LSIProvider extends CyclicBehaviour
//	{
//		private List<LSI> lsis;
//
//		public LSIProvider(List<LSI> lsis)
//		{
//			this.lsis = lsis;
//		}
//
//		@Override
//		public void action()
//		{
//
//		}
//	}
}
