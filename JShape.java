import java.awt.Color;

public class JShape extends TetriShape {

	public JShape(Color color, int degrees, int x, int y) {
		super(color, degrees, x, y);
		boxList.add(new DreamBox(color, 0, -1));
		boxList.add(new DreamBox(color, 0, 0));
		boxList.add(new DreamBox(color, 0, 1));
		boxList.add(new DreamBox(color, -1, 1));
	}

	public JShape getRotatedVersion() {
		JShape rotatedShape = new JShape(this.color, this.degrees, this.x,
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
