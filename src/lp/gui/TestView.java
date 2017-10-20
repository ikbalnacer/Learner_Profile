package lp.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

import lp.struct.Exo;
import lp.struct.Question;
import lp.struct.Test;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class TestView extends JFrame {

	private JPanel contentPane;
	private JPanel testContent;
	private JLabel lblTest;
	private JLabel lblNotifications;

	private Test test;

	/**
	 * Create the frame.
	 */
	public TestView(Test test)
	{
		this.test = test;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 674, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel testHeader = new JPanel();
		contentPane.add(testHeader, BorderLayout.NORTH);
		testHeader.setLayout(new BoxLayout(testHeader, BoxLayout.Y_AXIS));

		JPanel titlePan = new JPanel();
		titlePan.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		testHeader.add(titlePan);

		lblTest = new JLabel("Test:");
		lblTest.setFont(new Font("Tahoma", Font.BOLD, 14));
		titlePan.add(lblTest);

		JPanel notifPan = new JPanel();
		notifPan.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		testHeader.add(notifPan);

		lblNotifications = new JLabel("Notifications");
		lblNotifications.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNotifications.setForeground(Color.RED);
		notifPan.add(lblNotifications);

		JScrollPane cntntScroll = new JScrollPane();
		contentPane.add(cntntScroll, BorderLayout.CENTER);

		testContent = new JPanel();
		cntntScroll.setViewportView(testContent);
		testContent.setLayout(new BoxLayout(testContent, BoxLayout.Y_AXIS));

		JPanel southPan = new JPanel();
		southPan.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
		contentPane.add(southPan, BorderLayout.SOUTH);

		JButton btnSend = new JButton("Send");
		southPan.add(btnSend);
		showTest();
		setVisible(true);
	}

	private void showTest()
	{
		lblTest.setText("Test"+test.getId());
		for (Exo exo : test.getExos())
		{
			JPanel exoTitlePan = new JPanel();
			exoTitlePan.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			JLabel lblExoTitle = new JLabel("EXO"+exo.getNum());
			exoTitlePan.add(lblExoTitle);
			testContent.add(exoTitlePan);

			for (Question q : exo.getQuestions())
			{
				JPanel cntntQuestPan = new JPanel();
				cntntQuestPan.setBackground(Color.BLACK);
				cntntQuestPan.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(0, 0, 0)));
				JLabel lblCntntQuest = new JLabel("Q"+q.getNum()+" ("+q.getPoints()+" points): "+q.getContent());
				lblCntntQuest.setForeground(Color.WHITE);
				lblCntntQuest.setFont(new Font("Tahoma", Font.BOLD, 12));
				cntntQuestPan.add(lblCntntQuest);
				testContent.add(cntntQuestPan);

				JPanel responseSpacePan = new JPanel();
				responseSpacePan.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));

				if (q.getType() == Question.DIRECT)
				{
					JTextArea respArea = new JTextArea(8, 50);
					respArea.setBorder(new MatteBorder(1, 1, 2, 2, (Color) new Color(0, 0, 0)));
					responseSpacePan.add(respArea);
				}
				else // type = QCM
				{
					responseSpacePan.setLayout(new BoxLayout(responseSpacePan, BoxLayout.Y_AXIS));
					ButtonGroup bg = new ButtonGroup();

					for (String ch : q.getChoices())
					{
						JPanel chPan = new JPanel();
						JRadioButton rd = new JRadioButton(ch, false);
						bg.add(rd);
						chPan.add(rd);
						responseSpacePan.add(chPan);
					}
					bg.getElements().nextElement().setSelected(true);
				}
				testContent.add(responseSpacePan);
			}
		}
		testContent.validate();
	}

	public static Test fill()
	{
		List<Question> qus = new ArrayList<>();
		List<String> chs = new ArrayList<>();
		chs.add("choice1");
		chs.add("choice2");
		chs.add("choice3");
		chs.add("choice4");

		Question q11 = new Question(1, 2, Question.DIRECT, "how do you?", null);
		Question q12 = new Question(2, 2, Question.QCM, "where is this?", chs);
		Question q13 = new Question(3, 2, Question.DIRECT, "Ahmed or Mu?", null);

		qus.add(q11);
		qus.add(q12);
		qus.add(q13);

		List<Exo> lex = new ArrayList<>();
		Exo e1 = new Exo(1, null, null, qus);
		Exo e2 = new Exo(2, null, null, qus);
		Exo e3 = new Exo(3, null, null, qus);

		lex.add(e1);
		lex.add(e2);
		lex.add(e3);

		Test t = new Test(1, lex, null, null, null);
		return t;
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> {
			TestView tv = new TestView(fill());
			tv.setVisible(true);
		});
	}
}
