import java.awt.Color;

public class DreamBox {

	private Color color;
	private int x;
	private int y;
	private boolean canBlock;

	public DreamBox(Color color, int x, int y) {
		super();
		setColor(color);
		setCanBlock(false);
		setX(x);
		setY(y);
	}

	public DreamBox(Color color) {
		super();
		setColor(color);
		setCanBlock(false);
	}

	public void setCanBlock(boolean canBlock) {
		this.canBlock = canBlock;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean getCanBlock() {
		return this.canBlock;
	}

	public Color getColor() {
		return this.color;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
