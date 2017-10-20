package lp.behaviors;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.ControllerException;

/* The behavior that detects the user login/logout to the system.
 *  It receives a message from DeanAgent after a valid login,
 *  so that it sends the GUI to its user, adds some behaviors.
 *  Or, it receives a logout message from GUI, so it removes
 *  some behaviors. */
public class LogIONotifier extends CyclicBehaviour
{
	private JFrame ownerGUI;
	private List<Behaviour> toDo;
	public LogIONotifier(JFrame ownerGUI, List<Behaviour> toDo)
	{
		this.ownerGUI = ownerGUI;
		this.toDo = toDo;
	}

	@Override
	public void action()
	{
		MessageTemplate login = MessageTemplate.MatchConversationId("login");
		MessageTemplate logout = MessageTemplate.MatchConversationId("logout");

		MessageTemplate temp = MessageTemplate.or(login, logout);
		ACLMessage userPrsn = myAgent.receive(temp);

		if (userPrsn != null)
		{
			if ("login".equals(userPrsn.getConversationId()))
			{
				if (toDo != null)
				{
					new Thread(() -> toDo.forEach(b -> myAgent.addBehaviour(b))).start();
				}

				EventQueue.invokeLater(() -> {
					ownerGUI.setVisible(true);// show GUI from user
					ownerGUI.revalidate();
				});
			}
			else // logout
			{
				EventQueue.invokeLater(() -> {
					ownerGUI.setVisible(false);// hide GUI from user
					ownerGUI.revalidate();
				});
				/*try
				{
					myAgent.getContainerController().getPlatformController().kill();
				}
				catch (ControllerException e)
				{
					e.printStackTrace();
				}*/
			}
		}
		else
		{
			block();
		}
	}
}
