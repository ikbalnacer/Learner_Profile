package lp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import lp.naai.StudentNAAI;
import lp.struct.Chapter;
import lp.struct.Module;
import lp.struct.Test;

public class StudentGUI extends JFrame
{
	private StudentNAAI handler;

	private JPanel question_pan = new JPanel();
	private JPanel answers_pan = new JPanel(), pan_modules, testsList, testsListBody;
	private JPanel identification_pan = new JPanel();
	private JLabel label_photo = new JLabel();
	private JComboBox<String> comb;
	private JButton crsBtn;

	public StudentGUI(StudentNAAI handler)
	{
		System.out.println("thread = "+Thread.currentThread().getName());
		this.handler = handler;
		this.setSize(new Dimension(1000,680));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        identification_space();

		testsList = new JPanel(new BorderLayout());
		JPanel headers = new JPanel(new GridLayout(1, 3));
		headers.add(new JLabel("Module"));
		headers.add(new JLabel("Chapter N°"));
		headers.add(new JLabel("pass it"));

		headers.setBorder(BorderFactory.createBevelBorder(1));
		testsList.add(headers, BorderLayout.NORTH);

		testsListBody = new JPanel();
		testsListBody.setLayout(new BoxLayout(testsListBody, BoxLayout.Y_AXIS));
		testsList.add(new JScrollPane(testsListBody), BorderLayout.CENTER);

		JPanel bar = new JPanel();
		crsBtn = new JButton("start course");
		crsBtn.addActionListener(ae -> handler.startCourse());
		bar.add(crsBtn);
		testsList.add(bar, BorderLayout.SOUTH);

		JTextArea notif_text = new JTextArea();
		notif_text.setPreferredSize(new Dimension(450,150));
		notif_text.setEnabled(false);
		TitledBorder tb2=BorderFactory.createTitledBorder("Notification");
        tb2.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        testsList.setBorder(tb2);
        //notif_text.setBorder(tb2);
		JTextArea Question_text = new JTextArea();
		TitledBorder tb3=BorderFactory.createTitledBorder("Question space");
        tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        Question_text.setBorder(tb3);
		Question_text.setPreferredSize(new Dimension(450,400));
		JPanel pan_send = new JPanel();
		pan_send.setPreferredSize(new Dimension(250,100));
		pan_send.setBackground(Color.WHITE);
		JButton send = new JButton("Send");

		pan_send.add(send);

		//question_pan.add(notif_text,BorderLayout.NORTH);
		//question_pan.add(new JScrollPane(testsList), BorderLayout.CENTER);

        send.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));

		tb3=BorderFactory.createTitledBorder("Requirement space");
        tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        question_pan.setBorder(tb3);
		answers_pan.setPreferredSize(new Dimension(300,500));

		question_pan.setBackground(Color.WHITE);
        JTextArea answerstext = new JTextArea();
        JScrollPane scroll = new JScrollPane(answerstext);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        answerstext.setPreferredSize(new Dimension(280,500));
        answerstext.setAutoscrolls(true);
        answerstext.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        tb3=BorderFactory.createTitledBorder("Write Your Answers");
        tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        scroll.setBackground(Color.WHITE);
        scroll.setAutoscrolls(true);
        scroll.setBorder(tb3);

        tb3=BorderFactory.createTitledBorder("Answers Space");
        tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        answers_pan.setBorder(tb3);

        answers_pan.setBackground(Color.WHITE);
        answers_pan.add(scroll,BorderLayout.CENTER);
        answers_pan.add(pan_send,BorderLayout.SOUTH);

		this.getContentPane().add(identification_pan,BorderLayout.WEST);
		this.getContentPane().add(new JScrollPane(testsList), BorderLayout.CENTER);
		//this.getContentPane().add(answers_pan,BorderLayout.EAST);
		//testM();
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			 }catch (InstantiationException e) {}
			 catch (ClassNotFoundException e) {}
			 catch (UnsupportedLookAndFeelException e) {}
			 catch (IllegalAccessException e) {}
	}

	public void identification_space()
	{
    	label_photo.setPreferredSize(new Dimension(150,100));

		JLabel label_nom = new JLabel("nom :                                           ");
		JTextField nom_resp = new JTextField();
		nom_resp.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		nom_resp.setEnabled(false);
		nom_resp.setPreferredSize(new Dimension(170, 30));
		JLabel label_lastname = new JLabel("prenom :                                     ");
		JTextField lastname_resp = new JTextField();
		lastname_resp.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		lastname_resp.setEnabled(false);
		lastname_resp.setPreferredSize(new Dimension(170, 30));
		JLabel label_formation = new JLabel("formation  :                                ");
		JTextField formation_resp = new JTextField();
		formation_resp.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		formation_resp.setEnabled(false);
		formation_resp.setPreferredSize(new Dimension(170, 30));
		JLabel label_naissance = new JLabel("date de naissance :                    ");
		JTextField naissance_resp = new JTextField();
		naissance_resp.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		naissance_resp.setEnabled(false);
		naissance_resp.setPreferredSize(new Dimension(170, 30));

		JLabel label_participation=new JLabel("date de partcipation :                             ");
		JTextField particip_resp = new JTextField();
		particip_resp.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		particip_resp.setEnabled(false);
		particip_resp.setPreferredSize(new Dimension(170, 30));

		JLabel label_anticipation = new JLabel("graduation anticipated :                       ");
		JTextField anticipation_resp = new JTextField();
		anticipation_resp.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		anticipation_resp.setPreferredSize(new Dimension(170, 30));
		anticipation_resp.setEnabled(false);

		JButton next_page_button = new JButton(" Next page ");
		next_page_button.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
		JButton edit_button = new JButton(" Edit info ");

		/*lambda*/
		next_page_button.addActionListener(ae -> student_nextpage());

		edit_button.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
		JButton send_message = new JButton(" Send Message ");
		send_message.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        JPanel photo_pan = new JPanel();
        TitledBorder tb8=BorderFactory.createTitledBorder("Photo");
        tb8.setTitleFont(new Font("Palatino Linotype", Font.BOLD, 15));
        photo_pan.setBorder(tb8);
        photo_pan.setPreferredSize(new Dimension(150,180));
		identification_pan.add(photo_pan);
		photo_pan.setBackground(Color.WHITE);
		label_photo.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		identification_pan.add(label_nom);
		identification_pan.add(nom_resp);
		label_nom.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		identification_pan.add(label_lastname);
		identification_pan.add(lastname_resp);
		label_lastname.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		identification_pan.add(label_formation);
		identification_pan.add(formation_resp);
		label_formation.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		identification_pan.add(label_naissance);
		identification_pan.add(naissance_resp);
		label_naissance.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 12));
		identification_pan.add(label_participation);
		identification_pan.add(particip_resp);
		label_participation.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));

		identification_pan.add(label_anticipation);
		identification_pan.add(anticipation_resp);
		label_anticipation.setFont(new Font("Gill Sans Ultra Bold", Font.PLAIN, 10));

		identification_pan.add(edit_button);
		identification_pan.add(send_message);
        identification_pan.add(next_page_button);
		TitledBorder tb1=BorderFactory.createTitledBorder("espace d'identification");
        tb1.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
        identification_pan.setBorder(tb1);
		identification_pan.setBackground(Color.WHITE);
		identification_pan.setPreferredSize(new Dimension(230,500));
	}

	public void student_nextpage()
	{
		 this.setSize(new Dimension(1000,680));
		 identification_pan.removeAll();
		 identification_space();

		 pan_modules = new JPanel();
		 JScrollPane modScroll = new JScrollPane(pan_modules);
		 JPanel pan_Tests = new JPanel();

		 TitledBorder tb3=BorderFactory.createTitledBorder(" Modules ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     modScroll.setBorder(tb3);

		 JLabel module = new JLabel("Module             :  ");
	     module.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     comb = new JComboBox<>();

	     JLabel Courses = new JLabel("                              Courses                                     ");
	     Courses.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     /*JLabel chapter1= new JLabel("Chapter 1");
	     chapter1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download1 = new JButton("Donwload");
	     download1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter2= new JLabel("Chapter 2");
	     chapter2.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download2 = new JButton("Donwload");
	     download2.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter3= new JLabel("Chapter 3");
	     chapter3.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download3 = new JButton("Donwload");
	     download3.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter4= new JLabel("Chapter 4");
	     chapter4.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download4 = new JButton("Donwload");
	     download4.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter5= new JLabel("Chapter 5");
	     chapter5.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download5 = new JButton("Donwload");
	     download5.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter6= new JLabel("Chapter 6");
	     chapter6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download6 = new JButton("Donwload");
	     download6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter7= new JLabel("Chapter 7");
	     chapter7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download7 = new JButton("Donwload");
	     download7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter8= new JLabel("Chapter 8");
	     chapter8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download8 = new JButton("Donwload");
	     download8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter9= new JLabel("Chapter 9");
	     chapter9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download9 = new JButton("Donwload");
	     download9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter10= new JLabel("Chapter 10");
	     chapter10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download10 = new JButton("Donwload");
	     download10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));*/

	     pan_modules.add(module);
	     pan_modules.add(comb);
	     pan_modules.add(Courses);
	     /*pan_modules.add(chapter1);
	     pan_modules.add(download1);
	     pan_modules.add(chapter2);
	     pan_modules.add(download2);
	     pan_modules.add(chapter3);
	     pan_modules.add(download3);
	     pan_modules.add(chapter4);
	     pan_modules.add(download4);
	     pan_modules.add(chapter5);
	     pan_modules.add(download5);
	     pan_modules.add(chapter6);
	     pan_modules.add(download6);
	     pan_modules.add(chapter7);
	     pan_modules.add(download7);
	     pan_modules.add(chapter8);
	     pan_modules.add(download8);
	     pan_modules.add(chapter9);
	     pan_modules.add(download9);
	     pan_modules.add(chapter10);
	     pan_modules.add(download10);*/

	     JLabel exercices = new JLabel("                              Execices                                     ");
	     exercices.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     JLabel exo1= new JLabel("Execice 1");
	     exo1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE1 = new JButton("Donwload");
	     downloadE1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo02= new JLabel("Execice 2");
	     Exo02.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE2 = new JButton("Donwload");
	     downloadE2.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo03= new JLabel("Execice 3");
	     Exo03.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE3 = new JButton("Donwload");
	     downloadE3.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo04= new JLabel("Execice 4");
	     Exo04.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE4 = new JButton("Donwload");
	     downloadE4.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo05= new JLabel("Execice 5");
	     Exo05.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE5 = new JButton("Donwload");
	     downloadE5.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo6= new JLabel("Execice 6");
	     Exo6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE6 = new JButton("Donwload");
	     downloadE6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo7= new JLabel("Execice 7");
	     Exo7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE7 = new JButton("Donwload");
	     downloadE7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo8= new JLabel("Execice 8");
	     Exo8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE8 = new JButton("Donwload");
	     downloadE8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo9= new JLabel("Execice 9");
	     Exo9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE9 = new JButton("Donwload");
	     downloadE9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel Exo10= new JLabel("Execice 10");
	     Exo10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE10 = new JButton("Donwload");
	     downloadE10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));

	     pan_Tests.add(exercices);
	     pan_Tests.add(exo1);
	     pan_Tests.add(downloadE1);
	     pan_Tests.add(Exo02);
	     pan_Tests.add(downloadE2);
	     pan_Tests.add(Exo03);
	     pan_Tests.add(downloadE3);
	     pan_Tests.add(Exo03);
	     pan_Tests.add(downloadE3);
	     pan_Tests.add(Exo04);
	     pan_Tests.add(downloadE4);
	     pan_Tests.add(Exo05);
	     pan_Tests.add(downloadE5);
	     pan_Tests.add(Exo6);
	     pan_Tests.add(downloadE6);
	     pan_Tests.add(Exo7);
	     pan_Tests.add(downloadE7);
	     pan_Tests.add(Exo8);
	     pan_Tests.add(downloadE8);
	     pan_Tests.add(Exo9);
	     pan_Tests.add(downloadE9);
	     pan_Tests.add(Exo10);
	     pan_Tests.add(downloadE10);

	     tb3=BorderFactory.createTitledBorder(" Annex ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     pan_Tests.setBorder(tb3);

	     pan_Tests.setBackground(Color.WHITE);
	     JPanel centerpan = new JPanel();
	     centerpan.setBackground(Color.WHITE);
	     pan_Tests.setPreferredSize(new Dimension(400,300));
	     centerpan.add(modScroll,BorderLayout.NORTH);
	     centerpan.add(pan_Tests,BorderLayout.SOUTH);
	     pan_modules.setPreferredSize(new Dimension(400,300));
	     centerpan.setPreferredSize(new Dimension(300,500));
	     centerpan.setBackground(Color.WHITE);
	     pan_modules.setBackground(Color.WHITE);

	     JPanel the_side_pan = new JPanel();
	     JPanel description_module = new JPanel();
	     JTextArea area = new JTextArea();
	     tb3=BorderFactory.createTitledBorder(" Module's Abstraction ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     description_module.setBorder(tb3);
	     description_module.add(area);
	     description_module.setBackground(Color.WHITE);
	     the_side_pan.setBackground(Color.WHITE);
	     the_side_pan.setPreferredSize(new Dimension(350,500));
	     area.setPreferredSize(new Dimension(310,190));
	     description_module.setPreferredSize(new Dimension(320,280));
	     the_side_pan.add(description_module,BorderLayout.NORTH);
	     area.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));

	     JPanel notification = new JPanel();
	     tb3=BorderFactory.createTitledBorder(" Notification ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     notification.setBorder(tb3);

	     JTextArea area1 = new JTextArea();
	     //notification.add(area1);

	     notification.setBackground(Color.WHITE);
	     the_side_pan.setBackground(Color.WHITE);
	     area1.setPreferredSize(new Dimension(310,190));
	     notification.setPreferredSize(new Dimension(320,280));
	     the_side_pan.add(notification,BorderLayout.SOUTH);
	     area1.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));

		 //this.getContentPane().removeAll();
		 this.getContentPane().add(the_side_pan,BorderLayout.EAST);
		 this.getContentPane().add(centerpan,BorderLayout.CENTER);
		 this.getContentPane().add(identification_pan,BorderLayout.WEST);
	 }

	@Override
	public void dispose()
	{
		//super.dispose();
		handler.logout();
	}

	public void showTestInList(Module module, Test test)
	{
		JPanel testPan = new JPanel(new GridLayout(1, 3));
		testPan.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
		List<JPanel> dataPans = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			dataPans.add(0, new JPanel());
			testPan.add(dataPans.get(0));
		}

		JLabel lblMod = new JLabel(module.getTitle()),
				   lblChap = new JLabel("chapter"+test.getChapter().getNum());
		JButton passTest = new JButton("pass now");
		passTest.addActionListener(ae -> new TestView(test));

		dataPans.get(2).add(lblMod);
		dataPans.get(1).add(lblChap);
		dataPans.get(0).add(passTest);
		testsListBody.add(testPan);
		testsListBody.validate();
	}

	public void setCourseAccessibility(boolean access)
	{
		crsBtn.setEnabled(access);
	}
}
