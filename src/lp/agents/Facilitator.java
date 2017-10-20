package lp.agents;

import jade.core.Agent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Facilitator extends Agent
{
	@Override
	protected void setup()
	{
		DFAgentDescription d = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
	}
}
