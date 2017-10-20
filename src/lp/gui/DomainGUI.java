package lp.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import lp.naai.DomainNAAI;

public class DomainGUI extends JFrame
{
	private DomainNAAI handler;
	private JPanel identification_pan = new JPanel();
	private 	JLabel label_photo = new JLabel();

	public DomainGUI(DomainNAAI handler)
	{
		this.handler = handler;
		this.setSize(new Dimension(1000,680));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		 identification_pan.removeAll();
		 identification_space();

		 JPanel pan_modules = new JPanel();
		 JPanel pan_Tests = new JPanel();

		 TitledBorder tb3=BorderFactory.createTitledBorder(" Modules ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
		 pan_modules.setBorder(tb3);

		 JLabel module = new JLabel("Module                          :           ");
	     module.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     JComboBox<String> modcomb = new JComboBox<String>();
	     modcomb.addItem("Crypto");
	     JTextArea labe1 = new JTextArea("Info                          ");
	     labe1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));

	     labe1.setPreferredSize(new Dimension(380,220));
	     labe1.setEnabled(false);
	     labe1.setForeground(Color.WHITE);
	     pan_modules.add(module);
	     pan_modules.add(modcomb);
	     pan_modules.add(labe1);

	     pan_modules.setPreferredSize(new Dimension(300,300));

	     JButton create = new JButton(" Create ");
	     create.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
	     JButton Modify = new JButton(" Modify ");
	     Modify.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
	     JButton delete = new JButton(" Delete ");
	     delete.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));
	     JButton consult = new JButton(" Consult ");
	     consult.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 15));

	     JPanel pan_but = new JPanel();
	     pan_but.add(create);
	     pan_but.add(Modify);
	     pan_but.add(delete);
	     pan_but.add(consult);
	     pan_but.setBackground(Color.WHITE);


        tb3=BorderFactory.createTitledBorder(" Competancies ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     pan_Tests.setBorder(tb3);
	     JPanel required = new JPanel();
	     JLabel required1 = new JLabel("Required 1");
	     required.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel required2 = new JLabel("Required 1");
	     required.setFont(new Font("Palatino Linotype", Font.BOLD, 15));
	     JLabel required3 = new JLabel("Required 1");
	     JLabel required4 = new JLabel("Required 1");
	     JLabel required5 = new JLabel("Required 1");
	     tb3=BorderFactory.createTitledBorder(" Required ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     required.setBorder(tb3);
	     required.setBackground(Color.WHITE);
	     required.setPreferredSize(new Dimension(180,200));
	     required.add(required1);
	     required.add(required2);
	     required.add(required3);
	     required.add(required4);
	     required.add(required5);

	     JPanel Post_requisite  = new JPanel();
	     JLabel Post_requisite1 = new JLabel("Requisite");
	     JLabel Post_requisite2 = new JLabel("Requisite");
	     JLabel Post_requisite3 = new JLabel("Requisite");
	     JLabel Post_requisite4 = new JLabel("Requisite");
	     JLabel Post_requisite5 = new JLabel("Requisite");
	     tb3=BorderFactory.createTitledBorder(" Post requisite ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     Post_requisite.setBorder(tb3);
	     Post_requisite.setPreferredSize(new Dimension(180,200));
	     Post_requisite.setBackground(Color.WHITE);
	     Post_requisite1.add(required1);
	     Post_requisite2.add(required2);
	     Post_requisite3.add(required3);
	     Post_requisite4.add(required4);
	     Post_requisite5.add(required5);
	     pan_Tests.add(required,BorderLayout.WEST);
	     pan_Tests.add(Post_requisite,BorderLayout.EAST);
	     pan_Tests.setBackground(Color.WHITE);
	     JPanel centerpan = new JPanel();
	     centerpan.setBackground(Color.WHITE);
	     pan_Tests.setPreferredSize(new Dimension(400,250));
	     centerpan.add(pan_modules,BorderLayout.NORTH);
	     centerpan.add(pan_Tests,BorderLayout.CENTER);
	     centerpan.add(pan_but,BorderLayout.SOUTH);

	     pan_modules.setPreferredSize(new Dimension(400,300));
	     centerpan.setPreferredSize(new Dimension(300,500));
	     centerpan.setBackground(Color.WHITE);
	     pan_modules.setBackground(Color.WHITE);

	     JPanel the_side_pan = new JPanel();
	     JPanel description_module = new JPanel();
	     JTextArea area = new JTextArea();
	     tb3=BorderFactory.createTitledBorder(" Teacher notification. ");
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
	     tb3=BorderFactory.createTitledBorder(" Student Notification ");
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

	@Override
	public void dispose()
	{
		//super.dispose();
		handler.logout();
	}
}
