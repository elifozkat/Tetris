import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class Panel extends JPanel {

	JLabel labelLevel;
	JLabel labelLines;
	JLabel labelScore;
	BufferedImage image;
	JLabel picLabel;
	JPanel contentpanel;

	int blockSize = 20;
	public Color defaultColor;

	GameWindow parent;

	public Panel(GameWindow parent) {
		super();
		this.parent = parent; // DÝKKAT
		defaultColor = parent.defaultColor;

		labelLevel = new JLabel();
		labelLevel.setFont(new Font("Dialog.bold", 0, 16));
		labelLines = new JLabel();
		labelLines.setFont(new Font("Dialog.bold", 0, 16));
		labelScore = new JLabel();
		labelScore.setFont(new Font("Dialog.bold", 0, 16));
		image = new BufferedImage(blockSize * 6, blockSize * 6,
				BufferedImage.TYPE_INT_RGB); // DÝKKAT
		picLabel = new JLabel(new ImageIcon(image));

		contentpanel = new JPanel();
		contentpanel.setLayout(new BoxLayout(contentpanel, BoxLayout.Y_AXIS));

		contentpanel.add(picLabel);
		contentpanel.add(labelLevel);
		contentpanel.add(labelLines);
		contentpanel.add(labelScore);
		add(contentpanel);

		setVisible(true);
	}

	public void paintNextShape(Shape s) {
		for (int l = 0; l < 6 * blockSize; l++) {
			for (int k = 0; k < 6 * blockSize; k++) {
				image.setRGB(k, l, defaultColor.getRGB());
			}
		}
		for (DreamBox box : s.boxList) {
			int color = box.getColor().getRGB();
			for (int i = 0; i < blockSize; i++) {
				for (int j = 0; j < blockSize; j++) {
					image.setRGB(
							i
									+ (box.getX() - s.minNegX() + (5 - s
											.maxPosX()) / 2) * blockSize,
							j
									+ (box.getY() - s.minNegY() + (5 - s
											.maxPosY()) / 2) * blockSize, color);
				}
			}
		}
		for (int l = 0; l < 6 * blockSize; l++) {
			for (int k = 0; k < 6 * blockSize; k++) {
				if (k % blockSize == 0 || k == 6 * blockSize - 1
						|| l % blockSize == 0 || l == 6 * blockSize - 1) {
					image.setRGB(k, l, Color.black.getRGB());
				}
			}
		}
		picLabel.setIcon(new ImageIcon(image));

		revalidate();
		repaint();
	}

	public void setLevel(int level) {
		labelLevel.setText("Level: " + String.valueOf(level));
	}

	public void setLines(int lines) {
		labelLines.setText("Lines: " + String.valueOf(lines));
	}

	public void setScore(int score) {
		labelScore.setText("Score: " + String.valueOf(score));
	}
}
