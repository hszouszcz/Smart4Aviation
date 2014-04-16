package pl.areusmart.flightplan;

import java.util.Comparator;

public class Coords implements Comparator<Coords> {

	private double x;
	private double y;

	public Coords() {
		x = 0;
		y = 0;
	}

	@Override
	public int compare(Coords o1, Coords o2) {
		if (o1.x > o2.x && o1.y > o2.y)
			return 1;
		else if (o1.x == o2.x && o1.y == o2.y)
			return 0;
		else
			return -1;

	}

	public static int compareCoords(double c1, double c2) {
		if (c1 > c2)
			return 1;
		else if (c1 == c2)
			return 0;
		else
			return -1;
	}

	public static Coords substractCoords(Coords a, Coords b) {
		Coords newCoords = new Coords();
		newCoords.setX(a.x - b.x);
		newCoords.setY(a.y - b.y);
		return newCoords;
	}

	public void setX(double coordX) {
		x = coordX;
	}

	public void setY(double coordY) {
		y = coordY;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
