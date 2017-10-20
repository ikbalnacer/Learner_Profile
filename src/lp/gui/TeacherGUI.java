package lp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import lp.naai.TeacherNAAI;

public class TeacherGUI extends JFrame
{
	private TeacherNAAI handler;

	private String dir;
	private JPanel identification_pan = new JPanel();
	private 	JLabel label_photo = new JLabel();

	public TeacherGUI(String dir, TeacherNAAI handler)
	{
		this.dir = dir;
		this.handler = handler;
		this.setSize(new Dimension(1000,680));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		 identification_space();

		 JPanel pan_modules = new JPanel();
		 JPanel pan_Tests = new JPanel();

		 TitledBorder tb3=BorderFactory.createTitledBorder(" Modules ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
		 pan_modules.setBorder(tb3);

		 JLabel module = new JLabel("Module                          :  ");
	     module.setFont(new Font("Palatino Linotype", Font.BOLD, 17));

	     JLabel Courses = new JLabel("                              Courses                                     ");
	     Courses.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     JLabel chapter1= new JLabel("Chapter 1");
	     chapter1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download1 = new JButton("Upload");
	     download1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter2= new JLabel("Chapter 2");
	     chapter2.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download2 = new JButton("Upload");
	     download2.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter3= new JLabel("Chapter 3");
	     chapter3.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download3 = new JButton("Upload");
	     download3.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter4= new JLabel("Chapter 4");
	     chapter4.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download4 = new JButton("Upload");
	     download4.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter5= new JLabel("Chapter 5");
	     chapter5.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download5 = new JButton("Upload");
	     download5.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter6= new JLabel("Chapter 6");
	     chapter6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download6 = new JButton("Upload");
	     download6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter7= new JLabel("Chapter 7");
	     chapter7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download7 = new JButton("Upload");
	     download7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter8= new JLabel("Chapter 8");
	     chapter8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download8 = new JButton("Upload");
	     download8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter9= new JLabel("Chapter 9");
	     chapter9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download9 = new JButton("Upload");
	     download9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel chapter10= new JLabel("Chapter 10");
	     chapter10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton download10 = new JButton("Upload");
	     download10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));

	     pan_modules.add(module);

	     pan_modules.add(Courses);
	     pan_modules.add(chapter1);
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
	     pan_modules.add(download10);

	     JLabel exercices = new JLabel("                              Tests                                        ");
	     exercices.setFont(new Font("Palatino Linotype", Font.BOLD, 17));

	     JLabel exo1= new JLabel("    Test 1");
	     exo1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE1 = new JButton("Edit test");
	     downloadE1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE1.setName("1");
	     System.out.println("downloadE1 name is: "+downloadE1.getName());
	     downloadE1.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE1.getName())));

	     JLabel Exo02= new JLabel("    Test 2");
	     Exo02.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE2 = new JButton("Edit test");
	     downloadE2.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE2.setName("2");
	     downloadE2.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE2.getName())));

	     JLabel Exo03= new JLabel("   Test 3");
	     Exo03.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE3 = new JButton("Edit test");
	     downloadE3.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE3.setName("3");
	     downloadE3.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE3.getName())));

	     JLabel Exo04= new JLabel("   Test 4");
	     Exo04.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE4 = new JButton("Edit test");
	     downloadE4.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE4.setName("4");
	     downloadE4.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE4.getName())));

	     JLabel Exo05= new JLabel("   Test 5");
	     Exo05.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE5 = new JButton("Edit test");
	     downloadE5.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE5.setName("5");
	     downloadE5.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE5.getName())));

	     JLabel Exo6= new JLabel("    Test 6");
	     Exo6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE6 = new JButton("Edit test");
	     downloadE6.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE6.setName("6");
	     downloadE6.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE6.getName())));

	     JLabel Exo7= new JLabel("    Test 7");
	     Exo7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE7 = new JButton("Edit test");
	     downloadE7.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE7.setName("7");
	     downloadE7.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE7.getName())));

	     JLabel Exo8= new JLabel("    Test 8");
	     Exo8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE8 = new JButton("Edit test");
	     downloadE8.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE8.setName("8");
	     downloadE8.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE8.getName())));

	     JLabel Exo9= new JLabel("    Test 9");
	     Exo9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE9 = new JButton("Edit test");
	     downloadE9.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE9.setName("9");
	     downloadE9.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE9.getName())));

	     JLabel Exo10= new JLabel("   Test 10");
	     Exo10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JButton downloadE10 = new JButton("Edit test");
	     downloadE10.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     downloadE10.setName("10");
	     downloadE10.addActionListener(ae -> new TestCreator(handler, dir, Integer.parseInt(downloadE10.getName())));

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
	     centerpan.add(pan_modules,BorderLayout.NORTH);
	     centerpan.add(pan_Tests,BorderLayout.SOUTH);
	     pan_modules.setPreferredSize(new Dimension(400,300));
	     centerpan.setPreferredSize(new Dimension(300,500));
	     centerpan.setBackground(Color.WHITE);
	     pan_modules.setBackground(Color.WHITE);

	     JPanel the_side_pan = new JPanel();
	     JPanel description_module = new JPanel();
	     JTextArea area = new JTextArea();
	     tb3=BorderFactory.createTitledBorder(" Courses recommandations. ");
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
	     tb3=BorderFactory.createTitledBorder(" Exercices Recommandation ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     notification.setBorder(tb3);
	     JTextArea area1 = new JTextArea();

	     notification.add(area1);
	     notification.setBackground(Color.WHITE);
	     the_side_pan.setBackground(Color.WHITE);
	     area1.setPreferredSize(new Dimension(310,190));
	     notification.setPreferredSize(new Dimension(320,280));
	     the_side_pan.add(notification,BorderLayout.SOUTH);
	     area1.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));

	     this.getContentPane().removeAll();
		 this.getContentPane().removeAll();
		 this.getContentPane().add(the_side_pan,BorderLayout.EAST);
		 this.getContentPane().add(centerpan,BorderLayout.CENTER);
		 this.getContentPane().add(identification_pan,BorderLayout.WEST);
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
		//next_page_button.addActionListener(ae -> student_nextpage());

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

	public void disableChapters()
	{

	}

	public void showMsg(String msg)
	{

	}

	public void showErrors(String[] msgs)
	{

	}

	@Override
	public void dispose()
	{
		super.dispose();
		handler.logout();
	}
	
	
	
}
