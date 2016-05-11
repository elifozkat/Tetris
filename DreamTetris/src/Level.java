import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
class Level extends JFrame {

	JLabel selectLevel;
	Options parent;

	public Level(Options p) {
		super("Level");
		this.parent = p;
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parent.setEnabled(true);
			}
		});

		setLayout(new GridLayout(6, 1, 0, 6));

		int temp = getLevelFromTXT();

		selectLevel = new JLabel("Current level: " + temp
				+ ". Please select a level.");
		selectLevel.setFont(new Font("Dialog.bold", 0, 13));
		add(selectLevel);

		JButton l1 = new JButton("Level 1");
		l1.setFont(new Font("Dialog.bold", 0, 14));
		JButton l2 = new JButton("Level 2");
		l2.setFont(new Font("Dialog.bold", 0, 14));
		JButton l3 = new JButton("Level 3");
		l3.setFont(new Font("Dialog.bold", 0, 14));
		JButton l4 = new JButton("Level 4");
		l4.setFont(new Font("Dialog.bold", 0, 14));
		JButton l5 = new JButton("Level 5");
		l5.setFont(new Font("Dialog.bold", 0, 14));

		add(l1);
		add(l2);
		add(l3);
		add(l4);
		add(l5);

		l1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLevel.setText("Current Level: 1");
				saveLevelToTXT(1);
			}
		});

		l2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLevel.setText("Current Level: 2");
				saveLevelToTXT(2);
			}
		});

		l3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLevel.setText("Current Level: 3");
				saveLevelToTXT(3);
			}
		});

		l4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLevel.setText("Current Level: 4");
				saveLevelToTXT(4);
			}
		});

		l5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectLevel.setText("Current Level: 5");
				saveLevelToTXT(5);
			}
		});

	}

	protected static int getLevelFromTXT() {
		int a = 0;
		try {
			Scanner levelScan = new Scanner(new File("level.txt"));
			a = levelScan.nextInt();
			levelScan.close();
			if (a < 1 || a > 5) {
				throw new FileNotFoundException();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			saveLevelToTXT(1);
			return 1;
		}
		return a;
	}

	protected static void saveLevelToTXT(int level) {
		try {
			PrintWriter writinglevel5 = new PrintWriter(("level.txt"), "utf-8");
			writinglevel5.print(level);
			writinglevel5.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}