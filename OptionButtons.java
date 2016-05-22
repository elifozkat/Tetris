import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class OptionButtons extends JPanel {

	Options parent;

	public OptionButtons(Options p) {
		super();
		this.parent = p;

		setLayout(new GridLayout(5, 1, 0, 4));

		JButton b1 = new JButton("Key Configuration");
		b1.setFont(new Font("Dialog.bold", 0, 20));
		JButton b2 = new JButton("Board Size");
		b2.setFont(new Font("Dialog.bold", 0, 20));
		JButton b3 = new JButton("Select Block Types");
		b3.setFont(new Font("Dialog.bold", 0, 20));
		JButton b4 = new JButton("Select Level");
		b4.setFont(new Font("Dialog.bold", 0, 20));
		JButton b5 = new JButton("Set all settings to defaults");
		b5.setFont(new Font("Dialog.bold", 0, 20));

		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);

		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				KeyConfig frame = new KeyConfig(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setEnabled(false);
			}
		});

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				BoardSize frame = new BoardSize(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setEnabled(false);
			}
		});

		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				BlockOptions frame = new BlockOptions(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setEnabled(false);
			}
		});

		b4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Level frame = new Level(parent);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
				parent.setEnabled(false);
			}
		});

		b5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] keys = { 37, 39, 40, 38, 32 };
				KeyConfig.saveKeystoTXT(keys);
				BoardSize.saveSizeToTXT(10, 15);
				BlockOptions.saveBlockOptionToTXT(BlockOptions.ONLY_TETRIMINOS);
				Level.saveLevelToTXT(1);
				((JButton) e.getSource()).setText("Done.");
			}
		});

		setVisible(true);
	}

}