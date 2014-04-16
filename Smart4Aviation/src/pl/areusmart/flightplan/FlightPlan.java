package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class FlightPlan {

	private Airport startAirport;
	private Airport stopAirport;
	private FlightPath flightPath;
	private FlightClock flightClock;
	public int counter = 0;

	public FlightPlan(Airport startAirport, Airport stopAirport) {
		this.startAirport = startAirport;
		this.stopAirport = stopAirport;
		flightPath = new FlightPath();
		flightPath.setFlightPath(startAirport, stopAirport);
		flightClock = new FlightClock();
	}

	//fkcja tworzy listê lotnisk zapasowych
	
	/** Jeœli chodzi o sam algorytm wyznaczania lotnisk zapasowych: 
	 * 		Na poczatek sprawdzane sa lotniska, czy lezy w europie i jest okreslone jako pasazerskie.
	 * 		Nastêpnie sprawdzam na podstawie wyznaczania prostej prostopadlej do trasy lotu samolotu,
	 * 		czy lotnisko znajduje sie w zakresie zadanej odleglosci od trasy lotu.
	 * 		Kolejnym krokiem jest wyeliminowanie lotniks ktore moga spelniac powyzsze warunki, lecz moga sie
	 * 		znajdowac przed lub za punktami startu/ladowania. Uznalem, ze sa one zbedne i interesuja mnie
	 * 		jedynie lotniska znajdujace sie "na lewo i prawo" od trasy.
	 */
	public List<Airport> createListOfAirportsOnPath(double distanceFromPath) {

		BufferedReader br = null;
		List<Airport> listOfAirports = new ArrayList<>();
		listOfAirports.add(startAirport);
		Coords flightPathParams = new Coords();
		flightPathParams.setX(flightPath.getParamA());
		flightPathParams.setY(flightPath.getParamB());

		String currentLine = "none";
		try {

			br = new BufferedReader(new FileReader(Constants.FILE_PATH));

			while ((currentLine = br.readLine()) != null) {
				counter++;
				ReplacementAirport repAirport = new ReplacementAirport();
				repAirport.setAirportParams(currentLine);
				if (canBeAReplacementAirport(repAirport, distanceFromPath)) {

					Coords point = flightPath.getIntersectionPoint(
							flightPathParams, repAirport.getCoords());
					repAirport.setCoords(point);
					repAirport.setDistanceFromStart(flightPath.getDistance(
							startAirport, repAirport));
					listOfAirports.add(repAirport);

				}

			}

			listOfAirports.add(stopAirport);
			listOfAirports.get(listOfAirports.size() - 1).setDistanceFromStart(
					flightPath.getDistance(this.startAirport,
							listOfAirports.get(listOfAirports.size() - 1)));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {

			System.out.println(listOfAirports.size());
			System.out.println(listOfAirports.get(3).getIATA());
		} finally {

			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		return listOfAirports;
	}

	// fkcja sprawdza, czy lotnisko spe³nia
	// wymogi lotniska zapasowego
	private boolean canBeAReplacementAirport(ReplacementAirport airport,
			double distanceFromPath) {
		boolean firstCondition = false;
		boolean secondCondition = false;
		if (airport.getContinent().equalsIgnoreCase(Constants.EUROPE_TAG)
				&& !airport.getIATA().equalsIgnoreCase(Constants.EMPTY_STRING)) {
			if (airport.getID() != startAirport.getID()
					&& airport.getID() != stopAirport.getID()) {
				if (airport
						.getDistanceFromFlightPath(startAirport, stopAirport) <= distanceFromPath)
					firstCondition = true;

			}

		}

		secondCondition = flightPath.isOnRangeOfPath(startAirport, stopAirport,
				airport);
		if (firstCondition && secondCondition)
			return true;
		else
			return false;
	}
	


	// fkcja wypisuje plan lotu
	public void printFlightPlan(List<Airport> airportList, List<Long> timeList,
			String timeOfStart) { 

		System.out.printf(airportList.get(0).toString());

		flightClock.printTimeOverPoint(flightClock.parseTime(timeOfStart));

		for (int i = 1; i < airportList.size() - 1; i++) {
			System.out.printf(airportList.get(i).toString());
			flightClock.printTimeOverPoint(timeList.get(i)
					+ flightClock.parseTime(timeOfStart));
		}

		System.out.printf(airportList.get(airportList.size() - 1).toString());
		flightClock.printTimeOverPoint((timeList.get(timeList.size() - 1))
				+ flightClock.parseTime(timeOfStart));

	}

}
