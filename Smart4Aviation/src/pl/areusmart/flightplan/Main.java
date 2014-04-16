package pl.areusmart.flightplan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		Airport startAirport = new Airport();
		startAirport.loadAirport(args[0]);

		Airport stopAirport = new Airport();
		stopAirport.loadAirport(args[1]);

		int distanceToReplacementAirPort = Integer.parseInt(args[2]);
		int speed = Integer.parseInt(args[3]);
		String time = args[4];

		FlightPlan flightPlan;
		flightPlan = new FlightPlan(startAirport, stopAirport);

		List<Airport> listOfAirports = new ArrayList<Airport>();
		FlightClock flightClock = new FlightClock();

		listOfAirports = flightPlan
				.createListOfAirportsOnPath(distanceToReplacementAirPort);

		List<Long> timeList = new ArrayList<Long>();

		Collections.sort(listOfAirports);

		timeList = flightClock.setTimeOverPoints(listOfAirports, speed);

		flightPlan.printFlightPlan(listOfAirports, timeList, time);

	}
}
