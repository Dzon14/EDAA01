package mountain;

import java.util.HashMap;

import fractal.Fractal;
import fractal.TurtleGraphics;

public class Mountain extends Fractal {
	private Point p1;
	private Point p2;
	private Point p3;
	private double dev;

	private HashMap<Side, Point> side;

	public Mountain(Point p1, Point p2, Point p3, double dev) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.dev = dev;
		this.side = new HashMap<>();
	}

	@Override
	public String getTitle() {
		return "Mountain fractal";
	}

	@Override
	public void draw(TurtleGraphics g) {
		fractalTriangle(g, order, p1, p2, p3, dev);
	}

	// Private method to give the new point (middle)
	private Point midPoint(Point a, Point b, double dev) {
		Side s = new Side(a, b);

		// Checks if the side (with same points) exist
		if (side.containsKey(s)) {
			Point p = side.get(s);
			side.remove(s);
			return p;
		} else {
			int x = a.getX() + ((b.getX() - a.getX()) / 2);
			int y = (int) (a.getY() + ((b.getY() - a.getY()) / 2) + RandomUtilities.randFunc(dev));

			Point p = new Point(x, y); // Generating a new point and returning it

			side.put(s, p); // saving to the map

			return p;

		}
	}

	private void fractalTriangle(TurtleGraphics turtle, int order, Point p1, Point p2, Point p3, double dev) {
		if (order == 0) {
			turtle.moveTo(p1.getX(), p1.getY());
			turtle.forwardTo(p2.getX(), p2.getY());
			turtle.forwardTo(p3.getX(), p3.getY());
			turtle.forwardTo(p1.getX(), p1.getY());

		} else {
			Point ab = midPoint(p1, p2, dev);
			Point bc = midPoint(p2, p3, dev);
			Point ac = midPoint(p1, p3, dev);

			fractalTriangle(turtle, order - 1, ab, bc, ac, dev / 2);
			fractalTriangle(turtle, order - 1, p1, ab, ac, dev / 2);
			fractalTriangle(turtle, order - 1, ab, p2, bc, dev / 2);
			fractalTriangle(turtle, order - 1, ac, bc, p3, dev / 2);
		}

	}

}
