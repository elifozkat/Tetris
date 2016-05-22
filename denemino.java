import java.awt.Color;

public class denemino extends Shape {

	public denemino(Color color, int degrees, int x, int y) {
		super(color, degrees, x, y);
		DreamBox a = new DreamBox(color, 0, 0);
		DreamBox b = new DreamBox(color, 1, 0);
		boxList.add(a);
		boxList.add(b);
		isTetri = false;
	}

	public denemino getRotatedVersion() {
		denemino rotatedShape = new denemino(this.color, this.degrees, this.x,
				this.y);
		rotatedShape.rotateShape();
		return rotatedShape;
	}
}
