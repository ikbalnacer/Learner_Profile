package lp.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.CardLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

import lp.naai.StudentNAAI;
import lp.struct.Chapter;
import lp.struct.Competence;
import lp.struct.Module;

public class CourseView extends JFrame
{
	private StudentNAAI handler;

	private JPanel contentPane;
	private JPanel modsList;
	private List<Module> allowedMods;
	private JPanel layers;
	private JPanel chapsList;
	private List<Chapter> allowedChapters;

	/**
	 * Create the frame.
	 */
	public CourseView(StudentNAAI handler, List<Module> allowedMods)
	{
		System.out.println("size = "+allowedMods.size());
		this.handler = handler;
		this.allowedMods = allowedMods;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 683, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		layers = new JPanel();
		contentPane.add(layers, BorderLayout.CENTER);
		layers.setLayout(new CardLayout(0, 0));

		JPanel modules = new JPanel();
		layers.add(modules, "modules");
		modules.setLayout(new BorderLayout(0, 0));

		JScrollPane modsScroll = new JScrollPane();
		modules.add(modsScroll, BorderLayout.CENTER);

		modsList = new JPanel();
		modsScroll.setViewportView(modsList);
		modsList.setLayout(new BoxLayout(modsList, BoxLayout.Y_AXIS));
		showModules();

		JPanel modsBanner = new JPanel();
		modules.add(modsBanner, BorderLayout.NORTH);

		JLabel lblTitle = new JLabel("Select a module to show its chapters");
		lblTitle.setForeground(Color.BLUE);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		modsBanner.add(lblTitle);

		JPanel chapters = new JPanel();
		layers.add(chapters, "chapters");
		chapters.setLayout(new BorderLayout(0, 0));

		JPanel titleChapters = new JPanel();
		chapters.add(titleChapters, BorderLayout.NORTH);

		JLabel lblDownloadChapters = new JLabel("Download Chapters");
		lblDownloadChapters.setForeground(Color.BLUE);
		lblDownloadChapters.setFont(new Font("Tahoma", Font.BOLD, 14));
		titleChapters.add(lblDownloadChapters);

		JPanel southPan = new JPanel();
		chapters.add(southPan, BorderLayout.SOUTH);

		JButton btnBackToModules = new JButton("Back to modules");
		btnBackToModules.addActionListener(ae ->
		((CardLayout) layers.getLayout()).show(layers, "modules"));
		southPan.add(btnBackToModules);

		JScrollPane chapsScroll = new JScrollPane();
		chapters.add(chapsScroll, BorderLayout.CENTER);

		chapsList = new JPanel();
		chapsScroll.setViewportView(chapsList);
		chapsList.setLayout(new BoxLayout(chapsList, BoxLayout.Y_AXIS));
		setVisible(true);
	}

	private void showModules()
	{
		JPanel headers = new JPanel(new GridLayout(1, 5));
		headers.add(new JLabel("Title"));
		headers.add(new JLabel("Hours"));
		headers.add(new JLabel("Pre-requisites"));
		headers.add(new JLabel("Competences"));
		headers.add(new JLabel("Download"));
		headers.setBorder(BorderFactory.createBevelBorder(1));
		modsList.add(headers);

		for (Module m : allowedMods)
		{
			JPanel mPan = new JPanel(new GridLayout(1, 5));
			mPan.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));

			List<JPanel> dataPans = new ArrayList<>();
			for (int i = 0; i < 5; i++)
			{
				dataPans.add(0, new JPanel());
				mPan.add(dataPans.get(0));
			}
			JLabel title = new JLabel(m.getTitle()),
				   hours = new JLabel(m.getHours()+"h");
			JComboBox<String> pre  = new JComboBox<>(),
							  post = new JComboBox<>();
			pre.addItem("Pre-Requisites:");
			post.addItem("Competences:");
			for (Competence c : m.getPreRequisite())
			{
				pre.addItem(c.getName());
			}
			for (Competence c : m.getObjectives())
			{
				post.addItem(c.getName());
			}
			JButton get = new JButton("get chapters");
			get.addActionListener(ae -> handler.getChapters(
					allowedMods.stream().filter(mod -> title.getText().equals(mod.getTitle()))
					.findFirst().get()));
			dataPans.get(4).add(title);
			dataPans.get(3).add(hours);
			dataPans.get(2).add(pre);
			dataPans.get(1).add(post);
			dataPans.get(0).add(get);

			modsList.add(mPan);
			modsList.validate();
		}
	}

	/* Show the layer that exposes the chapters for a module*/
	public void showChapters(List<Chapter> chapters)
	{
		allowedChapters = chapters;
		chapsList.removeAll();
		JPanel headers = new JPanel(new GridLayout(1, 4));
		headers.add(new JLabel("Chapter"));
		headers.add(new JLabel("Title"));
		headers.add(new JLabel("Digest"));
		headers.add(new JLabel("Download"));
		headers.setBorder(BorderFactory.createBevelBorder(1));
		chapsList.add(headers);

		for (Chapter c : allowedChapters)
		{
			JPanel cPan = new JPanel(new GridLayout(1, 4));
			cPan.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
			List<JPanel> dataPans = new ArrayList<>();
			for (int i = 0; i < 4; i++)
			{
				dataPans.add(0, new JPanel());
				cPan.add(dataPans.get(0));
			}

			JLabel num = new JLabel("Chapter"+c.getNum()),
					   titleC = new JLabel(c.getTitle());
			JTextArea digest = new JTextArea(c.getDigest(), 5, 15);
			JButton download = new JButton("download");
			download.addActionListener(ae -> handler.download(c));

			dataPans.get(3).add(num);
			dataPans.get(2).add(titleC);
			dataPans.get(1).add(new JScrollPane(digest));
			dataPans.get(0).add(download);

			chapsList.add(cPan);
		}
		chapsList.validate();
		((CardLayout) layers.getLayout()).show(layers, "chapters");
	}
}
