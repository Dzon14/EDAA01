package fractal;


import koch.Koch;
import mountain.Mountain;
import mountain.Point;

public class FractalApplication {
	public static void main(String[] args) {
		Fractal[] fractals = new Fractal[2];
		fractals[0] = new Mountain(new Point(50, 550), new Point(370,100), new Point(560,450), 30);
		fractals[1] = new Koch(300);
		
	    new FractalView(fractals, "Fraktaler", 600, 600);
	}

}
