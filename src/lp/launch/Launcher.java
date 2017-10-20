package lp.launch;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

import jade.core.AID;
import jade.core.PlatformID;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

public class Launcher
{
	public static void main(String[] args)
	{
		//-gui -local-port 12400 dean:lp.agents.DeanAgent
		String ip = "";
		try
		{
			ip = InetAddress.getLocalHost().getHostAddress();
			System.out.println(ip);
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}
		jade.core.Runtime rt = jade.core.Runtime.instance();

		// Exit the JVM when there are no more containers around
		rt.setCloseVM(true);

		// Create a default profile
		Profile profile = new ProfileImpl(ip, 12400, new PlatformID(new AID("ams@"+ip+":12400/JADE", true)).getName());
		jade.wrapper.AgentContainer agentContainer = rt.createAgentContainer(profile);

		System.out.println("AgentCont = "+(agentContainer == null));

		try
		{
			agentContainer.createNewAgent("enter", lp.launch.LaunchAgent.class.getName(), new Object[]{ip+":12400/JADE"}).start();
		}
		catch (StaleProxyException e)
		{
			e.printStackTrace();
		}
	}
}
