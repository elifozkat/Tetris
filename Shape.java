import java.awt.Color;
import java.util.ArrayList;

abstract class Shape {
	public ArrayList<DreamBox> boxList;
	protected boolean isTetri;
	protected Color color;
	protected int degrees;
	protected int x, y;

	public Shape(Color color, int degrees, int x, int y) {
		setColor(color);
		setDegrees(degrees);
		setX(x);
		setY(y);
		boxList = new ArrayList<DreamBox>();
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	public void setDegrees(int degrees) {
		this.degrees = degrees;
	}

	public int getDegrees() {
		return this.degrees;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void rotateShape() {
		for (DreamBox box : boxList) {
			int x = box.getX();
			int y = box.getY();
			int a = y;
			y=x;  //
			x=-a; // Ters zannetmeyin, array aþaðý doðru artýyor diye böyle ayarladým		
			box.setX(x);
			box.setY(y);
		}
	}

	public int maxPosX() {
		int i = 0;
		for (DreamBox box : boxList) {
			if (box.getX() > i)
				i = box.getX();
		}
		return i;
	}

	public int minNegX() {
		int i = 0;
		for (DreamBox box : boxList) {
			if (box.getX() < i)
				i = box.getX();
		}
		return i;
	}

	public int maxPosY() {
		int i = 0;
		for (DreamBox box : boxList) {
			if (box.getY() > i)
				i = box.getY();
		}
		return i;
	}

	public int minNegY() {
		int i = 0;
		for (DreamBox box : boxList) {
			if (box.getY() < i)
				i = box.getY();
		}
		return i;
	}

	abstract public Shape getRotatedVersion();

}
