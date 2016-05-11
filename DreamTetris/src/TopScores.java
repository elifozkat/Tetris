import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
class TopScores extends JFrame {

	String[][] scores;
	JLabel labelList;
	int topScoreCount = 5;
	int newScore;
	JLabel labelWarning;
	JTextField tfName;
	JButton buttonSaveName;
	ActionListener actLis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!tfName.getText().equals("")) {
				rearrangeScores(newScore, tfName.getText());
				saveAndClose();
			}
		}
	};

	StartWindow parent;

	public TopScores(StartWindow p) {
		super("Top Scores");
		this.parent = p;
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				
				parent.setVisible(true);
				parent.setEnabled(true);
			}
		});

		scores = new String[topScoreCount][2];
		readFromTXT();

		this.setLayout(new GridLayout(0, 1));

		labelList = new JLabel(generateList());
		labelList.setFont(new Font("Dialog.bold", 0, 25));
		add(labelList);
	}

	public TopScores(int newScore, StartWindow p) {
		super("Top Scores");
		this.parent = p;
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parent.setVisible(true);
			}
		});

		this.newScore = newScore;

		scores = new String[topScoreCount][2];
		readFromTXT();

		this.setLayout(new GridLayout(0, 1));
		if (findNewScorePos(newScore) < topScoreCount) {
			labelWarning = new JLabel(
					"Congratulations, you've made it to the top scores! Please enter your name:");
			tfName = new JTextField();
			buttonSaveName = new JButton("Save Score");
			add(labelWarning);
			add(tfName);
			add(buttonSaveName);
			buttonSaveName.addActionListener(actLis);
		} else {
			labelWarning = new JLabel("Your final score is: "
					+ String.valueOf(newScore)
					+ ". You need to practice for a while!");
			add(labelWarning);
			labelList = new JLabel(generateList());
			add(labelList);
		}
	}

	// Generates a list from scores[]
	protected String generateList() {
		String list = "<html>";
		for (int k = 0; k < topScoreCount; k++) {
			list += scores[k][0] + ": ";
			list += scores[k][1] + "<br>";
		}
		return list + "</html>";
	}

	// Reads highscores.txt into scores[]
	protected void readFromTXT() {
		try {
			Scanner scoreScan = new Scanner(new File("highscores.txt"), "UTF-8");
			for (int k = 0; k < topScoreCount; k++) {
				scores[k][0] = scoreScan.nextLine();
				scores[k][1] = scoreScan.nextLine();
			}
			scoreScan.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			for (int k = 0; k < topScoreCount; k++) {
				scores[k][0] = "John Doe";
				scores[k][1] = "0";
			}
			try {
				PrintWriter writer = new PrintWriter(("highscores.txt"),
						"utf-8");
				for (int n = 0; n < 5; n++) {
					writer.println(scores[n][0]);
					writer.println(scores[n][1]);
				}
				writer.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	// Finds the ranking of a new score
	protected int findNewScorePos(int score) {
		int count = 0;
		for (int k = 0; k < topScoreCount; k++) {
			if (Integer.parseInt(scores[k][1]) >= score)
				count++;
		}
		return count;
	}

	// Reorders scores[] after adding newScore to scores[]
	protected void rearrangeScores(Integer newScore, String newName) {
		int newPos = findNewScorePos(newScore);
		for (int k = 3; k >= newPos; k--) {
			scores[k + 1][0] = scores[k][0];
			scores[k + 1][1] = scores[k][1];
		}
		scores[newPos][0] = newName;
		scores[newPos][1] = newScore.toString();
	}

	protected void saveAndClose() {
		try {
			PrintWriter writer = new PrintWriter(("highscores.txt"), "utf-8");
			for (int n = 0; n < 5; n++) {
				writer.println(scores[n][0]);
				writer.println(scores[n][1]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			TopScores frame = new TopScores(this.parent);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			this.dispose();
		}
	}

}