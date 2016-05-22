import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
class StartWindow extends JFrame {

	protected StartButtons startbuttons;

	public StartWindow() {
		super("Dream Tetris by Dream Team");
		setResizable(false);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		JToolBar toolBar = new JToolBar();
		addButtons(toolBar);

		startbuttons = new StartButtons(this);
		JScrollPane scrollPane = new JScrollPane(startbuttons);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout());
		contentPane.setPreferredSize(new Dimension(640, 675));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		setContentPane(contentPane);

	}

	protected void addButtons(final JToolBar toolBar) {
		JButton button = null;

		button = new JButton("About Dream Tetris");
		button.setToolTipText("Information about Dream Tetris");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JOptionPane
						.showMessageDialog(
								toolBar,
								"Dream Tetris V 1.0.0.0 BETA is a game developed by Dream Team for KU - COMP 302",
								"Dream Tetris - Info",
								JOptionPane.WARNING_MESSAGE);

			}
		});
		toolBar.add(button);

		button = new JButton("Exit");
		button.setToolTipText("Quit Game");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		toolBar.add(button);

	}
}