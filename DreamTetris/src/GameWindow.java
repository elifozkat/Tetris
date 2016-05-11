import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
class GameWindow extends JFrame {

	public GameAnimationWindow animationWindow;
	public Panel panel;

	private JLabel waitLabel;
	private JButton waitExitButton;
	private JButton returnButton;
	private JPanel waitMenu;
	ActionListener exitLis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	};
	ActionListener returnLis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			dispatchEvent(new WindowEvent((JFrame)returnButton.getTopLevelAncestor(),WindowEvent.WINDOW_CLOSING));
		}
	};

	int level;
	int lines;
	int score;
	double speed;
	public Timer timer;
	int[] keys;
	boolean speedDoubled = false;
	boolean isPaused = false;
	public Color defaultColor = new Color(166, 162, 148);

	StartWindow parent;

	ActionListener actLis = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent event) {

			if (timer.equals(event.getSource())) {
				if (animationWindow.moveDown()) {
					animationWindow.stopCurrentShape();
					int newlines = animationWindow.checkAndRemoveLines();
					lines += newlines;
					panel.setLines(lines);
					switch (newlines) {
					case 0:
						break;
					case 1:
						score += Math.round(1.0 / (0.2 * (6 - level)));
						break;
					case 2:
						score += Math.round(3.0 / (0.2 * (6 - level)));
						break;
					case 3:
						score += Math.round(6.0 / (0.2 * (6 - level)));
						break;
					default:
						score += Math.round(10.0 / (0.2 * (6 - level)));
						break;
					}
					panel.setScore(score);
					if (animationWindow.canPutOnBoard()) {
						animationWindow.putNewShapeOnBoard();
					} else {
						gameOver();
					}
				}
			}

		}
	};

	KeyListener keyLis = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			
			if (arg0.getKeyCode() == keys[2]) {
				speed *= 2;
				timer.setDelay((int) (1000 * speed));
				timer.start();
				speedDoubled = false;
			}
		}

		@Override
		public void keyPressed(KeyEvent arg0) {
			
			if (arg0.getKeyCode() == keys[0]) {
				animationWindow.moveLeft();
			}
			if (arg0.getKeyCode() == keys[1]) {
				animationWindow.moveRight();
			}
			if (arg0.getKeyCode() == keys[2] && speedDoubled == false) {
				speed /= 2;
				timer.setDelay((int) (1000 * speed));
				timer.start();
				speedDoubled = true;
			}
			if (arg0.getKeyCode() == keys[3]) {
				animationWindow.rotate();
			}
			if (arg0.getKeyCode() == keys[4]) {
				if (isPaused) {
					unPause();
				} else {
					pause();
				}

			}
		}
	};

	public GameWindow(StartWindow p) {

		super("Dream Tetris");
		this.parent = p;

		setResizable(false);
		
		// BURADA SIKINTI VAR
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				parent.setVisible(true);
				timer.stop();
				//((GameWindow) e.getSource()).dispose();
				GameWindow.this.dispose();
			}
		});

		keys = KeyConfig.getKeysFromTXT();

		panel = new Panel(this);
		animationWindow = new GameAnimationWindow(this);

		waitLabel = new JLabel("PAUSED");

		this.setLayout(new FlowLayout());
		add(animationWindow);
		add(panel);
		
		level = Level.getLevelFromTXT();
		speed = 0.2 * (6 - level);
		lines = 0;
		score = 0;
		panel.setLevel(level);
		panel.setLines(lines);
		panel.setScore(score);

		timer = new Timer((int) (1000 * speed), actLis);
		timer.start();

		addKeyListener(keyLis);

		waitExitButton = new JButton("Exit");
		waitExitButton.addActionListener(exitLis);
		
		returnButton = new JButton("Main Menu");
		returnButton.addActionListener(returnLis);
		
		waitMenu = new JPanel();
		waitMenu.setLayout(new GridLayout(3, 1, 0, 5));
		waitMenu.add(waitLabel);
		waitMenu.add(returnButton);
		waitMenu.add(waitExitButton);
		add(waitMenu);
		waitMenu.setVisible(false);
	}

	public void gameOver() {
		TopScores ts = new TopScores(score, this.parent);
		ts.pack();
		ts.setLocationRelativeTo(null);
		ts.setVisible(true);
		timer.stop();
		this.dispose();
	}

	public void pause() {
		timer.stop();
		animationWindow.setVisible(false);
		panel.setVisible(false);
		waitMenu.setVisible(true);
		isPaused = true;
	}

	public void unPause() {
		isPaused = false;
		animationWindow.setVisible(true);
		panel.setVisible(true);
		waitMenu.setVisible(false);
		timer = new Timer((int) (1000 * speed), actLis);
		timer.start();
	}

}