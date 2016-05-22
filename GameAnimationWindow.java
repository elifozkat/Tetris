import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class GameAnimationWindow extends JPanel {

	int width;
	int height;
	DreamBox[][] blocks;
	Shape currentShape;
	Shape nextShape;
	public Color defaultColor; 
	int blockOption;
	
	BufferedImage image;
	JLabel picLabel;
	int blockSize = 20;

	GameWindow parent;	

	public GameAnimationWindow(GameWindow parent) {
		super();
		this.parent = parent; // DÝKKAT
		defaultColor=parent.defaultColor;
		setBackground(defaultColor);
		
		int[] a = BoardSize.getSizeFromTXT();
		width = a[0];
		height = a[1];
		blockOption = BlockOptions.getBlockOptionFromTXT();

		blocks = new DreamBox[width][height];
		
		image = new BufferedImage(blockSize * width, blockSize * height,
				BufferedImage.TYPE_INT_RGB); // DÝKKAT

		for (int l = 0; l < height; l++) {
			for (int k = 0; k < width; k++) {
				blocks[k][l] = new DreamBox(defaultColor);
			}
		}
		picLabel = new JLabel(new ImageIcon(image));
		add(picLabel);

		paintImage();

		setPreferredSize(new Dimension(blockSize * width + 10, blockSize
				* height + 10));
		
		updateNextShape();
		putNewShapeOnBoard();
	}

	public void paintImage() {
		// remove(picLabel); // DÝKKAT
		for (int l = 0; l < height; l++) {
			for (int k = 0; k < width; k++) {
				int color = blocks[k][l].getColor().getRGB();
				for (int i = 0; i < blockSize; i++) {
					for (int j = 0; j < blockSize; j++) {
						if (i == 0 || (k == width - 1 && i == blockSize - 1)
								|| j == 0
								|| (l == height - 1 && j == blockSize - 1)) {
							image.setRGB(blockSize * k + i, blockSize * l + j,
									Color.black.getRGB());
							continue;
						}
						image.setRGB(blockSize * k + i, blockSize * l + j,
								color);
					}
				}
			}
		}
		picLabel = new JLabel(new ImageIcon(image));
		// add(picLabel);
		revalidate();
		repaint();
	}

	// Stops the currentShape when it is blocked
	public void stopCurrentShape() {
		for (DreamBox box : currentShape.boxList) {
			box.setCanBlock(true);
		}
	}

	// After old currentShape is stopped, puts a new currentShape on top of the
	// board
	public void putNewShapeOnBoard() {
		currentShape = nextShape;
		updateNextShape();

		currentShape.setX((width - (1 + currentShape.maxPosX() - currentShape
				.minNegX())) / 2);
		currentShape.setY(-currentShape.minNegY());
		// -----------------------------
		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = box;
		}
		paintImage();
	}

	public void updateNextShape() {
		nextShape = getRandomShape();
		Shape shapeToSend = nextShape.getRotatedVersion();
		shapeToSend.rotateShape();
		shapeToSend.rotateShape();
		shapeToSend.rotateShape();
		parent.panel.paintNextShape(nextShape);
	}

	public boolean canPutOnBoard() {
		int x = (width - (1 + nextShape.maxPosX() - nextShape.minNegX())) / 2;
		int y = -nextShape.minNegY();
		for (DreamBox box : nextShape.boxList) {
			int k = x + box.getX();
			int l = y + box.getY();
			if (blocks[k][l].getCanBlock()) {
				return false;
			}
		}
		return true;
	}

	// Moves currentShape downwards 1 unit
	public boolean moveDown() {
		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			if ((y + 1) >= height || blocks[x][y + 1].getCanBlock()) {
				return true;
			}
		}

		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = new DreamBox(defaultColor);
		}

		currentShape.setY(currentShape.getY() + 1);

		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = box;
		}
		paintImage();
		return false;
	}

	// Moves currentShape to left 1 unit
	public boolean moveLeft() {
		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			if ((x - 1) < 0 || blocks[x - 1][y].getCanBlock()) {
				return true;
			}
		}

		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = new DreamBox(defaultColor);
		}

		currentShape.setX(currentShape.getX() - 1);

		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = box;
		}
		paintImage();
		return false;
	}

	// Moves currentShape to right 1 unit
	public boolean moveRight() {
		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			if ((x + 1) >= width || blocks[x + 1][y].getCanBlock()) {
				return true;
			}
		}

		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = new DreamBox(defaultColor);
		}

		currentShape.setX(currentShape.getX() + 1);

		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = box;
		}

		paintImage();
		return false;
	}

	// Rotates the currentShape by 90 degrees clockwise, if there are no
	// blocking objects
	public void rotate() {
		Shape rotatedVersion = currentShape.getRotatedVersion();
		int outX = 0;
		int outY = 0;
		boolean problem = true;
		while (problem) {
			problem = false;
			for (DreamBox box : rotatedVersion.boxList) {
				int x = rotatedVersion.getX() + box.getX() - outX;
				int y = rotatedVersion.getY() + box.getY() - outY;
				if (x < 0 || x >= width || y >= height || y < 0) {
					if (x < 0) {
						if (outX > 0) {
							return;
						}
						outX += x;
						problem = true;
						break;
					}
					if (x >= width) {
						if (outX < 0) {
							return;
						}
						outX += x - width + 1;
						problem = true;
						break;
					}
					if (y >= height) {
						if (outY < 0) {
							return;
						}
						outY += y - height + 1;
						problem = true;
						break;
					}
					if (y < 0) {
						if (outY > 0) {
							return;
						}
						outY += y;
						problem = true;
						break;
					}
				} else {
					if (blocks[x][y].getCanBlock()) {
						if (box.getX() < 0) {
							if (outX > 0) {
								return;
							}
							outX += rotatedVersion.minNegX() - box.getX() - 1;
							problem = true;
							break;
						}
						if (box.getX() > 0) {
							if (outX < 0) {
								return;
							}
							outX += rotatedVersion.maxPosX() - box.getX() + 1;
							problem = true;
							break;
						}
						if (box.getY() < 0) {
							if (outY > 0) {
								return;
							}
							outY += rotatedVersion.minNegY() - box.getY() - 1;
							problem = true;
							break;
						}
						if (box.getY() > 0) {
							if (outY < 0) {
								return;
							}
							outY += rotatedVersion.maxPosY() - box.getY() + 1;
							problem = true;
							break;
						}
					}
				}
			}
		}
		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = new DreamBox(defaultColor);
		}
		currentShape.rotateShape();
		currentShape.setX(currentShape.getX() - outX);
		currentShape.setY(currentShape.getY() - outY);
		for (DreamBox box : currentShape.boxList) {
			int x = currentShape.getX() + box.getX();
			int y = currentShape.getY() + box.getY();
			blocks[x][y] = box;
		}
		paintImage();
	}

	public int checkAndRemoveLines() {
		int removedLines = 0;
		for (int k = height - 1; k >= 0; k--) {
			int count = 0;
			for (int l = 0; l < width; l++) {
				if (blocks[l][k].getCanBlock())
					count++;
			}
			if (count == width) {
				removedLines++;
				for (int h = k - 1; h >= 0; h--) {
					for (int w = 0; w < width; w++) {
						blocks[w][h + 1] = blocks[w][h];
						if (h == 0) {
							blocks[w][h] = new DreamBox(defaultColor);
						}
					}
				}
				k++;
			}
		}
		return removedLines;
	}

	public Shape getRandomShape() {
		Color c = getRandomColor();
		int a = 0;
		switch (blockOption) {
		case BlockOptions.ONLY_TRIMINOS:
			a = new Random().nextInt(3);
			break;
		case BlockOptions.ONLY_TETRIMINOS:
			a = 3 + new Random().nextInt(7);
			break;
		case BlockOptions.BOTH:
			a = new Random().nextInt(10);
		}
		Shape s;
		switch (a) {
		case 0:
			s = new iLittleShape(c, 0, 0, 0);
			break;
		case 1:
			s = new jLittleShape(c, 0, 0, 0);
			break;
		case 2:
			s = new rLittleShape(c, 0, 0, 0);
			break;
		case 3:
			s = new ZShape(c, 0, 0, 0);
			break;
		case 4:
			s = new SShape(c, 0, 0, 0);
			break;
		case 5:
			s = new OShape(c, 0, 0, 0);
			break;
		case 6:
			s = new TShape(c, 0, 0, 0);
			break;
		case 7:
			s = new JShape(c, 0, 0, 0);
			break;
		case 8:
			s = new LShape(c, 0, 0, 0);
			break;
		default:
			s = new IShape(c, 0, 0, 0);
			break;
		}

		for (int k = 0; k < getRandomDegrees() / 90; k++) {
			s.rotateShape();
		}
		return s;
	}

	public Color getRandomColor() {
		int a = new Random().nextInt(8);
		Color c;
		switch (a) {
		case 0:
			c = new Color(60, 186, 64);
			break;
		case 1:
			c = new Color(250, 2, 77);
			break;
		case 2:
			c = new Color(2, 122, 250);
			break;
		case 3:
			c = new Color(250, 155, 2);
			break;
		case 4:
			c = new Color(179, 28, 5);
			break;
		case 5:
			c = new Color(151, 31, 156);
			break;
		case 6:
			c = new Color(255, 251, 0);
			break;
		default:
			c = new Color(4, 6, 184);
			break;
		}
		return c;

	}

	public int getRandomDegrees() {
		return 90 * (new Random().nextInt(4));
	}
}
