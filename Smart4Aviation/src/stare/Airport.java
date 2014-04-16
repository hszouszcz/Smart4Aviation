package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Airport implements Comparable<Airport> {

	private String airportName;
	private String city;
	private String country;
	private String IATAcode;
	private double coordX;
	private double coordY;
	private int airportID;
	private String continent;
	private double distanceFromStart;

	public Airport() {
		airportID = 0;
		airportName = "none";
		city = "none";
		country = "none";
		IATAcode = "none";
		coordX = 0;
		coordY = 0;
		continent = "none";
	}

	public void getDataFromFile(String iata) {

		BufferedReader br = null;
		String sCurrentLine = "none";
		int i = 0;
		StringBuilder sb = new StringBuilder("\"");
		sb.append(iata);
		sb.append("\"");
		iata = sb.toString();

		try {

			br = new BufferedReader(new FileReader("src/airports.dat"));

			while (!sCurrentLine.isEmpty()) {

				sCurrentLine = br.readLine();

				if (sCurrentLine.contains(iata) == true) {
					this.setAirport(sCurrentLine);
					break;
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	public void setAirport(String line) {

		String[] dataList = null;

		dataList = line.split(",");
		setAirportPartA(dataList);
		dataList = line.replace(",", "").split("\"");
		setAirportPartB(dataList);
	}

	public void setAirportPartA(String data[]) {
		this.coordX = Double.parseDouble(data[data.length - 4]);
		this.coordY = Double.parseDouble(data[data.length - 5]);
		this.continent = data[data.length - 1].replace("\"", "");
	}

	public void setAirportPartB(String data[]) {

		this.airportID = Integer.parseInt(data[0]);
		this.airportName = data[1];
		this.city = data[3];
		this.country = data[5];
		this.IATAcode = data[7].replace("\"", "");
	}

	public double[] getIntersectionPoint(Airport a, Airport b) { // nie wiem czy
																	// ma to
																	// sens

		double[] tab = new double[2];
		tab[0] = this.getCoordX();
		tab[1] = this.getCoordY();
		return tab;
	}

	public double getCoordX() {
		return coordX;
	}

	public double getCoordY() {
		return coordY;
	}

	public String getIATA() {

		return IATAcode;
	}

	public String getCity() {

		return city;
	}

	public String getAirportName() {

		return airportName;
	}

	public String getCountry() {

		return country;
	}

	public int getAirportID() {

		return airportID;
	}

	public String getContinent() {

		return continent;
	}

	public double getDistanceFromStart() {

		return this.distanceFromStart;
	}

	public void setCoordX(double a) {
		this.coordX = a;
	}

	public void setCoordY(double a) {
		this.coordY = a;
	}

	public void setDistacneFromStart(double dist) {
		this.distanceFromStart = dist;
	}

	public static void main(String[] args) {

		Airport a = new Airport();
		Airport b = new Airport();
		a.getDataFromFile("NFO");
		System.out.println(a.airportName);
		System.out.println(a.getContinent());
		System.out.println(a.getIATA());
		System.out.println(a.getCoordX());

	}

	@Override
	public int compareTo(Airport o) {
		if (this.getDistanceFromStart() > o.getDistanceFromStart()) {
			return 1;
		} else if (this.getDistanceFromStart() == o.getDistanceFromStart()) {
			return 0;
		} else
			return -1;
	}

}
