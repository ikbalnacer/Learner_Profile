package lp.agents;

import java.awt.EventQueue;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import javax.swing.SwingUtilities;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.Property;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import lp.behaviors.LogIONotifier;
import lp.behaviors.TeacherBehav;
import lp.gui.TeacherGUI;
import lp.naai.TeacherNAAI;
import lp.struct.Aim;
import lp.struct.Chapter;
import lp.struct.Domain;
import lp.struct.Exo;
import lp.struct.LearningStyle;
import lp.struct.Module;
import lp.struct.Question;
import lp.struct.Student;
import lp.struct.Teacher;
import lp.struct.Test;
import lp.struct.TestResult;
import lp.struct.XMLHandler;

public class TeacherAgent extends Agent implements TeacherNAAI
{
	private TeacherGUI gui;
	private TeacherBehav behaviors;

	private Teacher owner;
	private Domain domain;
	private String pathXML;
	private List<Chapter> chapters;
	private List<Test> tests;
	/* each entry represents the arithmetic mean of all
	 *  students' results for a specific test. */
	private List<TestResult> globalRsults;
	private TreeMap<AID,TestResult> map = new TreeMap<>();
    private LearningStyle to_reach_aim;
    boolean checked= false;
    private ArrayList<Test> test_response = new ArrayList<Test>();
    
    Aim aim =null;
    LearningStyle gLearningstyle = new LearningStyle();
	@Override
	protected void setup()
	{
		System.out.println("I am: "+getAID().getLocalName());
		System.out.println("platID = "+getAMS().getName());

		pathXML = "agentsXML/"+getAID().getLocalName();
		/* Creating the teacher's XML folder for storing tests. */
		File teachXML = new File(pathXML);
		if (!teachXML.exists())
		{
			teachXML.mkdir();
		}

		try
		{
			EventQueue.invokeAndWait(() -> this.gui = new TeacherGUI(teachXML.getPath(), this));
		}
		catch (InvocationTargetException | InterruptedException e)
		{
			e.printStackTrace();
		}
		this.behaviors = new TeacherBehav(this, gui);
		this.owner = (Teacher) this.getArguments()[0];
		// domain = from DB
		// chapters = get from DB
		// tests = get from DB
		fill();
		fillTests();

		this.addBehaviour(new LogIONotifier(gui, null));
		this.addBehaviour(new ProposeChapter());
		this.addBehaviour(new EvaluateResponse());
		tests.forEach(t -> this.addBehaviour(new PublishTest(this, t.remainingTime(), t)));

		//register in df
		DFAgentDescription teachD = new DFAgentDescription();
		teachD.setName(this.getAID());
		ServiceDescription identity = new ServiceDescription();
		identity.setName("teacher");
		identity.setType(owner.getUsername());
		teachD.addServices(identity);
		ServiceDescription modS = new ServiceDescription();
		modS.setName("teacher-mod");
		modS.setType(owner.getModule().getTitle());
		teachD.addServices(modS);

		try
		{
			DFService.register(this, teachD);
		}
		catch (FIPAException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void createChapter(Chapter newChapter)
	{
		this.addBehaviour(new NewChapter(newChapter));
	}

	//test method, to remove later
	private void fill()
	{
		domain = new Domain("GL", "Software Engineering");
		chapters = new ArrayList<>();
		LocalDateTime startPoint = LocalDateTime.now();

		if ("SDA".equals(owner.getModule().getTitle()))
		{
			chapters.add(new Chapter(1, "The stack", "LIFO & impl", "", "pdf", startPoint));
			chapters.add(new Chapter(2, "The Queue", "FIFO & impl", "", "pdf", startPoint));
			chapters.add(new Chapter(3, "The Tree", "Nodes & Graph & impl", "", "pdf", startPoint));
		}
		else if ("MINF".equals(owner.getModule().getTitle()))
		{
			chapters.add(new Chapter(1, "First order Logic", "propositions & conjunction & disjunction", "", "pdf", startPoint));
			chapters.add(new Chapter(2, "Predicate Logic", "universal & existential", "", "pdf", startPoint));
			chapters.add(new Chapter(3, "Turing Machine", "finite state machines", "", "pdf", startPoint));
		}

		tests = new ArrayList<>();
	}

	private void fillTests()
	{
		List<Question> qus = new ArrayList<>();
		List<String> chs = new ArrayList<>();
		chs.add("choice1");
		chs.add("choice2");
		chs.add("choice3");
		chs.add("choice4");

		Question q11 = new Question(1, 2, Question.DIRECT, "how do you?", null);
		Question q12 = new Question(2, 2, Question.QCM, "where is this?", chs);
		Question q13 = new Question(3, 2, Question.DIRECT, "Ahmed or Mu?", null);

		qus.add(q11);
		qus.add(q12);
		qus.add(q13);

		List<Exo> lex = new ArrayList<>();
		Exo e1 = new Exo(1, null, null, qus);
		Exo e2 = new Exo(2, null, null, qus);
		Exo e3 = new Exo(3, null, null, qus);

		lex.add(e1);
		lex.add(e2);
		lex.add(e3);

		LocalDateTime ldt = LocalDateTime.of(2017, 6, 5, 23, 11);

		Test t = new Test(1, lex, chapters.get(0), null, ldt);
		tests.add(t);
	}

	/* The behavior that adds a new chapter to the course */
	public class NewChapter extends OneShotBehaviour
	{
		private Chapter chapter;

		public NewChapter(Chapter chapter)
		{
			this.chapter = chapter;
		}

		@Override
		public void action()
		{
			// do not forget: if the teacher terminates all chapters
			// before the contract's delay have expired, then remove
			// the ContractMonitor and notify the DomainAgent
			String[] errs = isValid();
			if (errs == null) // we add the chapter to list
			{
				myAgent.addBehaviour(new ChapterAllower((TeacherAgent) myAgent, chapter.remainingTime(), chapter));
				SwingUtilities.invokeLater(() -> gui.showMsg("Chapter added successfully"));
			}
			else // show error
			{
				SwingUtilities.invokeLater(() -> gui.showErrors(errs));
			}
		}

		/* Returns the errors found in this chapter's fields. */
		private String[] isValid()
		{
			List<String> errs = new ArrayList<>();
			return errs.size() == 0 ? null : errs.toArray(new String[errs.size()]);
		}
	} /*--------------- end class NewChapter ------------*/

	/* The behavior that adds a new chapter to the course */
	public class NewTest extends OneShotBehaviour
	{
		private Test test;

		public NewTest(Test test)
		{
			this.test = test;
		}

		@Override
		public void action()
		{
			boolean ok = new XMLHandler(pathXML+"/"+test.getId()+".xml").storeTest(test);
			if (ok)
			{
				SwingUtilities.invokeLater(() -> gui.showMsg("Your test has successfuly been added"));
				tests.add(test);
				myAgent.addBehaviour(new PublishTest((TeacherAgent) myAgent, test.remainingTime(), test));
			}
			else
			{
				SwingUtilities.invokeLater(() -> gui.showErrors(new String[]{"Your test storing has failed"}));
			}
		}
	} /*--------------- end class NewTest ------------*/

	/* The behavior that allows a student to get a chapter(s) */
	public class ProposeChapter extends CyclicBehaviour
	{
		@Override
		public void action()
		{
			MessageTemplate temp = MessageTemplate.MatchConversationId("chapters");
			ACLMessage chaptersMsg = myAgent.receive(temp);

			if (chaptersMsg != null)
			{
				ACLMessage reply = chaptersMsg.createReply();
				if (chapters == null || chapters.isEmpty()) // no available chapter
				{
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("No available chapter in this module");
				}
				else // at least 1 chapter, send the list to requester
				{
					reply.setPerformative(ACLMessage.INFORM);
					Envelope e = new Envelope();
					e.addProperties(new Property("chapters", chapters));
					reply.setEnvelope(e);
					reply.setContent("list of chapters");
				}
				myAgent.send(reply);
			}
			else
			{
				block();
			}
		}
	} /*------------ end class ProposeChapter --------------*/

	/**/
	public class PublishTest extends WakerBehaviour
	{
		private Test test;

		public PublishTest(TeacherAgent agent, long period, Test test)
		{
			super(agent, period);
			this.test = test;
		}

		@Override
		protected void onWake()
		{
			// finding all students in this domain
			DFAgentDescription dfdStud = new DFAgentDescription();
			ServiceDescription sdStud = new ServiceDescription();
			ServiceDescription domainS = new ServiceDescription();
			//sdStud.setName("student");
			domainS.setName("stud-dom");
			domainS.setType(domain.getName());
			System.out.println("from Publish- dom = "+domain.getName());
			dfdStud.addServices(sdStud);
			dfdStud.addServices(domainS);

			try
			{
				DFAgentDescription[] result = DFService.search(myAgent, dfdStud);
				if (result.length != 0) // students found
				{
					ACLMessage publish = new ACLMessage(ACLMessage.INFORM);
					Arrays.asList(result).forEach(r -> publish.addReceiver(r.getName()));
					publish.setConversationId("test");
					Envelope e = new Envelope();
					e.addProperties(new Property("test", test));
					e.addProperties(new Property("module", owner.getModule()));
					publish.setEnvelope(e);
					myAgent.send(publish);
				}
				else // though impossible because we have already ensured the validity
				{
					System.out.println("beh = Publish");
					System.out.println("this is the impossible else!");
				}
			}
			catch (FIPAException fe)
			{
				fe.printStackTrace();
			}
			// a test that stops after 2h
			addBehaviour(new StopTest((TeacherAgent) myAgent, 7200000, test));
		}

	} /*------------------- end class PublishTest ----------------------*/

	/* The behavior that removes a specific test
	 *  due to its duration expiration. */
	public class StopTest extends WakerBehaviour
	{
		private Test test;

		public StopTest(TeacherAgent agent, long period, Test test)
		{
			super(agent, period);
			this.test = test;
		}

		@Override
		protected void onWake()
		{
			// finding all students in this domain
			DFAgentDescription dfdStud = new DFAgentDescription();
			ServiceDescription sdStud = new ServiceDescription();
			ServiceDescription domainS = new ServiceDescription();
			sdStud.setName("student");
			domainS.setName("stud-dom");
			domainS.setType(domain.getName());
			dfdStud.addServices(sdStud);
			dfdStud.addServices(domainS);

			try
			{
				DFAgentDescription[] result = DFService.search(myAgent, dfdStud);
				if (result.length != 0) // students found
				{
					ACLMessage stop = new ACLMessage(ACLMessage.INFORM);
					Arrays.asList(result).forEach(r -> stop.addReceiver(r.getName()));
					stop.setConversationId("stop-test");
					Envelope e = new Envelope();
					e.addProperties(new Property("test", test));
					e.addProperties(new Property("module", owner.getModule()));
					stop.setEnvelope(e);
					myAgent.send(stop);
				}
				else // though impossible because we have already ensured the validity
				{
					System.out.println("beh = Stop");
					System.out.println("this is the impossible else!");
				}
			}
			catch (FIPAException fe)
			{
				fe.printStackTrace();
			}
		}

	} /*------------------- end class StopTest ----------------------*/

	/* Allows the teacher to see and enter the mark of test for a student */
	public class EvaluateResponse extends CyclicBehaviour
	{
            private int state=0;
            int j =0;
			@Override
			public void action()
			{
	             switch(this.state){
	             case 0: 
	            	    gLearningstyle = new LearningStyle();
	            	    to_reach_aim = new LearningStyle();
	            	    DFAgentDescription evaluateD = new DFAgentDescription();
						ServiceDescription evaluateService = new ServiceDescription();
						evaluateService.setName("responses");
						evaluateD.addServices(evaluateService);
					try {
						
						DFAgentDescription[] students = DFService.search(myAgent, evaluateD);
						if (students.length != 0) 
						{
							for (int i = 0; i < students.length; i++) {
							j++;
							ACLMessage request_Toevaluation = new ACLMessage(ACLMessage.REQUEST);
							request_Toevaluation.addReceiver(students[i].getName());
							request_Toevaluation.setConversationId("response_to_evaluate");
							myAgent.send(request_Toevaluation);
							}
						}
						
					} catch (FIPAException e) {
						e.printStackTrace();
					}
					this.state=1;
					
	            	 break;
	             case 1:
	            	MessageTemplate temp = MessageTemplate.MatchConversationId("Answers");
	            	ACLMessage msg = myAgent.receive(temp);
	     			if (msg != null)
	     			{   
	     				if ("answers".equals(msg.getConversationId())) {
	     				j++;
	     				Property proptabl = (Property) msg.getEnvelope().getAllProperties().next();
	     				Object[] proptabl1 = (Object[]) proptabl.getValue();
	     				AID aid = (AID) proptabl1[0];
	     				TestResult result = (TestResult) proptabl1[1];
						map.put(aid, result);
	     				
						if(j==30){
							state=0;
	     				}     		
						
	     				}
	     			}else{
	     				block();
	     			}
	            	 ;break;	 
	             }	
		}
	} /* --------------- end class EvaluateResponse --------------*/

	/* When awakened, it prohibits the teacher from adding
	 *  chapters any more, it informs the DomainAgent. */
	public void caluclate_recomandation(){
		gLearningstyle.setAbstractPerc((gLearningstyle.getAbstractPerc()+aim.getAim().getAbstractPerc())/2);
		gLearningstyle.setReflectivePerc((gLearningstyle.getReflectivePerc()+aim.getAim().getReflectivePerc())/2);
		gLearningstyle.setActivePerc((gLearningstyle.getActivePerc()+aim.getAim().getActivePerc())/2);
		gLearningstyle.setConcretePerc((gLearningstyle.getConcretePerc()+aim.getAim().getConcretePerc())/2);	
	   
		to_reach_aim.setAbstractPerc(gLearningstyle.getAbstractPerc()-aim.getAim().getAbstractPerc());
	    to_reach_aim.setReflectivePerc(gLearningstyle.getReflectivePerc()-aim.getAim().getReflectivePerc());
	    to_reach_aim.setActivePerc(gLearningstyle.getActivePerc()-aim.getAim().getActivePerc());
	    to_reach_aim.setConcretePerc(gLearningstyle.getConcretePerc()-aim.getAim().getConcretePerc());
	
	}
	public class ContractMonitor extends WakerBehaviour
	{
		private Module module;

		public ContractMonitor(TeacherAgent agent, long period, Module module)
		{
			super(agent, period);
			this.module = module;
		}

		@Override
		protected void onWake()
		{
			gui.disableChapters();
			ACLMessage expired = new ACLMessage(ACLMessage.INFORM);

			DFAgentDescription dfd = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setName("domainResp");
			sd.setType(module.getTitle());
			dfd.addServices(sd);

			try
			{
				DFAgentDescription[] result = DFService.search(myAgent, dfd);
				if (result.length != 0)
				{
					expired.addReceiver(result[0].getName());
					expired.setConversationId("contract-end");
					expired.setContent("contract is expired");
					myAgent.send(expired);
				}
				else // no domain agent! impossible
				{

				}
			}
			catch (FIPAException fe)
			{
				fe.printStackTrace();
			}
		}
	}

	/**/
	public class ReviewStudyState extends TickerBehaviour
	{

		public ReviewStudyState(TeacherAgent agent, long period)
		{
			super(agent, period);
		}

		@Override
		protected void onTick()
		{

		}

	} /*---------------------- end class ReviewStudyState ----------*/

	/* Since a chapter has a start point, this behavior
	 *  make a chapter allowed when its date has come. */
	public class ChapterAllower extends WakerBehaviour
	{
		private Chapter newChapter;

		public ChapterAllower(TeacherAgent agent, long period, Chapter newChapter)
		{
			super(agent, period);
			this.newChapter = newChapter;
		}

		@Override
		protected void onWake()
		{
			chapters.add(newChapter);
		}

	} /*---------------------- end class ChapterAllower ----------*/

	@Override
	public void logout()
	{
		ACLMessage getOut = new ACLMessage(ACLMessage.INFORM);
		getOut.addReceiver(getAID());
		getOut.setConversationId("logout");
		send(getOut);
	}

	@Override
	public void createTest(Test newTest)
	{
		this.addBehaviour(new NewTest(newTest));
	}
}
