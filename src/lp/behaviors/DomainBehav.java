package lp.behaviors;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
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
import lp.agents.DomainAgent;
import lp.struct.DomainResp;
import lp.struct.LearningStyle;
import lp.struct.Module;
import lp.struct.TestResult;

public class DomainBehav
{
	private DomainAgent responsible;
	private DomainResp owner;

	public DomainBehav(DomainAgent responsible, DomainResp owner)
	{
		this.responsible = responsible;
		this.owner = owner;
	}

//	/* The behavior that adds a new module */
//	public class NewModule extends Behaviour
//	{
//		private Module module;
//		private int state;
//		private boolean end;
//
//		public NewModule(Module module)
//		{
//			this.module = module;
//			this.state = 0;
//			this.end = false;
//		}
//
//		@Override
//		public void action()
//		{
//			switch (this.state)
//			{
//			case 0: // validating the fields to store a new module
//				break;
//			case 1: // Negotiating to assign a teacher
//				break;
//			case 2: // waiting for a notification about the assigned teacher's contract
//						// whether: course incomplete, remove teacher. Or, complete, allow module for study
//				break;
//			}
//		}
//
//		@Override
//		public boolean done()
//		{
//			return end;
//		}
//	} /*------------- end class NewModule---------------*/
//
//	/* The behavior that allows the requesters to consult the list of modules */
//	public class AllModules extends CyclicBehaviour
//	{
//		private List<Module> modules;
//
//		public AllModules(List<Module> modules)
//		{
//			this.modules = modules;
//		}
//
//		@Override
//		public void action()
//		{
//			MessageTemplate temp = MessageTemplate.MatchConversationId("modules");
//			ACLMessage allModules = myAgent.receive(temp);
//
//			if (allModules != null)
//			{
//				ACLMessage reply = allModules.createReply();
//				List<Module> avail = modules.stream().filter(m -> m.getState() == Module.AVAIL).collect(Collectors.toList());
//				if (avail.isEmpty()) // no available module
//				{
//					reply.setPerformative(ACLMessage.FAILURE);
//					reply.setContent("No available module in this domain");
//				}
//				else // at least 1 module, send the list to requester
//				{
//					reply.setPerformative(ACLMessage.INFORM);
//					Envelope e = new Envelope();
//					e.addProperties(new Property("modules", avail));
//					reply.setEnvelope(e);
//					reply.setContent("list of modules");
//				}
//				myAgent.send(reply);
//			}
//			else
//			{
//				block();
//			}
//		}
//	} /*------------ end class AllModules -------------------*/
//
//	/*  */
//	public class GlobalKolbLauncher extends WakerBehaviour
//	{
//		private TestResult globalKolbResult;
//
//		public GlobalKolbLauncher(DomainAgent agent, long period, TestResult globalKolbResult)
//		{
//			super(agent, period);
//			this.globalKolbResult = globalKolbResult;
//		}
//
//		@Override
//		protected void onWake()
//		{
//			myAgent.addBehaviour(new GlobalKolb(globalKolbResult));
//		}
//	}
//
//	/*The behavior that stops the subscribe process,
//	 *  calculate the global style for the subscribed students
//	 *  when the delay has expired (period).*/
//	public class GlobalKolb extends Behaviour
//	{
//		private TestResult globalKolbResult;
//		private List<TestResult> studsKolb;
//		private int state;
//		private boolean end;
//		private int AIDsCount;
//
//		public GlobalKolb(TestResult globalKolbResult)
//		{
//			this.globalKolbResult = globalKolbResult;
//			this.state = 0;
//			this.end = false;
//			this.AIDsCount = 0;
//		}
//
//		@Override
//		public void action()
//		{
//			switch (state)
//			{
//			case 0:
//				DFAgentDescription accessDesc = new DFAgentDescription();
//				ServiceDescription accessServ = new ServiceDescription();
//				accessServ.setName("access");
//				accessServ.setType("access");
//				accessDesc.addServices(accessServ);
//
//				try
//				{
//					DFAgentDescription[] result = DFService.search(myAgent, accessDesc);
//					if (result.length != 0)
//					{
//						ACLMessage stop = new ACLMessage(ACLMessage.REQUEST);
//						stop.addReceiver(result[0].getName());
//						stop.setConversationId("stop-subscribe");
//						myAgent.send(stop);
//
//						ACLMessage styleReq = new ACLMessage(ACLMessage.REQUEST);
//						styleReq.setConversationId("get-style");
//						//get all students in this domain, place them as receivers
//						DFAgentDescription dfd = new DFAgentDescription();
//						ServiceDescription sd = new ServiceDescription();
//						sd.setName("stud-dom");
//						sd.setType(owner.getDomain().getName());
//						dfd.addServices(sd);
//
//						try
//						{
//							DFAgentDescription[] studs = DFService.search(myAgent, dfd);
//							AIDsCount = studs.length;
//							Arrays.asList(studs).forEach(stud -> styleReq.addReceiver(stud.getName()));
//						}
//						catch (FIPAException fe)
//						{
//							fe.printStackTrace();
//							end = true;
//						}
//						// if nbrReceivers = 0, no need to send anything
//						if (! styleReq.getAllReceiver().hasNext())
//						{
//							end = true;
//						}
//						else // at least 1 receiver, we send
//						{
//							myAgent.send(styleReq);
//							state = 1;
//						}
//					}
//					else // no service found (impossible)
//					{
//						end = true;
//					}
//				}
//				catch (FIPAException fe)
//				{
//					fe.printStackTrace();
//				}
//				break;
//
//			case 1: // receive styles
//				MessageTemplate fltr = MessageTemplate.MatchConversationId("get-style");
//				ACLMessage styleMsg = myAgent.receive(fltr);
//
//				if (styleMsg != null)
//				{
//					if (styleMsg.getPerformative() == ACLMessage.PROPOSE)
//					{
//						// In fact, style is not a string but a TestResult
//						// we should change that
//						String style = styleMsg.getContent();
//						AIDsCount--;
//
//						if (AIDsCount == 0) // we have received all responses
//						{
//							state = 2;
//						}
//					}
//					else // performative = failure, i.e. stud has not a style yet
//					{
//
//					}
//				}
//				else
//				{
//					block();
//				}
//				break;
//
//			case 2: //calculate global kolb
//
//				LearningStyle ls = globalKolbResult.getResultPerc();
//				studsKolb.forEach(tr -> {
//					ls.setConcretePerc(ls.getConcretePerc() + tr.getResultPerc().getConcretePerc());
//					ls.setReflectivePerc(ls.getReflectivePerc() + tr.getResultPerc().getReflectivePerc());
//					ls.setAbstractPerc(ls.getAbstractPerc() + tr.getResultPerc().getAbstractPerc());
//					ls.setActivePerc(ls.getActivePerc() + tr.getResultPerc().getActivePerc());
//				});
//				ls.setConcretePerc(ls.getConcretePerc() / AIDsCount);
//				ls.setReflectivePerc(ls.getReflectivePerc() / AIDsCount);
//				ls.setAbstractPerc(ls.getAbstractPerc() / AIDsCount);
//				ls.setActivePerc(ls.getActivePerc() / AIDsCount);
//				end = true;
//
//				break;
//			}
//		}
//
//		@Override
//		public boolean done()
//		{
//			return end;
//		}
//	}
}
