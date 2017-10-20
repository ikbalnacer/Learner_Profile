package lp.agents;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
import lp.behaviors.LogIONotifier;
import lp.behaviors.StudentBehav;
import lp.gui.CourseView;
import lp.gui.ProfileInitiator;
import lp.gui.StudentGUI;
import lp.naai.StudentNAAI;
import lp.struct.Chapter;
import lp.struct.Competence;
import lp.struct.LSI;
import lp.struct.LearningStyle;
import lp.struct.Module;
import lp.struct.Profile;
import lp.struct.Student;
import lp.struct.Test;
import lp.struct.TestResult;

public class StudentAgent extends Agent implements StudentNAAI
{
	private StudentGUI gui;
	private StudentBehav behaviors;
	private Student owner;
	private TestResult result;
    private TreeMap<String,TestResult> map = new TreeMap<String,TestResult>();
	@Override
	protected void setup()
	{
		System.out.println("I am: "+getAID().getLocalName());	
		try
		{
			EventQueue.invokeAndWait(() -> this.gui = new StudentGUI(this));
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}

		this.behaviors = new StudentBehav(this);
		this.owner = (Student) this.getArguments()[0];
		// get Profile from DB and set it in the owner
		List<Behaviour> toDo = null;
		if (owner.getProfile() == null) // a new student, show him the initiator GUI
		{
			gui.setCourseAccessibility(false); // he is a new student, no right to study before init profile
			ProfileInit init = new ProfileInit();
			toDo = new ArrayList<>();
			toDo.add(init);
		}
		System.out.println("GUI is null: "+(gui == null));
		this.addBehaviour(new LogIONotifier(gui, toDo));
		this.addBehaviour(new ProfileInformer());
		this.addBehaviour(new Study());
		this.addBehaviour(new TestReceiver());

		// register in DF, services are:
		// s1 = name: "student" type: owner.getUsername()
		// s2 = name: "stud-dom" type: owner.getStudyDomain().getName()
		
		DFAgentDescription studD = new DFAgentDescription();
		studD.setName(this.getAID());
		ServiceDescription identity = new ServiceDescription();
		identity.setName("student");
		identity.setType(owner.getUsername());
		studD.addServices(identity);
		ServiceDescription domS = new ServiceDescription();
		domS.setName("stud-dom");
		domS.setType(owner.getStudyDomain().getName());
		studD.addServices(domS);

		try
		{
			DFService.register(this, studD);
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	/* Allows the student to follow his courses */
	public class Study extends CyclicBehaviour
	{
		private CourseView cv;
		private int state;

		public Study()
		{
			this.state = 0;
		}

		@Override
		public void action()
		{
			switch (state)
			{
			case 0: // demanding modules OR showing chapters
				MessageTemplate chapterTemp = MessageTemplate.MatchConversationId("get-chapters");
				MessageTemplate studyTemp = MessageTemplate.MatchConversationId("study");
				MessageTemplate temp = MessageTemplate.or(chapterTemp, studyTemp);
				ACLMessage msg = myAgent.receive(temp);
				if (msg != null)
				{
					if ("study".equals(msg.getConversationId()))
					{
						System.out.println("I am 'study' message!");
						//ask modules
						DFAgentDescription dfdMod = new DFAgentDescription();
						ServiceDescription sdMod = new ServiceDescription();
						ServiceDescription domain = new ServiceDescription();
						sdMod.setName("modules");
						sdMod.setType("modules");
						domain.setName("domain");
						domain.setType(owner.getStudyDomain().getName());
						dfdMod.addServices(sdMod);
						dfdMod.addServices(domain);

						try
						{
							DFAgentDescription[] result = DFService.search(myAgent, dfdMod);
							if (result.length != 0) // responsible found
							{
								ACLMessage getMods = new ACLMessage(ACLMessage.REQUEST);
								getMods.addReceiver(result[0].getName());
								getMods.setConversationId("modules");
								myAgent.send(getMods);
								state = 1;
							}
							else // though impossible because we have already ensured the validity
							{
								System.out.println("else = invalid user");
								System.out.println("this is the impossible else!");
							}
						}
						catch (FIPAException fe)
						{
							fe.printStackTrace();
						}
					}
					else // id = get-chapters
					{
						
						Property pMod = (Property) msg.getEnvelope().getAllProperties().next();
						Module module = (Module) pMod.getValue();
						// find a teacher educating selected module & send him a message
						//ask chapters
						DFAgentDescription dfdChap = new DFAgentDescription();
						ServiceDescription identity = new ServiceDescription();
						ServiceDescription teachMod = new ServiceDescription();
						identity.setName("teacher");
						teachMod.setName("teacher-mod");
						teachMod.setType(module.getTitle());
						dfdChap.addServices(identity);
						dfdChap.addServices(teachMod);

						try
						{
							DFAgentDescription[] result = DFService.search(myAgent, dfdChap);
							if (result.length != 0) // teacher found
							{
								ACLMessage getChapters = new ACLMessage(ACLMessage.REQUEST);
								getChapters.addReceiver(result[0].getName());
								getChapters.setConversationId("chapters");
								myAgent.send(getChapters);
								state = 2;
							}
							else // though impossible because we have already ensured the validity
							{
								System.out.println("else = invalid user");
								System.out.println("this is the impossible else!");
							}
						}
						catch (FIPAException fe)
						{
							fe.printStackTrace();
						}
					}
				}
				else
				{
					block();
				}

				break;

			case 1: // receiving modules & filtering them
				MessageTemplate tempMod = MessageTemplate.MatchConversationId("modules");
				ACLMessage modMsg = myAgent.receive(tempMod);

				if (modMsg != null)
				{
					Property pMod = (Property) modMsg.getEnvelope().getAllProperties().next();
					List<Module> modules = (List<Module>) pMod.getValue();
					if (modules == null || modules.isEmpty())
					{
						SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
								null,
								"No module in this domain",
								"no module",
								JOptionPane.INFORMATION_MESSAGE));
					}
					else //
					{
						List<Module> allowedMods = allwedMods(modules);
						if (allowedMods == null || allowedMods.isEmpty())
						{
							SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(
									null,
									"None from the domain's modules is available for you",
									"Acquired or lack pre-requisites",
									JOptionPane.INFORMATION_MESSAGE));
						}
						else
						{
							//create GUI(allowedMods)
							EventQueue.invokeLater(() -> cv = new CourseView(
							(StudentNAAI) ((StudentAgent) myAgent), allowedMods));
						}
					}
					state = 0;
				}
				else
				{
					block();
				}
				break;

			case 2: // receiving the chapters & filtering them & show filtered
				MessageTemplate tempChap = MessageTemplate.MatchConversationId("chapters");
				ACLMessage chapMsg = myAgent.receive(tempChap);

				if (chapMsg != null)
				{
					Property pChap = (Property) chapMsg.getEnvelope().getAllProperties().next();
					List<Chapter> allowedChapters = allwedChapters((List<Chapter>) pChap.getValue());
					//show chapters in GUI(allowedMods)
					SwingUtilities.invokeLater(() -> cv.showChapters(allowedChapters));
					state = 0;
				}
				else
				{
					block();
				}
				break;
			}
		}

		private List<Module> allwedMods(List<Module> modules)
		{
			return modules.stream().filter(m -> owner.getProfile().qualified(m) &&
					! owner.getProfile().acquired(m)).collect(Collectors.toList());
		}

		private List<Chapter> allwedChapters(List<Chapter> chapters)
		{
			List<Chapter> allowed = /*new ArrayList<>();*/chapters;
			return allowed;
		}
	} /*---------------------- end class Study ----------------*/

	/* This behavior provides a requester by a piece of data in the profile.
	 *  The purposes of those requests are generally for statistics and
	 *  for deducing some decisions. */
	public class ProfileInformer extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			Profile profile = owner.getProfile();
			/* For the moment, the only thing requested is the style. Later, it
			 * will be another infos, so the template will change to the
			 * method .or(,) */
			MessageTemplate temp = MessageTemplate.MatchConversationId("get-style");
			ACLMessage styleReq = myAgent.receive(temp);

			if (styleReq != null)
			{
				ACLMessage reply = styleReq.createReply();
				if (profile == null || profile.getKolbResult() == null)
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("No style yet");
				}
				else
				{
					reply.setPerformative(ACLMessage.PROPOSE);
					Envelope e = new Envelope();
					e.addProperties(new Property("style", profile.getKolbResult()));
					reply.setEnvelope(e);
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
		}

	} /*----------- end class ProfileInformer -------------------*/

	/* demands the competences' list from responsible.
	 *  demands Kolb's LSI from dean.
	 *  shows those to student.
	 *  calculates Kolb's result, stores it with selected competences. */
	public class ProfileInit extends Behaviour
	{
		private LSI lsi;
		private List<Module> modules;
		private int state;
		private boolean end;

		public ProfileInit()
		{
			this.end = false;
			this.state = 0;
			this.lsi = null;
			this.modules = null;
		}

		@Override
		public void action()
		{
			System.out.println("ProfileInit action here = "+state);
			switch (state)
			{
			case 0: //ask the Kolb's test and modules

				// ask LSI
				DFAgentDescription dfd = new DFAgentDescription();
				ServiceDescription sd = new ServiceDescription();
				sd.setName("lsi");
				sd.setType("lsi");
				dfd.addServices(sd);

				try
				{
					DFAgentDescription[] result = DFService.search(myAgent, dfd);
					if (result.length != 0) // dean found
					{
						ACLMessage getLSI = new ACLMessage(ACLMessage.REQUEST);
						getLSI.addReceiver(result[0].getName());
						getLSI.setConversationId("get-lsi");
						myAgent.send(getLSI);
					}
					else // though impossible because we have already ensured the validity
					{
						System.out.println("else = invalid user getLSI");
						System.out.println("this is the impossible else getLSI!");
					}
				}
				catch (FIPAException fe)
				{
					fe.printStackTrace();
				}

				//ask modules
				DFAgentDescription dfdMod = new DFAgentDescription();
				ServiceDescription sdMod = new ServiceDescription();
				ServiceDescription domain = new ServiceDescription();
				sdMod.setName("modules");
				sdMod.setType("modules");
				domain.setName("domain");
				domain.setType(owner.getStudyDomain().getName());
				dfdMod.addServices(sdMod);
				dfdMod.addServices(domain);

				try
				{
					DFAgentDescription[] result = DFService.search(myAgent, dfdMod);
					if (result.length != 0) // responsible found
					{
						ACLMessage getMods = new ACLMessage(ACLMessage.REQUEST);
						getMods.addReceiver(result[0].getName());
						getMods.setConversationId("modules");
						myAgent.send(getMods);
					}
					else // though impossible because we have already ensured the validity
					{
						System.out.println("else = invalid user");
						System.out.println("this is the impossible else!");
					}
				}
				catch (FIPAException fe)
				{
					fe.printStackTrace();
				}
				state = 1;

				break;

			case 1: //receive the LSI
				MessageTemplate tempLSI = MessageTemplate.MatchConversationId("get-lsi");
				ACLMessage lsiMsg = myAgent.receive(tempLSI);
				if (lsiMsg != null)
				{
					Property pLSI = (Property) lsiMsg.getEnvelope().getAllProperties().next();
					lsi = (LSI) pLSI.getValue();
					state = 4;
				}
				else
				{
					block();
				}

				break;

			case 4: //receive the Modules
				MessageTemplate tempMod = MessageTemplate.MatchConversationId("modules");
				ACLMessage modMsg = myAgent.receive(tempMod);

				if (modMsg != null)
				{
					Property pMod = (Property) modMsg.getEnvelope().getAllProperties().next();
					modules = (List<Module>) pMod.getValue();
					state = 2;
				}
				else
				{
					block();
				}

				break;

			case 2: //show the GUI to allow the student to initiate his profile
				EventQueue.invokeLater(() ->
				new ProfileInitiator((StudentNAAI) ((StudentAgent) myAgent), lsi, modules));
				state = 3;
				break;

			case 3: // waiting for the student's responses
				MessageTemplate initPTemp = MessageTemplate.MatchConversationId("init-profile");
				ACLMessage initReq = myAgent.receive(initPTemp);
				if (initReq != null)
				{
					Iterator it = initReq.getEnvelope().getAllProperties();
					List<Module> competences = (List<Module>) ((Property) it.next()).getValue();
					Map<String, Double> bacMarks = (Map<String, Double>) ((Property) it.next()).getValue();
					LSI lsiResponses = (LSI) ((Property) it.next()).getValue();

					Profile profile = new Profile();
					List<Competence> comp = new ArrayList<>();
					competences.forEach(mod -> {
						if (mod.getObjectives() != null)
						{
							comp.addAll(mod.getObjectives());
						}
					});
					TestResult kolbResult = LSIResult(lsiResponses);
					profile.setKolbResult(kolbResult);
					profile.setRequisites(comp);
					profile.setBacMarks(bacMarks);
					owner.setProfile(profile);
					gui.setCourseAccessibility(true);
					end = true;
					System.out.println("Profile has been initiated:");
					System.out.println(profile.getKolbResult().getResultPerc().getAbstractPerc());
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

		private TestResult LSIResult(LSI lsiResponses)
		{
			LearningStyle ls = new LearningStyle(0, 0, 0, 0);
			lsiResponses.getQuestions().forEach(lsiQ -> {
				// 4 is the greatest value of preference, so, the style
				// with 4 gets the point.
				String style = lsiQ.getStyles().get(lsiQ.getValues().indexOf(4));
				switch (style)
				{
				case "concrete":
					ls.setConcretePerc(ls.getConcretePerc() + 1);
					break;

				case "reflective":
					ls.setReflectivePerc(ls.getReflectivePerc() + 1);
					break;

				case "abstract":
					ls.setAbstractPerc(ls.getAbstractPerc() + 1);
					break;

				case "active":
					ls.setActivePerc(ls.getActivePerc() + 1);
					break;
				}
			});

			ls.setConcretePerc(100*ls.getConcretePerc() / lsiResponses.getQuestions().size());
			ls.setReflectivePerc(100*ls.getReflectivePerc() / lsiResponses.getQuestions().size());
			ls.setAbstractPerc(100*ls.getAbstractPerc() / lsiResponses.getQuestions().size());
			ls.setActivePerc(100*ls.getActivePerc() / lsiResponses.getQuestions().size());

			TestResult kolbRes = new TestResult(null, TestResult.LSI, ls);
			return kolbRes;
		}

	} /* ----------------- end class ProfileInit --------------- */

	public class test_requested extends CyclicBehaviour
	{
		private List<Test> tests;
		public test_requested()
		{
			this.tests = new ArrayList<>();
		}
		
		@Override
		public void action()
		{
		
		MessageTemplate testTemp = MessageTemplate.MatchConversationId("Request_test");
	
		ACLMessage msg = myAgent.receive(testTemp);
		
		if (msg != null )
		{
		
		Property args = (Property) msg.getEnvelope().getAllProperties().next();
		Object[] tableau = (Object[]) args.getValue();
		String module_name = (String) tableau[0];
		
		if ( map.get(module_name)!=null) {
			ACLMessage reply=msg.createReply();
			Envelope env = new Envelope();
			env.addProperties(new Property("TestResult",map.get(module_name)));
			reply.setEnvelope(env);
			myAgent.send(reply);
		}
		}else{	
			block();
		}
		}
	} 
	
	public class answers_requested extends CyclicBehaviour
	{
		
		@Override
		public void action()
		{
		
		MessageTemplate testTemp = MessageTemplate.MatchConversationId("responses");
		ACLMessage msg = myAgent.receive(testTemp);
		
		if (msg != null )
		{
			
			ACLMessage reply = msg.createReply();
		    Envelope env = new Envelope();
		    env.addProperties(new Property("owner",this.getAgent().getAID()));
		    env.addProperties(new Property("answer",result));
            reply.setEnvelope(env);
            myAgent.send(reply);
            
		}else{	
			block();
		}
		}
	} 
	
	public class TestReceiver extends CyclicBehaviour
	{
		private List<Test> tests;
		public TestReceiver()
		{
			this.tests = new ArrayList<>();
		}

		@Override
		public void action()
		{
			MessageTemplate testTemp = MessageTemplate.MatchConversationId("test");
			MessageTemplate stopTemp = MessageTemplate.MatchConversationId("stop-test");
			MessageTemplate temp = MessageTemplate.or(testTemp, stopTemp);
			ACLMessage msg = myAgent.receive(temp);
			if (msg != null)
			{
				if ("test".equals(msg.getConversationId()))
				{
					Iterator it = msg.getEnvelope().getAllProperties();
					Property pTest = (Property) it.next();
					Test test = (Test) pTest.getValue();
					tests.add(test);
					Property pMod = (Property) it.next();
					Module module = (Module) pMod.getValue();
					/*if those conditions are not met, the test will be ignored. */
					if (owner.getProfile() != null &&
						 owner.getProfile().qualified(module) &&
						!owner.getProfile().acquired(module))
					{
						/* Show this in the student GUI */
						SwingUtilities.invokeLater(() -> gui.showTestInList(module, test));
					}
				}
				else // id = stop-test
				{

				}
			}
			else
			{
				block();
			}
		}

	} /*-------------------- end class TestReceiver ---------------------*/

	@Override
	public void logout()
	{
		ACLMessage getOut = new ACLMessage(ACLMessage.INFORM);
		getOut.addReceiver(getAID());
		getOut.setConversationId("logout");
		send(getOut);
	}

	@Override
	public void initProfile(List<Module> competences, Map<String, Double> bacMarks, LSI lsiResponses)
	{
		ACLMessage initReq = new ACLMessage(ACLMessage.INFORM);
		initReq.addReceiver(getAID());
		initReq.setConversationId("init-profile");
		Envelope e = new Envelope();
		e.addProperties(new Property("competences", competences));
		e.addProperties(new Property("bacMarks", bacMarks));
		e.addProperties(new Property("lsiResponses", lsiResponses));
		initReq.setEnvelope(e);
		send(initReq);
	}

	@Override
	public void startCourse()
	{
		ACLMessage start = new ACLMessage(ACLMessage.REQUEST);
		start.addReceiver(getAID());
		start.setConversationId("study");
		send(start);
	}

	@Override
	public void getChapters(Module module)
	{
		ACLMessage getCh = new ACLMessage(ACLMessage.REQUEST);
		getCh.addReceiver(getAID());
		getCh.setConversationId("get-chapters");
		Envelope e = new Envelope();
		e.addProperties(new Property("module", module));
		getCh.setEnvelope(e);
		send(getCh);
	}

	@Override
	public void download(Chapter selectedChapter)
	{
		/* This message will be treated by the Behavior 'Study' */
		ACLMessage downCh = new ACLMessage(ACLMessage.REQUEST);
		downCh.addReceiver(getAID());
		downCh.setConversationId("download");
		Envelope e = new Envelope();
		e.addProperties(new Property("chapter", selectedChapter));
		downCh.setEnvelope(e);
		send(downCh);
	}
}
