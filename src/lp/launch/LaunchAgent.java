package lp.launch;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class LaunchAgent extends Agent
{
	private static String accessAddr = "access@";

	@Override
	protected void setup()
	{
		System.out.println(getAID().getName());
		LaunchAgent.accessAddr += (String) getArguments()[0];
		addBehaviour(new Launch());
	}

	private class Launch extends Behaviour
	{
		private int state;
		private boolean end;

		public Launch()
		{
			this.state = 0;
			this.end = false;
		}

		@Override
		public void action()
		{
			switch (this.state)
			{
			case 0: // send to open app
				ACLMessage openApp = new ACLMessage(ACLMessage.REQUEST);
				openApp.addReceiver(new AID(accessAddr, true));
				openApp.setConversationId("open-app");
				send(openApp);
				state = 1;
				break;

			case 1: // receive response and terminate
				MessageTemplate temp = MessageTemplate.MatchConversationId("open-app");
				ACLMessage resp = receive(temp);
				if (resp != null)
				{
					System.out.println("sender = "+resp.getSender().getName());
					System.out.println("cntnt = "+resp.getContent());
					end = true;
					doDelete();
					/*try
					{
						getContainerController().kill();
					}
					catch (StaleProxyException e)
					{
						e.printStackTrace();
					}*/
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

	}
}
