import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class StartButtons extends JPanel {

	StartWindow parent;

	public StartButtons(StartWindow p) {
		super();
		this.parent = p;

		setLayout(new GridLayout(4, 1, 0, 3));

		JLabel pic = new JLabel(new ImageIcon("DreamJPG.jpg"));
		add(pic);

		JButton b1 = new JButton("Play New Game");
		b1.setFont(new Font("Dialog.bold", 0, 25));
		JButton b2 = new JButton("Options");
		b2.setFont(new Font("Dialog.bold", 0, 25));
		JButton b3 = new JButton("Top Scores");
		b3.setFont(new Font("Dialog.bold", 0, 25));

		add(b1);
		add(b2);
		add(b3);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				GameWindow frame = new GameWindow(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setVisible(false);
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Options frame = new Options(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setEnabled(false);
			}
		});

		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				TopScores frame = new TopScores(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setEnabled(false);
			}
		});

		setVisible(true);
	}

}