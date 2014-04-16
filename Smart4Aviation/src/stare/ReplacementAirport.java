package pl.areusmart.flightplan;

public class ReplacementAirport extends Airport {

	public ReplacementAirport() {
		super();

	}

	public double getDistanceFromPath(Airport a, Airport b) {

		double[] coords = new double[2];
		coords = this.getIntersectionPoint(a, b);
		return 111 * Math.sqrt(Math.pow(coords[0] - this.getCoordX(), 2)
				+ Math.pow(coords[1] - this.getCoordY(), 2));
	}

	public double[] getIntersectionPoint(Airport a, Airport b) {

		FlightPath path = new FlightPath();
		path.setFlightPath(a, b);
		double[] coords = new double[2];
		double orthogonalA = (1 / path.getParamA()) * (-1);
		double orthogonalB = this.getCoordY() - orthogonalA * this.getCoordX();
		double coordX = (orthogonalB - path.getParamB())
				/ (path.getParamA() - orthogonalA);
		double coordY = orthogonalA * coordX + orthogonalB;
		coords[0] = coordX;
		coords[1] = coordY;
		return coords;
	}

	public static void main(String[] args) {
		Airport a = new Airport();
		Airport b = new Airport();
		a.getDataFromFile("KRK");
		b.getDataFromFile("MMX");
		FlightPath p = new FlightPath();
		p.setFlightPath(a, b);

		ReplacementAirport c = new ReplacementAirport();
		c.getDataFromFile("WRO");

		// c.distanceFromPath = c.getDistanceFromPath(a, b);
		double distance = c.getDistanceFromPath(a, b);
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println(distance);
		System.out.println("A :" + p.getParamA());
		System.out.println("B :" + p.getParamB());
		System.out.println("wspXA :" + a.getCoordX());
		System.out.println("wspYA :" + a.getCoordY());
		System.out.println("wspXB :" + b.getCoordX());
		System.out.println("wspYB :" + b.getCoordY());
		System.out.println("wspXC :" + c.getCoordX());
		System.out.println("wspYC :" + c.getCoordY());
	}
}
