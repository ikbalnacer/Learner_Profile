package lp.behaviors;

import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import lp.agents.DomainAgent;
import lp.agents.TeacherAgent;
import lp.gui.TeacherGUI;
import lp.struct.Chapter;
import lp.struct.Module;
import lp.struct.Test;

public class TeacherBehav
{
	private TeacherAgent responsible;
	private TeacherGUI gui;

	public TeacherBehav(TeacherAgent responsible, TeacherGUI gui)
	{
		this.responsible = responsible;
		this.gui = gui;
	}

//	/* The behavior that adds a new chapter to the course */
//	public class NewChapter extends OneShotBehaviour
//	{
//		private Chapter chapter;
//
//		public NewChapter(Chapter chapter)
//		{
//			this.chapter = chapter;
//		}
//		@Override
//		public void action()
//		{
//			// do not forget: if the teacher terminates all chapters
//			// before the contract's delay have expired, then remove
//			// the ContractMonitor and notify the DomainAgent
//		}
//	} /*--------------- end class NewChapter ------------*/
//
//	/* The behavior that adds a new chapter to the course */
//	public class NewTest extends OneShotBehaviour
//	{
//		private Test test;
//
//		public NewTest(Chapter chapter)
//		{
//			this.test = test;
//		}
//		@Override
//		public void action()
//		{
//			// do not forget: if the teacher terminates all chapters
//			// before the contract's delay have expired, then remove
//			// the ContractMonitor and notify the DomainAgent
//		}
//	} /*--------------- end class NewTest ------------*/
//
//	/* The behavior that allows a student to get a chapter(s) */
//	public class ProposeChapter extends CyclicBehaviour
//	{
//		@Override
//		public void action()
//		{
//
//		}
//	} /*------------ end class ProposeChapter --------------*/
//
//	/**/
//	public class PublishTest extends WakerBehaviour
//	{
//		public PublishTest(TeacherAgent agent, long period)
//		{
//			super(agent, period);
//		}
//
//		@Override
//		protected void onWake()
//		{
//			// do whatever you intend to publish the test,
//
//			// and then be ready to receive the responses
//			responsible.addBehaviour(new EvaluateResponse());
//		}
//
//	} /*------------------- end class PublishTest ----------------------*/
//
//	/* Allows the teacher to see and enter the mark of test for a student */
//	public class EvaluateResponse extends CyclicBehaviour
//	{
//		@Override
//		public void action()
//		{
//
//		}
//	} /* --------------- end class EvaluateResponse --------------*/
//
//	/* When awakened, it prohibits the teacher from adding
//	 *  chapters any more, it informs the DomainAgent. */
//	public class ContractMonitor extends WakerBehaviour
//	{
//		private Module module;
//
//		public ContractMonitor(TeacherAgent agent, long period, Module module)
//		{
//			super(agent, period);
//			this.module = module;
//		}
//
//		@Override
//		protected void onWake()
//		{
//			gui.disableChapters();
//			ACLMessage expired = new ACLMessage(ACLMessage.INFORM);
//
//			DFAgentDescription dfd = new DFAgentDescription();
//			ServiceDescription sd = new ServiceDescription();
//			sd.setName("domainResp");
//			sd.setType(module.getTitle());
//			dfd.addServices(sd);
//
//			try
//			{
//				DFAgentDescription[] result = DFService.search(myAgent, dfd);
//				if (result.length != 0)
//				{
//					expired.addReceiver(result[0].getName());
//					expired.setConversationId("contract-end");
//					expired.setContent("contract is expired");
//					myAgent.send(expired);
//				}
//				else // no domain agent! impossible
//				{
//
//				}
//			}
//			catch (FIPAException fe)
//			{
//				fe.printStackTrace();
//			}
//		}
//	}
//
//	/**/
//	public class ReviewStudyState extends TickerBehaviour
//	{
//
//		public ReviewStudyState(DomainAgent agent, long period)
//		{
//			super(agent, period);
//		}
//
//		@Override
//		protected void onTick()
//		{
//
//		}
//
//	} /*---------------------- end class ReviewStudyState ----------*/
}
