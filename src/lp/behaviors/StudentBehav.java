package lp.behaviors;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import lp.agents.StudentAgent;
import lp.struct.Profile;

public class StudentBehav
{
	private StudentAgent responsible;

	public StudentBehav(StudentAgent responsible)
	{
		this.responsible = responsible;
	}

//	/* Allows the student to follow his courses */
//	public class Study extends CyclicBehaviour
//	{
//		@Override
//		public void action()
//		{
//
//		}
//
//	} /*---------------------- end class Study ----------------*/
//
//	/* This behavior provides a requester by a piece of data in the profile.
//	 *  The purposes of those requests are generally for statistics and
//	 *  for deducing some decisions. */
//	public class ProfileInformer extends CyclicBehaviour
//	{
//		private Profile profile;
//
//		public ProfileInformer(Profile profile)
//		{
//			this.profile = profile;
//		}
//
//		@Override
//		public void action()
//		{
//			/* For the moment, the only thing requested is the style. Later, it
//			 * will be another infos, so the template will change to the
//			 * method .or(,) */
//			MessageTemplate temp = MessageTemplate.MatchConversationId("get-style");
//			ACLMessage styleReq = myAgent.receive(temp);
//
//			if (styleReq != null)
//			{
//				ACLMessage reply = styleReq.createReply();
//				if (profile.getStyle().isEmpty())
//				{
//					reply.setPerformative(ACLMessage.FAILURE);
//					reply.setContent("No style yet");
//				}
//				else
//				{
//					reply.setPerformative(ACLMessage.PROPOSE);
//					reply.setContent(profile.getStyle());
//				}
//				myAgent.send(reply);
//			}
//			else
//			{
//				block();
//			}
//		}
//
//	} /*----------- end class ProfileInformer -------------------*/
//
//	/* demands the competences' list from responsible.
//	 *  demands Kolb's LSI from dean.
//	 *  shows those to student.
//	 *  calculates Kolb's result, stores it with selected competences. */
//	public class ProfileInit extends Behaviour
//	{
//		private Profile profile;
//		private int state;
//		private boolean end;
//
//		public ProfileInit(Profile profile)
//		{
//			this.profile = profile;
//			this.end = false;
//			this.state = 0;
//		}
//
//		@Override
//		public void action()
//		{
//
//		}
//
//		@Override
//		public boolean done()
//		{
//			return false;
//		}
//
//	} /* ----------------- end class ProfileInit --------------- */
}
