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
class BlockOptions extends JFrame {

	public static final int ONLY_TETRIMINOS = 1;
	public static final int ONLY_TRIMINOS = 2;
	public static final int BOTH = 3;

	JLabel selectBlock;
	Options parent;

	public BlockOptions(Options p) {
		super("Block Options");
		this.parent = p;
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parent.setEnabled(true);
			}
		});

		setLayout(new GridLayout(4, 1, 0, 4));

		int temp = getBlockOptionFromTXT();
		String type = "";
		switch (temp) {
		case 1:
			type = "tetriminos";
			break;
		case 2:
			type = "triminos";
			break;
		case 3:
			type = "tetriminos and triminos";
			break;
		}

		selectBlock = new JLabel("Current block type is " + type
				+ ". Please select a type.");
		selectBlock.setFont(new Font("Dialog.bold", 0, 13));
		add(selectBlock);

		JButton tetri = new JButton("Only tetriminos");
		tetri.setFont(new Font("Dialog.bold", 0, 15));

		JButton tri = new JButton("Only triminos");
		tri.setFont(new Font("Dialog.bold", 0, 15));

		JButton both = new JButton("Both");
		both.setFont(new Font("Dialog.bold", 0, 15));

		add(tetri);
		add(tri);
		add(both);

		tetri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBlock.setText("Tetriminos is selected.");
				saveBlockOptionToTXT(ONLY_TETRIMINOS);
			}
		});

		tri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBlock.setText("Triminos is selected.");
				saveBlockOptionToTXT(ONLY_TRIMINOS);
			}
		});

		both.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectBlock.setText("'Tetriminos and triminos' is selected.");
				saveBlockOptionToTXT(BOTH);
			}
		});

	}

	protected static int getBlockOptionFromTXT() {
		int a = 0;
		try {
			Scanner blockScan = new Scanner(new File("blockOption.txt"));
			a = blockScan.nextInt();
			blockScan.close();
			if (a < 1 || a > 3) {
				throw new FileNotFoundException();
			}

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			saveBlockOptionToTXT(ONLY_TETRIMINOS);
			return ONLY_TETRIMINOS;
		}
		return a;
	}

	protected static void saveBlockOptionToTXT(int blockOption) {
		try {
			PrintWriter blockOptions = new PrintWriter(("blockOption.txt"),
					"utf-8");
			blockOptions.print(blockOption);
			blockOptions.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}