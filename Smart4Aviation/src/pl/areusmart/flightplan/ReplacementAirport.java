package pl.areusmart.flightplan;

public class ReplacementAirport extends Airport {

	public ReplacementAirport() {
		super();
	}
	
	
	//fkcja zwraca odleglosc miedzy pktem polozenia lotniska zapasowego, a pktem przeciecia prostej prostopadej do prostej bedacej torem lotu,
	//przechodzacej przez wspolrzedne lotniska zapasowego
	public double getDistanceFromFlightPath(Airport a, Airport b) {
		FlightPath fp = new FlightPath();
		fp.setFlightPath(a, b);
		Coords straightParams = new Coords();
		straightParams.setX(fp.getParamA());
		straightParams.setY(fp.getParamB());
		Coords point = fp.getIntersectionPoint(straightParams, getCoords());
		return Conversion.DEGREES_TO_KILOMETERS
				* Math.sqrt(Math.pow(point.getX() - getCoords().getX(), 2)
						+ Math.pow(point.getY() - getCoords().getY(), 2));
	}
	
}
