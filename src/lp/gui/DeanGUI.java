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

import lp.naai.DeanNAAI;

public class DeanGUI extends JFrame
{
	private DeanNAAI handler;
	private JPanel identification_pan = new JPanel();
	private JLabel label_photo = new JLabel();

	public DeanGUI(DeanNAAI handler)
	{
		this.handler = handler;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(new Dimension(1000,680));
		 identification_pan.removeAll();
		 identification_space();

		 JPanel pan_modules = new JPanel();
		 JPanel pan_Tests = new JPanel();

		 TitledBorder tb3=BorderFactory.createTitledBorder(" Modules ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
		 pan_modules.setBorder(tb3);

		 JLabel module = new JLabel("Domain                          :           ");
	     module.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     JComboBox<String> modcomb = new JComboBox<String>();
	     modcomb.addItem(" GL ");
	     JTextArea labe1 = new JTextArea("                         ");
	     JPanel textpan = new JPanel();
	     textpan.add(labe1);

	     textpan.setBackground(Color.WHITE);
	     labe1.setFont(new Font("Palatino Linotype", Font.BOLD, 15));

	     labe1.setPreferredSize(new Dimension(380,180));
	     labe1.setEnabled(false);
	     labe1.setForeground(Color.WHITE);
	     pan_modules.add(module);
	     pan_modules.add(modcomb);
	     pan_modules.add(textpan);
	     JPanel pan_but1 = new JPanel();

	     JButton create1 = new JButton(" Create ");
	     create1.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     JButton Modify1 = new JButton(" Modify ");
	     Modify1.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     JButton delete1 = new JButton(" Delete ");
	     delete1.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     JButton consult1 = new JButton(" Consult ");
	     consult1.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     pan_but1.add(create1);
	     pan_but1.add(Modify1);
	     pan_but1.add(delete1);
	     pan_but1.add(consult1);

	     pan_but1.setBackground(Color.WHITE);

	     pan_modules.add(pan_but1,BorderLayout.SOUTH);

	     pan_modules.setPreferredSize(new Dimension(300,300));

	     JButton create = new JButton(" Create ");
	     create.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     JButton Modify = new JButton(" Modify ");
	     Modify.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     JButton delete = new JButton(" Delete ");
	     delete.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));
	     JButton consult = new JButton(" Consult ");
	     consult.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 12));

	     JPanel pan_but = new JPanel();
	     pan_but.add(create);
	     pan_but.add(Modify);
	     pan_but.add(delete);
	     pan_but.add(consult);
	     pan_but.setBackground(Color.WHITE);


         tb3=BorderFactory.createTitledBorder(" Competancies ");
	     tb3.setTitleFont(new Font("Matura MT Script Capitals", Font.BOLD, 20));
	     pan_Tests.setBorder(tb3);

	     JLabel responsible = new JLabel("responsible                          :           ");
	     module.setFont(new Font("Palatino Linotype", Font.BOLD, 17));
	     responsible.setFont(new Font("Palatino Linotype", Font.BOLD, 17));

	     JComboBox<String> resp = new JComboBox<String>();
	     modcomb.addItem(" GL ");

	     pan_Tests.add(responsible);
	     pan_Tests.add(resp);

	     JTextArea area2 = new JTextArea();
	     JPanel panarea1=new JPanel();
	     panarea1.add(area2);
	     pan_Tests.add(area2);

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
	     tb3=BorderFactory.createTitledBorder("  Notification. ");
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
	     tb3=BorderFactory.createTitledBorder(" genral information ");
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
		 try {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			 }catch (InstantiationException e) {}
			 catch (ClassNotFoundException e) {}
			 catch (UnsupportedLookAndFeelException e) {}
			 catch (IllegalAccessException e) {}
	}

	@Override
	public void dispose()
	{
		//super.dispose();
		handler.logout();
	}
}
