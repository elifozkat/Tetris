import java.awt.Color;

public class TetriShape extends Shape {
	public TetriShape(Color color, int degrees, int x, int y) {
		super(color, degrees, x, y);
		this.isTetri = true;
	}

	@Override
	public Shape getRotatedVersion() {
		// TODO Auto-generated method stub
		return null;
	}

}
