import java.awt.Color;

public class TriShape extends Shape {

	public TriShape(Color color, int degrees, int x, int y) {
		super(color, degrees, x, y);
		this.isTetri = false;
	}

	@Override
	public Shape getRotatedVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
