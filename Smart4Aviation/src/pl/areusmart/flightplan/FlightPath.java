package pl.areusmart.flightplan;

public class FlightPath {

	private double x1;
	private double y1;
	private double x2;
	private double y2;
	private double paramA;
	private double paramB;



	//fkcja oblicza parametry A i B fkcji liniowej, przechodz¹cej przez 2 punkty
	public void setFlightPath(Airport startAirport, Airport stopAirport) {
		x1 = startAirport.getCoords().getX();
		y1 = startAirport.getCoords().getY();
		x2 = stopAirport.getCoords().getX();
		y2 = stopAirport.getCoords().getY();

		paramA = (y2 - y1) / (x2 - x1);
		paramB = y1 - paramA * x1;

	}

	public double getParamA() {
		return paramA;
	}

	public double getParamB() {
		return paramB;
	}

	
	//fkcja oblicza odleg³oœæ miêdzy lotniskiem pocz¹tkowym i koñcowym
	public double getDistance(Airport startAirport, Airport stopAirport) {

		Coords newCoords = Coords.substractCoords(stopAirport.getCoords(),
				startAirport.getCoords());
		return Conversion.DEGREES_TO_KILOMETERS
				* Math.sqrt((Math.pow((newCoords.getX()), 2) + (Math.pow(
						(newCoords.getY()), 2))));

	}
	
	//Fkcja wyznacza prosta prostopadla do prostej pathParams
	private Coords straightOrthogonal(Coords pathParams, Coords point) {
		
		Coords orthoParams = new Coords();
		orthoParams.setX((1 / pathParams.getX()) * (-1));
		orthoParams.setY(point.getY() - orthoParams.getX()
				* point.getX());
		return orthoParams;

	}
	

	// Fkcja zwraca punkt przeciecia prostej prostopadlej do prostej straightParams, i przechodzacej przez punkt point
	public Coords getIntersectionPoint(Coords straightParams, Coords point) {


		double orthogonalA = (1 / straightParams.getX()) * (-1);
		double orthogonalB = point.getY() - orthogonalA
				* point.getX();
		double coordX = (orthogonalB - straightParams.getY())
				/ (straightParams.getX()- orthogonalA);
		double coordY = orthogonalA * coordX + orthogonalB;
		Coords newPoint = new Coords();
		newPoint.setX(coordX);
		newPoint.setY(coordY);
		return newPoint;
	}
	
	public double getDistanceBetweenPoints(Coords point1, Coords point2) {
		
		Coords newCoords = Coords.substractCoords(point1,
				point2);
		return Conversion.DEGREES_TO_KILOMETERS
				* Math.sqrt((Math.pow((newCoords.getX()), 2) + (Math.pow(
						(newCoords.getY()), 2))));
	}
	
	/**
	 * Fkcja wyznacza dwie proste prostopadle do linii toru lotu w punktach: startowym i poczatkowym.
	 * Nastêpnie sprawdza czy, wspolrzedne lotniska zapasowego leza pomiedzy tymi prostymi.
	 * Pozwala to zapobiec uwzgledniania lotnisk ktore leza przed lub za trasa samolotu.
	 */
	public boolean isOnRangeOfPath(Airport startAirport, Airport stopAirport,
			ReplacementAirport repAirport) {

		setFlightPath(startAirport, stopAirport);
		Coords straightParams = new Coords();
		straightParams.setX(getParamA());
		straightParams.setY(getParamB());
		Coords orthoParamsFromStart = straightOrthogonal(straightParams,
				startAirport.getCoords());
		Coords intersecPointWithStart = getIntersectionPoint(
				orthoParamsFromStart, repAirport.getCoords());
		Coords orthoParamsFromStop = straightOrthogonal(straightParams,
				stopAirport.getCoords());
		Coords intersecPointWithStop = getIntersectionPoint(
				orthoParamsFromStop, repAirport.getCoords());
		double distFromStart = getDistanceBetweenPoints(repAirport.getCoords(),
				intersecPointWithStart);
		double distFromStop = getDistanceBetweenPoints(repAirport.getCoords(),
				intersecPointWithStop);

		if ((distFromStart > getDistance(startAirport, stopAirport))
				|| distFromStop > getDistance(startAirport, stopAirport))
			return false;
		else
			return true;
	}
	
	public static void main(String[] args) {
		Airport a = new Airport();
		Airport b = new Airport();
		ReplacementAirport c = new ReplacementAirport();
		a.loadAirport("KRK");
		b.loadAirport("MMX");
		c.loadAirport("DRS");
		FlightPath fp = new FlightPath();
		boolean x = fp.isOnRangeOfPath(a, b, c);
		System.out.println(x);
	}

}
