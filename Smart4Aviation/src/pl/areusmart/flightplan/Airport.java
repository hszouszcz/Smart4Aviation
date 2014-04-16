package pl.areusmart.flightplan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

public class Airport implements Comparable<Airport> {

	private String name;
	private String city;
	private String country;
	private String IATAcode;
	private String continent;
	private double distanceFromStart;
	private int id;
	private Coords coords;

	public Airport() {
		name = "none";
		city = "none";
		country = "none";
		IATAcode = "none";
		continent = "none";
		coords = new Coords();
		distanceFromStart = 0;
		id = 0;
	}

	public void loadAirport(String IATA) {
		BufferedReader br = null;
		String currentLine = "none";
		StringBuilder sb = new StringBuilder("\"");
		sb.append(IATA);
		sb.append("\"");
		IATA = sb.toString();

		try {
			br = new BufferedReader(new FileReader(Constants.FILE_PATH));
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.contains(IATA) == true) {
					setAirportParams(currentLine);
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

	
	// Fkcja pobiera dane lotniska z pliku aiports.dat
	public void setAirportParams(String rawParams) {

		String[] arrayOfParams = rawParams.split(",");
		Coords coords = new Coords();
		coords.setX(Double.parseDouble(arrayOfParams[arrayOfParams.length - 4]));
		coords.setY(Double.parseDouble(arrayOfParams[arrayOfParams.length - 5]));
		setCoords(coords);
		continent = arrayOfParams[arrayOfParams.length - 1].replace("\"", "");
		arrayOfParams = rawParams.replace(",", "").split("\"");
		id = Integer.parseInt(arrayOfParams[0]);
		name = arrayOfParams[1];
		city = arrayOfParams[3];
		country = arrayOfParams[5];
		IATAcode = arrayOfParams[7].replace("\"", "");
	}

	public Coords getCoords() {
		return coords;
	}

	public String getIATA() {
		return IATAcode;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	public String getCountry() {
		return country;
	}

	public int getID() {
		return id;
	}

	public void setCoords(Coords newCoords) {
		coords = newCoords;
	}

	public double getDistanceFromStart() {
		return distanceFromStart;
	}

	public String getContinent() {
		return continent;
	}

	public void setDistanceFromStart(double dist) {
		distanceFromStart = dist;
	}

	@Override
	public int compareTo(Airport o) {
		if (getDistanceFromStart() > o.getDistanceFromStart()) {
			return 1;
		} else if (getDistanceFromStart() == o.getDistanceFromStart()) {
			return 0;
		} else
			return -1;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append(getIATA() + " " + getCity() + " " + getCountry() + " "
				+ new DecimalFormat("#0.0000").format(getCoords().getY()) + " "
				+ new DecimalFormat("#0.0000").format(getCoords().getX()) + " ");
		return sb.toString();
	}

	public static void main(String[] args) {
		Airport a = new Airport();
		a.loadAirport("KRK");
		System.out.println(a.toString());
	}
}
