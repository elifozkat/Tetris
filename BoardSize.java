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
import javax.swing.JTextField;

@SuppressWarnings("serial")
class BoardSize extends JFrame {

	int width, height;
	JLabel labelWidth, labelHeight;
	JTextField tfWidth, tfHeight;
	JButton saveButton;
	JLabel labelWarning;
	ActionListener actLis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				Integer.parseInt(tfWidth.getText());
				Integer.parseInt(tfHeight.getText());
			} catch (Exception e) {
				labelWarning
						.setText("Width and height have to be numbers greater than 3.");
				return;
			}
			width = Integer.parseInt(tfWidth.getText());
			height = Integer.parseInt(tfHeight.getText());
			if (width < 4 || height < 4) {
				labelWarning
						.setText("Width and height have to be greater than 3.");
			} else {
				saveSizeToTXT(width, height);
				labelWarning.setText(("Board size is saved."));
			}
		}
	};

	Options parent;

	public BoardSize(Options p) {
		super("Board Size");
		this.parent = p;
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parent.setEnabled(true);
			}
		});

		int[] a = getSizeFromTXT();
		width = a[0];
		height = a[1];

		this.setLayout(new GridLayout(3, 2));

		labelWidth = new JLabel("Board Width: ");
		labelWidth.setFont(new Font("Dialog.bold", 0, 13));

		labelHeight = new JLabel("Board Height: ");
		labelHeight.setFont(new Font("Dialog.bold", 0, 13));

		tfWidth = new JTextField();
		tfWidth.setText(String.valueOf(width));
		tfWidth.setFont(new Font("Dialog.bold", 0, 13));

		tfHeight = new JTextField();
		tfHeight.setText(String.valueOf(height));
		tfHeight.setFont(new Font("Dialog.bold", 0, 13));

		saveButton = new JButton("Save");
		saveButton.setFont(new Font("Dialog.bold", 0, 13));
		saveButton.addActionListener(actLis);

		labelWarning = new JLabel("Enter width and height.");
		labelWarning.setFont(new Font("Dialog.bold", 0, 13));

		add(labelWidth);
		add(tfWidth);
		add(labelHeight);
		add(tfHeight);
		add(labelWarning);
		add(saveButton);
		setVisible(true);
	}

	protected static int[] getSizeFromTXT() {
		int[] a = new int[2];
		try {
			Scanner sizeScan = new Scanner(new File("boardboyut.txt"));
			a[0] = sizeScan.nextInt(); // width
			a[1] = sizeScan.nextInt();// height
			sizeScan.close();
			if (a[0] < 4 || a[1] < 4) {
				throw new FileNotFoundException();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			saveSizeToTXT(10, 15);
			return new int[] { 10, 15 };
		}
		return a;
	}

	protected static void saveSizeToTXT(int width, int height) {
		try {
			PrintWriter writer = new PrintWriter(("boardboyut.txt"), "utf-8");
			writer.println(width);
			writer.println(height);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}