package pl.areusmart.flightplan;

public class FlightPath {

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double paramA;
	private double paramB;

	public void setFlightPath(Airport startAirport, Airport stopAirport) {
		x1 = startAirport.getCoordX();
		y1 = startAirport.getCoordY();
		x2 = stopAirport.getCoordX();
		y2 = stopAirport.getCoordY();

		paramA = (y2 - y1) / (x2 - x1);
		paramB = y1 - paramA * x1;

	}

	public double getParamA() {
		return paramA;
	}

	public double getParamB() {
		return paramB;
	}

	public double getDistance(Airport startAirport, Airport stopAirport) {
		return 111 * Math
				.sqrt((Math.pow(
						(stopAirport.getCoordX() - startAirport.getCoordX()), 2) + (Math
						.pow((stopAirport.getCoordY() - startAirport
								.getCoordY()), 2))));

	}

	public static void main(String[] args) {
		Airport a = new Airport();
		Airport b = new Airport();

		a.getDataFromFile("KRK");
		b.getDataFromFile("WAW");

		FlightPath flightPath = new FlightPath();
		flightPath.setFlightPath(a, b);
		System.out.println(flightPath.getParamA());
		System.out.println(flightPath.getParamB());
	}
}
