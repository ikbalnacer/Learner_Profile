package lp.gui;

import java.util.Map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import lp.naai.AccessNAAI;

public class AccessGUI extends JFrame
{
	private AccessNAAI handler;

	private JMenuBar menuBar = new JMenuBar();
	private JMenu test1 = new JMenu("Fichier");
	private JMenu test1_2 = new JMenu("Sous ficher");
	private JMenu test2 = new JMenu("Edition");
	private JMenuItem item1 = new JMenuItem("Ouvrir");
	private JMenuItem item2 = new JMenuItem("Fermer");
	private JMenuItem item3 = new JMenuItem("Lancer");
	private JMenuItem item4 = new JMenuItem("Arrêter");
	private JTextField email, password, nameEmail_text, namelast_text, name_text;
	private JComboBox<String> comb;
	private JButton submit;

	public AccessGUI(AccessNAAI handler)
	{
		this.handler = handler;

		this.setTitle("NS Learner Profile");
		this.setSize(800, 600);
		BorderLayout border = new BorderLayout();
		BorderLayout border1 = new BorderLayout();
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			 }catch (InstantiationException e) {}
			 catch (ClassNotFoundException e) {}
			 catch (UnsupportedLookAndFeelException e) {}
			 catch (IllegalAccessException e) {}
		this.setLocationRelativeTo(null);
		JPanel pan = new JPanel();
		JLabel label1=null;
		ImageIcon img = new ImageIcon("studybetter.png");
		label1 = new JLabel(img);

		label1.setBorder(BorderFactory.createEtchedBorder());
		JPanel North_pan = new JPanel();
		pan.add(label1);
		pan.setBackground(Color.white);
		this.test1.add(item1);
        this.test1_2.addSeparator();
	    ButtonGroup bg = new ButtonGroup();
		this.test1.add(this.test1_2);
		this.test1.addSeparator();
		item2.addActionListener(ae -> System.exit(0));
		this.test1.add(item2);
		this.test2.add(item3);
		this.test2.add(item4);
		JPanel panLogin = new JPanel();
		panLogin.setBackground(Color.white);
		panLogin.setPreferredSize(new Dimension(380, 135));
		email = new JTextField(); //in fact, username
		email.setPreferredSize(new Dimension(200, 25));

		TitledBorder tb=BorderFactory.createTitledBorder("Login");

		tb.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
		panLogin.setBorder(tb);

		JLabel emailLabel = new JLabel("Username :");
		emailLabel.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
		emailLabel.setPreferredSize(new Dimension(100, 25));
		panLogin.add(emailLabel);
		panLogin.add(email);
		JLabel passwordLabel = new JLabel("Password :");
		panLogin.add(passwordLabel);
		JButton login = new JButton("sign in");
		login.addActionListener(ae -> this.login());

		//login.addActionListener(ae -> student_mode()); Invoking the StudentGUI

		login.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));

		password = new JTextField();
		password.setPreferredSize(new Dimension(200, 25));
		passwordLabel.setPreferredSize(new Dimension(100, 25));
		passwordLabel.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
		panLogin.add(password);
		panLogin.add(login);

		this.menuBar.add(test1);
		this.menuBar.add(test2);

		this.setJMenuBar(menuBar);

		JPanel pan_sub = new JPanel();
        TitledBorder tb1=BorderFactory.createTitledBorder("Subsribe");
        tb1.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
		pan_sub.setBorder(tb1);
		pan_sub.setPreferredSize(new Dimension(280,400));
		JLabel label0 = new JLabel("                                                                             ");

		JLabel label2 = new JLabel("To subscribe, is more prefrable to use");
		JLabel label3 = new JLabel("your real name as well as a correct");
		JLabel label4 = new JLabel("answers to what follow about");
		JLabel label5 = new JLabel("              your information.             ");
		JLabel label6 = new JLabel("                                                                             ");

		pan_sub.add(label0,border.NORTH);
		pan_sub.add(label2,border.NORTH);
		label2.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));
		pan_sub.add(label3,border.NORTH);
		label3.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));
		pan_sub.add(label4,border.NORTH);
		label4.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));
		pan_sub.add(label5,border.NORTH);
		label5.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));
		pan_sub.add(label6,border.NORTH);

        JLabel label_name = new JLabel("Name           : ");
        name_text = new JTextField();
        label_name.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));
        name_text.setPreferredSize(new Dimension(160, 25));

        JLabel label_last_name = new JLabel("Last Name : ");
        namelast_text = new JTextField();
        namelast_text.setPreferredSize(new Dimension(160, 25));
        label_last_name.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));

        JLabel label_Email = new JLabel("E-Mail          : ");
        nameEmail_text = new JTextField();
        nameEmail_text.setPreferredSize(new Dimension(160, 25));
        label_Email.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));

        JLabel labelFromation = new JLabel("Domain : ");

        comb = new JComboBox();
        comb.addItem("GL");
        comb.addItem("STIC");//
        comb.addItem("STIW");
        comb.addItem("ACAD");
        comb.setPreferredSize(new Dimension(160, 25));

        JLabel label_age = new JLabel("Age             : ");
        JTextField nameage_text = new JTextField();
        nameage_text.setPreferredSize(new Dimension(160, 25));
        label_age.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));
        labelFromation.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));

        pan_sub.add(label_name,BorderLayout.CENTER);
        pan_sub.add(name_text,BorderLayout.CENTER);

        pan_sub.add(label_last_name,BorderLayout.CENTER);
        pan_sub.add(namelast_text,BorderLayout.CENTER);

        pan_sub.add(label_Email,BorderLayout.CENTER);
        pan_sub.add(nameEmail_text,BorderLayout.CENTER);//nameEmail_text, namelast_text, name_text
        pan_sub.add(labelFromation,BorderLayout.CENTER);
        pan_sub.add(comb,BorderLayout.CENTER);
        pan_sub.add(label_age,BorderLayout.CENTER);
        pan_sub.add(nameage_text,BorderLayout.CENTER);

        submit = new JButton("SIGN UP");
        submit.addActionListener(ae -> this.subscribe());
        submit.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        pan_sub.add(submit,BorderLayout.CENTER);
    	North_pan.add(pan,BorderLayout.NORTH);
		North_pan.add(panLogin,BorderLayout.NORTH);
		North_pan.setBackground(Color.WHITE);
		pan_sub.setBackground(Color.WHITE);

		JScrollPane scroll1 = new JScrollPane();
        scroll1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroll1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JPanel learningstyle1 = new JPanel();
        JPanel learningstyle2 = new JPanel();
        JPanel learningstyle3 = new JPanel();
        JPanel learningstyle4 = new JPanel();
        learningstyle1.setPreferredSize(new Dimension(500,160));
        JPanel gen_pan1 = new JPanel();
        gen_pan1.add(learningstyle1);
        gen_pan1.setPreferredSize(new Dimension(500,160));
        JPanel pan_scrol1 = new JPanel();
        pan_scrol1.setPreferredSize(new Dimension(500,180));
        scroll1.setPreferredSize(new Dimension(480,140));
        pan_scrol1.add(scroll1);
        scroll1.add(gen_pan1);
        gen_pan1.setBackground(Color.WHITE);
        pan_scrol1.setBackground(Color.WHITE);

		JScrollPane scroll = new JScrollPane();

        JPanel form1 = new JPanel();
        form1.setPreferredSize(new Dimension(120,150));
        JLabel number_student = new JLabel("Student Number  :     :");
        JLabel leanring_style = new JLabel("learning style  :     :");
        JLabel formation_ratio1 = new JLabel("domain %  :      :");
        JLabel formation_ratio2 = new JLabel("domain %   :        :");
        JLabel formation_ratio3 = new JLabel("domain %  :        :");
        form1.add(number_student);
        form1.add(leanring_style);
        form1.add(formation_ratio1);
        form1.add(formation_ratio2);
        form1.add(formation_ratio3);

        JPanel form2 = new JPanel();
        JPanel form3 = new JPanel();
        JPanel form4 = new JPanel();
        form1.setPreferredSize(new Dimension(500,160));
        JPanel gen_pan = new JPanel();

        gen_pan.add(form1);
        gen_pan.setPreferredSize(new Dimension(500,160));
        JPanel pan_scrol = new JPanel();
        pan_scrol.setPreferredSize(new Dimension(500,180));
        scroll.setPreferredSize(new Dimension(480,140));
        pan_scrol.add(scroll);
        scroll.add(form1);
        gen_pan.setBackground(Color.WHITE);
        pan_scrol.setBackground(Color.WHITE);

        JPanel pan_info = new JPanel();
        pan_info.setPreferredSize(new Dimension(500,400));
        TitledBorder tb3=BorderFactory.createTitledBorder("Student types");
        tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        pan_scrol1.setBorder(tb3);
        TitledBorder tb4=BorderFactory.createTitledBorder("Domains");
        tb4.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        pan_scrol.setBorder(tb4);
        pan_info.add(pan_scrol1,BorderLayout.NORTH);
        pan_info.add(pan_scrol,BorderLayout.CENTER);
        pan_info.setBackground(Color.WHITE);

		this.getContentPane().add(pan_sub,BorderLayout.WEST);
		this.getContentPane().add(North_pan,BorderLayout.NORTH);
		this.getContentPane().add(pan_info,BorderLayout.CENTER);

		this.getContentPane().setBackground(Color.WHITE);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBackground(Color.WHITE);
		this.setVisible(false);
	}

	/* Inner class for showing messages */
	private class Expose extends JFrame
	{
		private String[] errs;

		public Expose(String[] errs)
		{
			Box errsBox = Box.createVerticalBox();
			JPanel[] errsPans = new JPanel[errs.length];
			for (int i = 0; i < errsPans.length; i++)
			{
				errsPans[i] = new JPanel();
				errsPans[i].setPreferredSize(new Dimension(700, 30));
				JLabel errLab = new JLabel(errs[i]);
				errLab.setFont(new Font("Tahoma", Font.BOLD, 14));
				errLab.setForeground(Color.RED);
				errLab.setHorizontalAlignment(JLabel.LEFT);
				errsPans[i].add(errLab);
				errsBox.add(errsPans[i]);
			}
			setVisible(true);
			setSize(700, 30*errs.length);
			getContentPane().add(errsBox);
			pack();
		}
	}/*--------------- end class Expose ------------------------*/

	public void showSubscribeErrors(String[] errors)
	{
		new Expose(errors);
	}

	public void showMessage(String message)
	{
		new Expose(new String[]{ message });
	}

	public void showLoginErrors(String[] errors)
	{
		new Expose(errors);
	}

	public void showStatistics(Map<String, Map<String, Integer>> statistics)
	{

	}

	public void disableSubscribe()
	{
		submit.setEnabled(false);
	}
	private void login()
	{
		this.handler.login(email.getText(), password.getText());
	}

	private void subscribe()
	{
		System.out.println(nameEmail_text.getText());
		this.handler.subscribe(new String[] {
				name_text.getText(),
				namelast_text.getText(),
				nameEmail_text.getText(),
				(String) comb.getSelectedItem()
		});//fn,ln,em,dom
	}

	@Override
	public void dispose()
	{
		//super.dispose();
		SwingUtilities.invokeLater(() -> {
			this.setVisible(false);
			this.revalidate();
		});
	}
}
