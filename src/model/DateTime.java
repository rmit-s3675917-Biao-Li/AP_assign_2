package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;

public class DateTime {
	// testing purpose
	private long advance;
	// time is in millis format.
	private long time;

	// set to be current time.
	public DateTime() {
		time = System.currentTimeMillis();

	}

	// set Clock Forward In Days
	public DateTime(int setClockForwardInDays) {
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = System.currentTimeMillis() + advance;
	}

	public DateTime(String s) throws Exception {
		String[] a;
		a = s.split("/");
		setDate(Integer.parseInt(a[0]),Integer.parseInt(a[1]),Integer.parseInt(a[2]));
		
	}
	// startdate's millis plus forwardindays's millis
	public DateTime(DateTime startDate, int setClockForwardInDays) {
		advance = ((setClockForwardInDays * 24L + 0) * 60L) * 60000L;
		time = startDate.getTime() + advance;
	}

	// set time(constructor)
	public DateTime(int day, int month, int year) {
		setDate(day, month, year);
	}

	// set time(supported method)
	private void setDate(int day, int month, int year) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day, 0, 0);

		java.util.Date date = calendar.getTime();

		time = date.getTime();
	}

	// return time in millis
	public long getTime() {
		return time;
	}

	// return readable format of time
	public String toString() {
		// long currentTime = getTime();
		// Date gct = new Date(currentTime);
		// return gct.toString();
		return getFormattedDate();
	}

	// return current time of current time
	public static String getCurrentTime() {
		Date date = new Date(System.currentTimeMillis()); // returns current Date/Time
		return date.toString();
	}

	// private method
	public String getFormattedDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);

		return sdf.format(gct);
	}

	//// private method
	public String getEightDigitDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
		long currentTime = getTime();
		Date gct = new Date(currentTime);

		return sdf.format(gct);
	}

	// returns difference in days
	public int diffDays(DateTime endDate, DateTime startDate) {
		final long HOURS_IN_DAY = 24L;
		final int MINUTES_IN_HOUR = 60;
		final int SECONDS_IN_MINUTES = 60;
		final int MILLISECONDS_IN_SECOND = 1000;
		long convertToDays = HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTES * MILLISECONDS_IN_SECOND;
		long hirePeriod = endDate.getTime() - startDate.getTime();
		double difference = (double) hirePeriod / (double) convertToDays;
		int round = (int) Math.round(difference);
		return round;
	}

	// Advances date/time by specified days, hours and mins for testing purposes
	public void setAdvance(int days, int hours, int mins) {
		advance = ((days * 24L + hours) * 60L) * 60000L;
	}

	public String getDayOfWeek() {
		SimpleDateFormat sdf = new SimpleDateFormat("u");
		long currentTime = getTime();
		Date gct = new Date(currentTime);
		return sdf.format(gct);
	}
}
