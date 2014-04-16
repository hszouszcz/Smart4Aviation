package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightPlan {
	int counter = 0;
	private Airport startAirport;
	private Airport stopAirport;
	private FlightPath flightPath;
	private List<Airport> listOfAirports;
	private List<Long> timeOverPoint;

	public enum Direction {
		UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT, NONE
	}

	public FlightPlan() {
		startAirport = new Airport();
		stopAirport = new Airport();
		flightPath = new FlightPath();
		listOfAirports = new ArrayList<>(10);
		timeOverPoint = new ArrayList<>(10);

	}

	public FlightPlan(Airport startAirport, Airport stopAirport) {

		this.startAirport = startAirport;
		this.stopAirport = stopAirport;
		flightPath = new FlightPath();
		flightPath.setFlightPath(this.startAirport, this.stopAirport);
		this.timeOverPoint = new ArrayList<>(10);

	}

	public long getTimeFromString(String time) {

		String[] timeArray = new String[3];
		long timeInMiliseconds;
		timeArray = time.split(":");
		timeInMiliseconds = Integer.parseInt(timeArray[0]) * 3600000;
		timeInMiliseconds += Integer.parseInt(timeArray[0]) * 60000;
		timeInMiliseconds += Integer.parseInt(timeArray[0]) * 1000;
		return timeInMiliseconds;
	}

	public List<Airport> selectionOfAirports(Airport startAirport,
			Airport stopAirport, double distance, int speed) {

		BufferedReader br = null;
		List<Airport> listOfAirports = new ArrayList<>(10);
		listOfAirports.add(startAirport);
		try {

			String CurrentLine = "none,";
			br = new BufferedReader(new FileReader("src/airports.dat"));

			while ((CurrentLine = br.readLine()) != null) {
				counter++;

				ReplacementAirport repAirport = new ReplacementAirport();
				repAirport.setAirport(CurrentLine);
				if (repAirport.getContinent().equalsIgnoreCase("E")
						&& !repAirport.getIATA().equalsIgnoreCase("")) {

					if (repAirport.getAirportID() != startAirport
							.getAirportID()
							&& (repAirport.getAirportID() != stopAirport
									.getAirportID())) {
						switch (getDirection(startAirport.getCoordX(),
								startAirport.getCoordY(),
								stopAirport.getCoordX(),
								stopAirport.getCoordY())) {
						case UP_RIGHT:
							if (repAirport.getDistanceFromPath(startAirport,
									stopAirport) <= distance) {
								if (repAirport.getCoordX() < stopAirport
										.getCoordX()
										&& repAirport.getCoordY() < stopAirport
												.getCoordY()) {
									if (repAirport.getCoordX() < startAirport
											.getCoordX()
											&& repAirport.getCoordY() > stopAirport
													.getCoordY()) {
										listOfAirports.add(repAirport);
										double[] temp = new double[2];
										temp = listOfAirports.get(
												listOfAirports.size() - 1)
												.getIntersectionPoint(
														startAirport,
														stopAirport);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordX(temp[0]);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordY(temp[1]);
										listOfAirports
												.get(listOfAirports.size() - 1)
												.setDistacneFromStart(
														flightPath
																.getDistance(
																		this.startAirport,
																		listOfAirports
																				.get(listOfAirports
																						.size() - 1)));

									}
								}
							}
							break;

						case UP_LEFT:
							if (repAirport.getDistanceFromPath(startAirport,
									stopAirport) <= distance) {
								if (repAirport.getCoordX() > stopAirport
										.getCoordX()
										&& repAirport.getCoordY() < stopAirport
												.getCoordY()) {
									if (repAirport.getCoordX() < startAirport
											.getCoordX()
											&& repAirport.getCoordY() > startAirport
													.getCoordY()) {
										listOfAirports.add(repAirport);
										double[] temp = new double[2];
										temp = listOfAirports.get(
												listOfAirports.size() - 1)
												.getIntersectionPoint(
														startAirport,
														stopAirport);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordX(temp[0]);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordY(temp[1]);
										listOfAirports
												.get(listOfAirports.size() - 1)
												.setDistacneFromStart(
														flightPath
																.getDistance(
																		this.startAirport,
																		listOfAirports
																				.get(listOfAirports
																						.size() - 1)));

									}
								}
							}
							break;

						case DOWN_RIGHT:
							if (repAirport.getDistanceFromPath(startAirport,
									stopAirport) <= distance) {
								if (repAirport.getCoordX() > stopAirport
										.getCoordX()
										&& repAirport.getCoordY() > stopAirport
												.getCoordY()) {
									if (repAirport.getCoordX() < startAirport
											.getCoordX()
											&& repAirport.getCoordY() < startAirport
													.getCoordY()) {
										listOfAirports.add(repAirport);
										double[] temp = new double[2];
										temp = listOfAirports.get(
												listOfAirports.size() - 1)
												.getIntersectionPoint(
														startAirport,
														stopAirport);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordX(temp[0]);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordY(temp[1]);
										listOfAirports
												.get(listOfAirports.size() - 1)
												.setDistacneFromStart(
														flightPath
																.getDistance(
																		this.startAirport,
																		listOfAirports
																				.get(listOfAirports
																						.size() - 1)));

									}
								}
							}
							break;

						case DOWN_LEFT:
							if (repAirport.getDistanceFromPath(startAirport,
									stopAirport) <= distance) {
								if (repAirport.getCoordX() > stopAirport
										.getCoordX()
										&& repAirport.getCoordY() < stopAirport
												.getCoordY()) {
									if (repAirport.getCoordX() < startAirport
											.getCoordX()
											&& repAirport.getCoordY() > startAirport
													.getCoordY()) {
										listOfAirports.add(repAirport);
										double[] temp = new double[2];
										temp = listOfAirports.get(
												listOfAirports.size() - 1)
												.getIntersectionPoint(
														startAirport,
														stopAirport);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordX(temp[0]);
										listOfAirports.get(
												listOfAirports.size() - 1)
												.setCoordY(temp[1]);
										listOfAirports
												.get(listOfAirports.size() - 1)
												.setDistacneFromStart(
														flightPath
																.getDistance(
																		this.startAirport,
																		listOfAirports
																				.get(listOfAirports
																						.size() - 1)));

									}
								}
							}

						default:
							break;
						}

					}

				} else {
				}

			}

			listOfAirports.add(stopAirport);
			listOfAirports.get(listOfAirports.size() - 1).setDistacneFromStart(
					flightPath.getDistance(this.startAirport,
							listOfAirports.get(listOfAirports.size() - 1)));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			System.out.println(counter);
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

	private Direction getDirection(double x1, double y1, double x2, double y2) {
		// Direction dir;
		double courseX = startAirport.getCoordX() - stopAirport.getCoordX();
		double courseY = startAirport.getCoordY() - stopAirport.getCoordY();
		if (courseX > 0 && courseY < 0) {
			return Direction.UP_LEFT;
		} else if (courseX < 0 && courseY < 0) {
			return Direction.UP_RIGHT;
		} else if (courseX > 0 && courseY > 0) {
			return Direction.DOWN_LEFT;
		} else if (courseX < 0 && courseY > 0) {
			return Direction.DOWN_RIGHT;
		} else {

			return Direction.NONE;
		}

	}

	public List<Long> getTime(List<Airport> list, int speed) {

		List<Long> timeList = new ArrayList<Long>();
		for (Airport a : list) {
			Long time = (long) (3600000 * (a.getDistanceFromStart() / speed));
			timeList.add(time);
		}
		return timeList;
	}

	public void printTimeOverPoint(long time) {
		String hms = String.format(
				"%02d:%02d:%02d",
				TimeUnit.MILLISECONDS.toHours(time),
				TimeUnit.MILLISECONDS.toMinutes(time)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
								.toHours(time)),
				TimeUnit.MILLISECONDS.toSeconds(time)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(time)));
		System.out.println(hms);
	}

	public void printFlightPlan(List<Airport> airportList, List<Long> timeList,
			String timeOfStart) {

		System.out.printf(
				airportList.get(0).getIATA() + " "
						+ airportList.get(0).getCity() + " "
						+ airportList.get(0).getCountry() + " " + "%.4f" + " "
						+ "%.4f" + " ", airportList.get(0).getCoordY(),
				airportList.get(0).getCoordX());
		printTimeOverPoint(getTimeFromString(timeOfStart));

		for (int i = 1; i < airportList.size() - 1; i++) {
			System.out.printf(airportList.get(i).getIATA() + " "
					+ airportList.get(i).getCity() + " "
					+ airportList.get(i).getCountry() + " " + "%.4f" + " "
					+ "%.4f" + " ", airportList.get(i).getCoordY(), airportList
					.get(i).getCoordX());
			printTimeOverPoint(timeList.get(i) + getTimeFromString(timeOfStart));

		}
		System.out.printf(airportList.get(airportList.size() - 1).getIATA()
				+ " " + airportList.get(airportList.size() - 1).getCity() + " "
				+ airportList.get(airportList.size() - 1).getCountry() + " "
				+ "%.4f" + " " + "%.4f" + " ",
				airportList.get(airportList.size() - 1).getCoordY(),
				airportList.get(airportList.size() - 1).getCoordX());
		printTimeOverPoint((timeList.get(timeList.size() - 1))
				+ getTimeFromString(timeOfStart));

	}

	public List<Airport> getlistOfAirports() {

		return this.listOfAirports;
	}

}