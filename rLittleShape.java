import java.awt.Color;

public class rLittleShape extends TriShape {

	public rLittleShape(Color color, int degrees, int x, int y) {
		super(color, degrees, x, y);
		boxList.add(new DreamBox(color, 0, -1));
		boxList.add(new DreamBox(color, 0, 0));
		boxList.add(new DreamBox(color, 1, 0));
	}

	public rLittleShape getRotatedVersion() {
		rLittleShape rotatedShape = new rLittleShape(this.color, this.degrees,
				this.x, this.y);
		rotatedShape.boxList.clear();
		for (DreamBox box : this.boxList) {
			rotatedShape.boxList.add(new DreamBox(box.getColor(), box.getX(),
					box.getY()));
		}
		rotatedShape.rotateShape();
		return rotatedShape;
	}

}
