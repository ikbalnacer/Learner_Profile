package lp.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;

import lp.naai.StudentNAAI;
import lp.struct.LSI;
import lp.struct.LSIQuestion;
import lp.struct.Module;
import lp.struct.Question;

import java.awt.Component;
import javax.swing.border.MatteBorder;
import java.awt.Dimension;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

public class ProfileInitiator extends JFrame
{
	private StudentNAAI handler;

	private JPanel contentPane;
	private DefaultListModel<String> dlm;
	private JList<String> listMods;
	private JTextField tfMath;
	private JTextField tfArab;
	private JTextField tfFrench;
	private JTextField tfEnglish;
	private JTextField tfAmasigh;
	private JTextField tfPhisic;
	private JTextField tfScien;
	private JTextField tfHGeo;
	private JTextField tfPhilosophy;
	private JTextField tfIslam;
	/* The values for each LSI question's choices (from 1 to 4)*/
	private List<JRadioButton> kolbChoicesValues;

	private LSI lsi;
	private List<Module> modulesBeans, selectedMods;
	private Map<String, Double> bacMarks;

	private JPanel kolbPan;
	private JPanel panLSIQuestions;
	private JPanel layers;

	/**
	 * Create the frame.
	 */
	public ProfileInitiator(StudentNAAI handler, LSI lsi, List<Module> modulesBeans)
	{
		this.handler = handler;
		this.lsi = lsi;
		this.modulesBeans = modulesBeans;
		kolbChoicesValues = new ArrayList<>();

		dlm = new DefaultListModel<>();
		initModsList(); // fill dlm

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 687, 398);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panBanner = new JPanel();
		panBanner.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panBanner, BorderLayout.NORTH);

		JLabel lblBanner = new JLabel("You are a new Student...Help us by initiating your profile");
		lblBanner.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBanner.setForeground(Color.BLUE);
		panBanner.add(lblBanner);

		layers = new JPanel();
		layers.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		contentPane.add(layers, BorderLayout.CENTER);
		layers.setLayout(new CardLayout(0, 0));

		JPanel modBacPan = new JPanel();
		layers.add(modBacPan, "modBacPan");
		modBacPan.setLayout(new BorderLayout(0, 0));

		JPanel bacPan = new JPanel();
		modBacPan.add(bacPan);
		bacPan.setLayout(new BorderLayout(0, 0));

		JPanel bacTitlePan = new JPanel();
		bacTitlePan.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		bacPan.add(bacTitlePan, BorderLayout.NORTH);

		JLabel lblBac = new JLabel("Enter your BAC marks");
		bacTitlePan.add(lblBac);

		JPanel bacMarksPan = new JPanel();
		bacPan.add(bacMarksPan, BorderLayout.CENTER);
		bacMarksPan.setLayout(new GridLayout(10, 2, 0, 0));

		JPanel panel = new JPanel();
		bacMarksPan.add(panel);

		JLabel lblMath = new JLabel("Math: ");
		panel.add(lblMath);

		JPanel panel_1 = new JPanel();
		bacMarksPan.add(panel_1);

		tfMath = new JTextField();
		panel_1.add(tfMath);
		tfMath.setColumns(10);

		JPanel panel_2 = new JPanel();
		bacMarksPan.add(panel_2);

		JLabel lblArabic = new JLabel("Arabic: ");
		panel_2.add(lblArabic);

		JPanel panel_3 = new JPanel();
		bacMarksPan.add(panel_3);

		tfArab = new JTextField();
		panel_3.add(tfArab);
		tfArab.setColumns(10);

		JPanel panel_4 = new JPanel();
		bacMarksPan.add(panel_4);

		JLabel lblFrench = new JLabel("French: ");
		panel_4.add(lblFrench);

		JPanel panel_5 = new JPanel();
		bacMarksPan.add(panel_5);

		tfFrench = new JTextField();
		panel_5.add(tfFrench);
		tfFrench.setColumns(10);

		JPanel panel_6 = new JPanel();
		bacMarksPan.add(panel_6);

		JLabel lblEnglish = new JLabel("English: ");
		panel_6.add(lblEnglish);

		JPanel panel_7 = new JPanel();
		bacMarksPan.add(panel_7);

		tfEnglish = new JTextField();
		panel_7.add(tfEnglish);
		tfEnglish.setColumns(10);

		JPanel panel_8 = new JPanel();
		bacMarksPan.add(panel_8);

		JLabel lblAmasigh = new JLabel("Amasigh: ");
		panel_8.add(lblAmasigh);

		JPanel panel_9 = new JPanel();
		bacMarksPan.add(panel_9);

		tfAmasigh = new JTextField();
		panel_9.add(tfAmasigh);
		tfAmasigh.setColumns(10);

		JPanel panel_10 = new JPanel();
		bacMarksPan.add(panel_10);

		JLabel lblPhisics = new JLabel("Phisics: ");
		panel_10.add(lblPhisics);

		JPanel panel_11 = new JPanel();
		bacMarksPan.add(panel_11);

		tfPhisic = new JTextField();
		panel_11.add(tfPhisic);
		tfPhisic.setColumns(10);

		JPanel panel_12 = new JPanel();
		bacMarksPan.add(panel_12);

		JLabel lblScience = new JLabel("Science: ");
		panel_12.add(lblScience);

		JPanel panel_13 = new JPanel();
		bacMarksPan.add(panel_13);

		tfScien = new JTextField();
		panel_13.add(tfScien);
		tfScien.setColumns(10);

		JPanel panel_14 = new JPanel();
		bacMarksPan.add(panel_14);

		JLabel lblHistoireGeo = new JLabel("Histoire & Geo: ");
		panel_14.add(lblHistoireGeo);

		JPanel panel_15 = new JPanel();
		bacMarksPan.add(panel_15);

		tfHGeo = new JTextField();
		panel_15.add(tfHGeo);
		tfHGeo.setColumns(10);

		JPanel panel_16 = new JPanel();
		bacMarksPan.add(panel_16);

		JLabel lblPhilosophy = new JLabel("Philosophy:");
		panel_16.add(lblPhilosophy);

		JPanel panel_17 = new JPanel();
		bacMarksPan.add(panel_17);

		tfPhilosophy = new JTextField();
		panel_17.add(tfPhilosophy);
		tfPhilosophy.setColumns(10);

		JPanel panel_18 = new JPanel();
		bacMarksPan.add(panel_18);

		JLabel lblIslamicEducation = new JLabel("Islamic Education: ");
		panel_18.add(lblIslamicEducation);

		JPanel panel_19 = new JPanel();
		bacMarksPan.add(panel_19);

		tfIslam = new JTextField();
		panel_19.add(tfIslam);
		tfIslam.setColumns(10);

		JPanel modules = new JPanel();
		modules.setMinimumSize(new Dimension(100, 10));
		modules.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
		modBacPan.add(modules, BorderLayout.WEST);
		modules.setLayout(new BorderLayout(0, 0));

		JPanel selectModPan = new JPanel();
		modules.add(selectModPan, BorderLayout.NORTH);

		JLabel lblSelectModules = new JLabel("select modules");
		selectModPan.add(lblSelectModules);

		listMods = new JList<>(dlm);
		listMods.setBorder(new EmptyBorder(3, 3, 3, 3));

		JScrollPane modsScroll = new JScrollPane(listMods);
		modsScroll.setPreferredSize(new Dimension(100, 300));
		modules.add(modsScroll, BorderLayout.CENTER);

		JPanel buttons = new JPanel();
		buttons.setBorder(new MatteBorder(1, 0, 0, 0, (Color) new Color(0, 0, 0)));
		modBacPan.add(buttons, BorderLayout.SOUTH);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(ae -> onNext());
		buttons.add(btnNext);

		kolbPan = new JPanel();
		layers.add(kolbPan, "kolbPan");
		kolbPan.setLayout(new BorderLayout(0, 0));

		JPanel panKolbTitle = new JPanel();
		panKolbTitle.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		kolbPan.add(panKolbTitle, BorderLayout.NORTH);

		JLabel lblKolbTest = new JLabel("Kolb Test: ");
		panKolbTitle.add(lblKolbTest);

		panLSIQuestions = new JPanel();

		JScrollPane scrollPane = new JScrollPane(panLSIQuestions);
		panLSIQuestions.setLayout(new BoxLayout(panLSIQuestions, BoxLayout.Y_AXIS));
		kolbPan.add(scrollPane, BorderLayout.CENTER);

		JPanel kolbSendBtn = new JPanel();
		kolbPan.add(kolbSendBtn, BorderLayout.SOUTH);

		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(ae -> onSend());
		kolbSendBtn.add(btnSend);
		setVisible(true);
	}

	/* Cheks and collects the selected modules & bac marks. */
	private void onNext()
	{
		 bacMarks = new TreeMap<>();
		 try
		 {
			 bacMarks.put("math", Double.parseDouble(tfMath.getText()));
			 bacMarks.put("arab", Double.parseDouble(tfArab.getText()));
			 bacMarks.put("french", Double.parseDouble(tfFrench.getText())) ;
			 bacMarks.put("english", Double.parseDouble(tfEnglish.getText()));
			 bacMarks.put("amasigh", Double.parseDouble(tfAmasigh.getText()));
			 bacMarks.put("phisic", Double.parseDouble(tfPhisic.getText()));
			 bacMarks.put("science", Double.parseDouble(tfScien.getText()));
			 bacMarks.put("histGeo", Double.parseDouble(tfHGeo.getText()));
			 bacMarks.put("philosophy", Double.parseDouble(tfPhilosophy.getText()));
			 bacMarks.put("islamic", Double.parseDouble(tfIslam.getText()));

			 boolean valid = ! bacMarks.keySet().stream().anyMatch(matter ->
			 bacMarks.get(matter).doubleValue() < 0.0 ||
			 20.0 < bacMarks.get(matter).doubleValue());

			 if (valid) // we handle the modules then
			 {
				selectedMods = new ArrayList<>();
				int[] selected =  listMods.getSelectedIndices();
				if (selected != null)
				{
					for (int modIndx : selected)
					{
						Module module = modulesBeans.stream().filter(mod ->
						mod.getTitle().equals(dlm.get(modIndx))).findFirst().get();
						selectedMods.add(module);
					}
				}
				showLSI();
			 }
			 else // show error
			 {
				 JOptionPane.showMessageDialog(null, "Some marks are not between 0 and 20",
						 "invalid BAC mark", JOptionPane.ERROR_MESSAGE);
			 }
		 }
		 catch (NumberFormatException e)
		 {
			 JOptionPane.showMessageDialog(null, "Some marks are not numbers",
					 "invalid BAC mark", JOptionPane.ERROR_MESSAGE);
		 }
	}

	private void onSend()
	{
		//kolbChoicesValues
		int count = 0, seg = 0;
		for (LSIQuestion q : lsi.getQuestions())
		{
			seg = 4*count;
			for (int i = seg; i < seg + 3; i++)
			{
				q.getValues().add(kolbChoicesValues.get(i).isSelected() ? new Integer(4) : new Integer(0));
			}
			count++;
		}

		handler.initProfile(selectedMods, bacMarks, lsi);
		JOptionPane.showMessageDialog(null, "Information well sended",
				 "well send", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	}

	private void showLSI()
	{
		List<JPanel> questPan = new ArrayList<>(),
					 contentQuestPan = new ArrayList<>(),
					 choicesPan = new ArrayList<>();

		for (LSIQuestion q : lsi.getQuestions())
		{
			questPan.add(0, new JPanel());
			questPan.get(0).setLayout(new BorderLayout());
			contentQuestPan.add(0, new JPanel());
			contentQuestPan.get(0).setBackground(Color.BLACK);
			JLabel lblCntnt = new JLabel(q.getNum()+") "+q.getContent()+":");
			lblCntnt.setForeground(Color.WHITE);
			lblCntnt.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentQuestPan.get(0).add(lblCntnt);

			questPan.get(0).add(contentQuestPan.get(0), BorderLayout.NORTH);
			choicesPan.add(0, new JPanel());
			choicesPan.get(0).setLayout(new BoxLayout(choicesPan.get(0), BoxLayout.PAGE_AXIS));
			List<JPanel> chPan = new ArrayList<>();
			ButtonGroup bg = new ButtonGroup();
			for (String ch : q.getChoices())
			{
				chPan.add(0, new JPanel());
				JRadioButton rb = new JRadioButton(ch, false);
				bg.add(rb);
				kolbChoicesValues.add(rb);
				chPan.get(0).add(kolbChoicesValues.get(kolbChoicesValues.size()-1));
				//chPan.get(0).add(new JLabel(i+"- "+ch));
				//kolbChoicesValues.add(new JTextField(10));
				choicesPan.get(0).add(chPan.get(0));
			}
			bg.getElements().nextElement().setSelected(true);

			questPan.get(0).add(choicesPan.get(0), BorderLayout.CENTER);
			panLSIQuestions.add(questPan.get(0));
		}
		panLSIQuestions.validate();
		((CardLayout) layers.getLayout()).show(layers, "kolbPan");
	}

	private void initModsList()
	{
		for (Module m : modulesBeans)
		{
			dlm.addElement(m.getTitle());
		}
	}
}
