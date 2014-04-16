package pl.areusmart.flightplan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightClock {

	public long parseTime(String time) {

		String[] timeArray = time.split(":");
		long timeInMiliSeconds = Integer.parseInt(timeArray[0])
				* Conversion.HOURS_TO_MILISECONDS;
		timeInMiliSeconds += Integer.parseInt(timeArray[1])
				* Conversion.MINUTES_TO_MILISECONDS;
		timeInMiliSeconds += Integer.parseInt(timeArray[2])
				* Conversion.SECONDS_TO_MILISECONDS;
		return timeInMiliSeconds;
	}
	
	
	//fkcja oblicza czas w którym samolot znajdzie siê nad kolejnymi pktami
	public List<Long> setTimeOverPoints(List<Airport> list, int speed) {

		List<Long> timeList = new ArrayList<>();
		for (Airport airport : list) {
			Long time = (long) ((airport.getDistanceFromStart() / speed) * Conversion.HOURS_TO_MILISECONDS);
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

}
