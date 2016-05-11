import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
class KeyConfig extends JFrame {

	JLabel label;
	JButton[] buttons;
	int[] keys;

	Options parent;

	ActionListener actLis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			label.setText("Press the key now.");
			((JButton) arg0.getSource()).addKeyListener(listen);
		}
	};

	KeyListener listen = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			int k = 0;
			while (!buttons[k].equals(arg0.getSource())) {
				k++;
			}
			keys[k] = arg0.getKeyCode();
			KeyConfig.saveKeystoTXT(keys);
			updateButtonTexts();
			label.setText("New key is saved.");
			removeKeyListener(this);
		}
	};

	public KeyConfig(Options p) {
		super("Key Configuration");
		this.parent = p;
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parent.setEnabled(true);
			}
		});

		this.setLayout(new GridLayout(0, 1, 0, 2));

		keys = getKeysFromTXT();
		buttons = new JButton[5];
		label = new JLabel("Click on buttons to change key configuration.");
		label.setFont(new Font("Dialog.bold", 0, 13));
		add(label);
		for (int k = 0; k < 5; k++) {
			buttons[k] = new JButton();
			buttons[k].setFont(new Font("Dialog.bold", 0, 15));
			add(buttons[k]);
			buttons[k].addActionListener(actLis);
		}
		updateButtonTexts();

	}

	// Reads keys.txt into keys[]
	protected static int[] getKeysFromTXT() {
		int[] keys = new int[5];
		try {
			Scanner keyScan = new Scanner(new File("keys.txt"));
			for (int k = 0; k < 5; k++) {
				keys[k] = keyScan.nextInt();
			}
			keyScan.close();
		} catch (FileNotFoundException e1) {
			int[] defKeys = { 37, 39, 40, 38, 32 };
			KeyConfig.saveKeystoTXT(defKeys);
			return defKeys;
		}

		return keys;
	}

	protected static void saveKeystoTXT(int[] keys) {
		try {
			PrintWriter writer = new PrintWriter(("keys.txt"), "utf-8");
			for (int n = 0; n < 5; n++) {
				writer.println(keys[n]);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	// To update button texts when keys[] is changed.
	protected void updateButtonTexts() {

		buttons[0].setText("Left (Currently : " + KeyEvent.getKeyText(keys[0])
				+ ")");
		buttons[1].setText("Right (Currently : " + KeyEvent.getKeyText(keys[1])
				+ ")");
		buttons[2].setText("Speed Up (Currently : "
				+ KeyEvent.getKeyText(keys[2]) + ")");
		buttons[3].setText("Rotate (Currently : "
				+ KeyEvent.getKeyText(keys[3]) + ")");
		buttons[4].setText("Pause (Currently : " + KeyEvent.getKeyText(keys[4])
				+ ")");
	}

}