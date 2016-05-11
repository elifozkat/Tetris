import java.awt.Color;

public class IShape extends TetriShape {

	public IShape(Color color, int degrees, int x, int y) {
		super(color, degrees, x, y);
		boxList.add(new DreamBox(color, 0, -2));
		boxList.add(new DreamBox(color, 0, -1));
		boxList.add(new DreamBox(color, 0, 0));
		boxList.add(new DreamBox(color, 0, 1));
	}

	public IShape getRotatedVersion() {
		IShape rotatedShape = new IShape(this.color, this.degrees, this.x,
				this.y);
		rotatedShape.boxList.clear();
		for (DreamBox box : this.boxList) {
			rotatedShape.boxList.add(new DreamBox(box.getColor(), box.getX(),
					box.getY()));
		}
		rotatedShape.rotateShape();
		return rotatedShape;
	}

}
