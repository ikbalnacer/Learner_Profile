package lp.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;
import javax.swing.SpringLayout;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;

import lp.naai.TeacherNAAI;
import lp.struct.Exo;
import lp.struct.Question;
import lp.struct.Test;
import lp.struct.XMLHandler;

import java.awt.Font;
import java.util.Arrays;

public class TestCreator extends JFrame
{
	private TeacherNAAI handler;
	private JPanel contentPane;
	private JTextField nbrExoTxt;
	private String[] headers;
	private JTable questNbrTab;
	private DefaultTableModel tm;
	private JPanel layers;
	private JTextField markTextField;
	private JLabel lblQuestion;
	private JLabel exoNumLab;
	private JLabel questNumLab;

	private String dir;
	private int maxExo = 5;
	private int testId;
	private Test test;
	private int currExo;
	private int currQuest;

	private JTextArea questCntnttextArea;
	private JLabel lblNotifications;
	private JRadioButton rdbtnDirect;
	private JRadioButton rdbtnQcm;
	private JTextArea choicestextArea;
	private JButton btnStoreTest;
	private JButton btnNextQuestion;
	private JButton btnPreviousQuestion;
	private JFormattedTextField testDateTxtFld, testTimeTxtFld;

	/**
	 * Create the frame.
	 */
	public TestCreator(TeacherNAAI handler, String dir, int testId)
	{
		this.dir = dir;
		this.testId = testId;
		headers = new String[]{"exo n°", "nbr question", "Kolb processing", "Kolb perceiving"};
		tm = new DefaultTableModel(headers, 0);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 622, 368);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		layers = new JPanel();
		contentPane.add(layers, BorderLayout.CENTER);
		layers.setLayout(new CardLayout(0, 0));

		JPanel exoNbrLayer = new JPanel();
		layers.add(exoNbrLayer, "exoNbrLayer");
		exoNbrLayer.setLayout(new BorderLayout(0, 0));

		JPanel nbrExoLabTxt = new JPanel();
		exoNbrLayer.add(nbrExoLabTxt, BorderLayout.NORTH);

		JLabel nbrExoLab = new JLabel("Enter number of exo:");
		nbrExoLabTxt.add(nbrExoLab);

		nbrExoTxt = new JTextField();
		nbrExoLabTxt.add(nbrExoTxt);
		nbrExoTxt.setColumns(2);

		JLabel lblTestDate = new JLabel("test date (D-M-Y)");
		nbrExoLabTxt.add(lblTestDate);

		testDateTxtFld = new JFormattedTextField(new SimpleDateFormat("d-m-y"));
		nbrExoLabTxt.add(testDateTxtFld);
		testDateTxtFld.setColumns(10);

		JLabel lblTestTime = new JLabel("test time (H:M:S)");
		nbrExoLabTxt.add(lblTestTime);

		testTimeTxtFld = new JFormattedTextField(DateFormat.getTimeInstance());
		nbrExoLabTxt.add(testTimeTxtFld);
		testTimeTxtFld.setColumns(10);

		JPanel nextBtnPan = new JPanel();
		exoNbrLayer.add(nextBtnPan, BorderLayout.SOUTH);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(ae -> onApply());
		nextBtnPan.add(btnApply);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(ae -> onNext());
		nextBtnPan.add(btnNext);

		JScrollPane scrollPane = new JScrollPane();
		exoNbrLayer.add(scrollPane, BorderLayout.CENTER);

		questNbrTab = new JTable(tm);
		scrollPane.setViewportView(questNbrTab);

		JPanel questDetails = new JPanel();
		layers.add(questDetails, "questDetails");
		questDetails.setLayout(new BorderLayout(0, 0));

		JPanel questNumAndType = new JPanel();
		questNumAndType.setBorder(new LineBorder(Color.DARK_GRAY));
		questDetails.add(questNumAndType, BorderLayout.NORTH);

		JLabel lblExo = new JLabel("Exo:");
		questNumAndType.add(lblExo);

		exoNumLab = new JLabel("()");
		questNumAndType.add(exoNumLab);

		lblQuestion = new JLabel("Question:");
		questNumAndType.add(lblQuestion);

		questNumLab = new JLabel("()");
		questNumAndType.add(questNumLab);


		rdbtnQcm = new JRadioButton("QCM");
		questNumAndType.add(rdbtnQcm);

		rdbtnDirect = new JRadioButton("Direct");
		questNumAndType.add(rdbtnDirect);
		rdbtnDirect.setSelected(true);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnQcm);
		bg.add(rdbtnDirect);

		JLabel lblQuestionPoints = new JLabel("Question points:");
		questNumAndType.add(lblQuestionPoints);

		markTextField = new JTextField();
		questNumAndType.add(markTextField);
		markTextField.setColumns(10);

		JPanel nxtPrevQuestPan = new JPanel();
		questDetails.add(nxtPrevQuestPan, BorderLayout.SOUTH);

		btnStoreTest = new JButton("store test");
		btnStoreTest.addActionListener(ae -> handler.createTest(test));
		btnStoreTest.setEnabled(false);
		nxtPrevQuestPan.add(btnStoreTest);

		btnNextQuestion = new JButton("next question");
		btnNextQuestion.addActionListener(ae -> onNextQuest());
		nxtPrevQuestPan.add(btnNextQuestion);

		btnPreviousQuestion = new JButton("previous question");
		btnPreviousQuestion.addActionListener(ae -> onPreviousQuest());
		btnPreviousQuestion.setEnabled(false);
		nxtPrevQuestPan.add(btnPreviousQuestion);

		JButton btnBackToExos = new JButton("back to exos");
		btnBackToExos.addActionListener(ae -> onBack());
		nxtPrevQuestPan.add(btnBackToExos);

		JPanel contentChoices = new JPanel();
		contentChoices.setBorder(new MatteBorder(0, 1, 1, 1, (Color) new Color(0, 0, 0)));
		questDetails.add(contentChoices, BorderLayout.CENTER);
		SpringLayout sl_contentChoices = new SpringLayout();
		contentChoices.setLayout(sl_contentChoices);

		JLabel lblQuestionContent = new JLabel("Question content:");
		sl_contentChoices.putConstraint(SpringLayout.WEST, lblQuestionContent, 9, SpringLayout.WEST, contentChoices);
		contentChoices.add(lblQuestionContent);

		questCntnttextArea = new JTextArea();
		sl_contentChoices.putConstraint(SpringLayout.SOUTH, lblQuestionContent, -6, SpringLayout.NORTH, questCntnttextArea);
		sl_contentChoices.putConstraint(SpringLayout.NORTH, questCntnttextArea, 57, SpringLayout.NORTH, contentChoices);
		sl_contentChoices.putConstraint(SpringLayout.WEST, questCntnttextArea, 9, SpringLayout.WEST, contentChoices);
		sl_contentChoices.putConstraint(SpringLayout.EAST, questCntnttextArea, -11, SpringLayout.EAST, contentChoices);
		questCntnttextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentChoices.add(questCntnttextArea);

		JLabel lblChoices = new JLabel("choices(only in case of QCM, split your choices by \"/\"):");
		sl_contentChoices.putConstraint(SpringLayout.SOUTH, questCntnttextArea, -6, SpringLayout.NORTH, lblChoices);
		sl_contentChoices.putConstraint(SpringLayout.WEST, lblChoices, 9, SpringLayout.WEST, contentChoices);
		sl_contentChoices.putConstraint(SpringLayout.SOUTH, lblChoices, -93, SpringLayout.SOUTH, contentChoices);
		contentChoices.add(lblChoices);

		choicestextArea = new JTextArea();
		sl_contentChoices.putConstraint(SpringLayout.NORTH, choicestextArea, 6, SpringLayout.SOUTH, lblChoices);
		sl_contentChoices.putConstraint(SpringLayout.WEST, choicestextArea, 9, SpringLayout.WEST, contentChoices);
		sl_contentChoices.putConstraint(SpringLayout.SOUTH, choicestextArea, -18, SpringLayout.SOUTH, contentChoices);
		sl_contentChoices.putConstraint(SpringLayout.EAST, choicestextArea, -11, SpringLayout.EAST, contentChoices);
		choicestextArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentChoices.add(choicestextArea);

		lblNotifications = new JLabel("- notifications -");
		sl_contentChoices.putConstraint(SpringLayout.NORTH, lblNotifications, 10, SpringLayout.NORTH, contentChoices);
		sl_contentChoices.putConstraint(SpringLayout.EAST, lblNotifications, -245, SpringLayout.EAST, contentChoices);
		lblNotifications.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNotifications.setForeground(Color.RED);
		contentChoices.add(lblNotifications);
		setVisible(true);
	}

	private void onApply()
	{
		int nbrExo = 0;

		if (nbrExoTxt.getText().matches("[0-9]+"))
		{
			nbrExo = Integer.parseInt(nbrExoTxt.getText());
			nbrExo = (nbrExo > maxExo || nbrExo < 1)? maxExo : nbrExo;
		}
		else
		{
			nbrExo = maxExo;
		}

		Object[][] data = new Object[nbrExo][4];
		for (int i = 0; i < data.length; i++)
		{
			data[i][0] = (i+1)+"";
			data[i][1] = "1";
			data[i][2] = "reflective";
			data[i][3] = "abstract";
			tm.addRow(data[i]);
		}
		tm = new DefaultTableModel(data, headers);
		questNbrTab.setModel(tm);
	}

	private void onNext()
	{
		if (tm.getRowCount() <= 0)
		{
			JOptionPane.showMessageDialog(null, "enter number of exo first",
					"Invalid format", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			test = new Test();
			test.setId(testId);
			LocalDateTime testDate = strToDate(testDateTxtFld.getText(), testTimeTxtFld.getText());
			if (testDate == null)
			{
				JOptionPane.showMessageDialog(null, "Date format should be 'DD-MM-YYYY'",
						"Invalid format", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				test.setTestDate(testDate);
				for (int i = 0; i < tm.getRowCount(); i++) //creating exos for the test
				{
					Exo exo = new Exo();
					exo.setNum(i+1);
					exo.setKolbProcessing((String)tm.getValueAt(i, 2));
					exo.setKolbPerceiving((String)tm.getValueAt(i, 3));
					int exoNbrQuest = 6;

					if (((String)tm.getValueAt(i, 1)).matches("[0-9]+"))
					{
						exoNbrQuest = Integer.parseInt((String)tm.getValueAt(i, 1));
					}

					for (int j = 0; j < exoNbrQuest; j++) //creating questions for the exo
					{
						Question question = new Question();
						question.setNum(j+1);
						exo.getQuestions().add(question);
					}
					test.getExos().add(exo);
				}
				currExo = 1;
				currQuest = 1;
				exoNumLab.setText("(1/"+test.getExos().size()+")");
				questNumLab.setText("(1/"+test.getExos().get(0).getQuestions().size()+")");
				btnStoreTest.setEnabled(false);
				btnPreviousQuestion.setEnabled(false);
				btnNextQuestion.setEnabled(true);

				((CardLayout)layers.getLayout()).show(layers, "questDetails");
			}
		}
	}

	private LocalDateTime strToDate(String strDate, String strTime)
	{
		String[] date = strDate.split("-");
		String[] time = strTime.split(":");
		LocalDateTime testDate = LocalDateTime.of(
				Integer.parseInt(date[2]), //year
				Integer.parseInt(date[1]), // month
				Integer.parseInt(date[0]), // day of month
				Integer.parseInt(time[0]), // hour
				Integer.parseInt(time[1]) // minute
				);
		return testDate;
	}

	private void onBack()
	{
		((CardLayout)layers.getLayout()).show(layers, "exoNbrLayer");
	}

	private void onNextQuest()
	{
		Question question = test.getExos().get(currExo-1).getQuestions().get(currQuest-1);
		boolean ok = false;

		String markStr = markTextField.getText();
		String cntntStr = questCntnttextArea.getText();

		if (markStr.matches("[0-9]+") && cntntStr != null && !cntntStr.isEmpty())
		{
			question.setPoints(Integer.parseInt(markStr));
			question.setContent(cntntStr);

			if (rdbtnQcm.isSelected())
			{
				String[] choices = choicestextArea.getText().split("/");
				if (choices.length < 2)
				{
					lblNotifications.setText("error: A QCM should have at least 2 choices");
				}
				else
				{
					boolean choicesOk = true;
					for (String choice : choices)
					{
						if (choice == null || choice.isEmpty())
						{
							choicesOk = false;
							lblNotifications.setText("error: One of your choices is empty");
							break;
						}
					}
					if (choicesOk)
					{
						question.setType(Question.QCM);
						question.setChoices(Arrays.asList(choices));
						ok = true;
					}
				}
			}
			else // Direct is selected
			{
				question.setType(Question.DIRECT);
				ok = true;
			}
		}
		else
		{
			lblNotifications.setText("syntax error: mark not a number or empty question content");
		}

		if (ok) // that means: the question's attributes are all valid, so, let's get the next one
		{
			if (currQuest == test.getExos().get(currExo-1).getQuestions().size())
			{
				currQuest = 1;
				if (currExo == test.getExos().size())
				{
					btnStoreTest.setEnabled(true);
					lblNotifications.setText("You are able to store your test now");
					btnNextQuestion.setEnabled(false);
				}
				else
				{
					currExo++;
				}
			}
			else
			{
				currQuest++;
			}
			exoNumLab.setText("("+currExo+"/"+test.getExos().size()+")");
			questNumLab.setText("("+currQuest+"/"+test.getExos().get(currExo-1).getQuestions().size()+")");
			btnPreviousQuestion.setEnabled(true);
		}
	}

	private void onPreviousQuest()
	{
		if (currQuest == 1)
		{
			currExo--;
		}
		else
		{
			currQuest--;
		}
		exoNumLab.setText("("+currExo+"/"+test.getExos().size()+")");
		questNumLab.setText("("+currQuest+"/"+test.getExos().get(currExo-1).getQuestions().size()+")");
		Question question = test.getExos().get(currExo-1).getQuestions().get(currQuest-1);
		markTextField.setText(question.getPoints()+"");
		questCntnttextArea.setText(question.getContent());
		if (Question.QCM.equals(question.getType()))
		{
			rdbtnQcm.setSelected(true);
			for (String choice : question.getChoices())
			{
				choicestextArea.append(choice+"/");
			}
		}
		else
		{
			rdbtnDirect.setSelected(true);
		}
		if (currExo == 1 && currQuest == 1)
		{
			btnPreviousQuestion.setEnabled(false);
		}
		btnNextQuestion.setEnabled(true);
		btnStoreTest.setEnabled(false);
	}
}
