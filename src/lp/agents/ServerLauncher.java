package lp.agents;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.StaleProxyException;

public class ServerLauncher
{
	public static void main(String[] args)
	{
		/* Creating the agents XML folder. */
		File agentsXML = new File("agentsXML");
		if (!agentsXML.exists())
		{
			agentsXML.mkdir();
		}

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
		Profile profile = new ProfileImpl(ip, 12400, null);
		jade.wrapper.AgentContainer mainContainer = rt.createMainContainer(profile);

		System.out.println("mainCont = "+(mainContainer == null));

		try
		{
			mainContainer.createNewAgent("dean", lp.agents.DeanAgent.class.getName(), null).start();
		}
		catch (StaleProxyException e)
		{
			e.printStackTrace();
		}
	}
}
