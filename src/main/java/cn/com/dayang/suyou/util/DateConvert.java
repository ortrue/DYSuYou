package cn.com.dayang.suyou.util;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateConvert {

	public static String DateSeparator = "-";

	public static String LongDatePattern = "yyyy" + DateSeparator + "MM"
			+ DateSeparator + "dd HH:mm:ss";

	public static String ShortDatePattern = "yyyy" + DateSeparator + "MM"
			+ DateSeparator + "dd";

	public static String CalendarToStr(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat(ShortDatePattern);
		if (cal != null) {
			Date date = cal.getTime();
			return format.format(date);
		}
		return "";
	}

	public static Date addDate(Date date, int amount) {
		return addDate(date, Calendar.DAY_OF_MONTH, amount);
	}

	public static Date addDate(Date date, int field, int amount) {
		Date newdate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		newdate = cal.getTime();
		return newdate;
	}
	
	public static String addDateToStr(Date date, int field, int amount) {
		SimpleDateFormat format = new SimpleDateFormat(ShortDatePattern);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, amount);
		Date newdate = cal.getTime();
		return format.format(newdate);
	}

	public static String CalTimeToStr(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat(LongDatePattern);
		if (cal != null) {
			Date date = cal.getTime();
			return format.format(date);
		}
		return "";
	}

	public static Calendar starCalOfWeek(Calendar day) {
		int temp = day.get(Calendar.DAY_OF_WEEK);
		switch (temp) {
		case 1:
			return day;
		case 2:
			day.add(Calendar.DAY_OF_MONTH, -1);
			return day;
		case 3:
			day.add(Calendar.DAY_OF_MONTH, -2);
			return day;
		case 4:
			day.add(Calendar.DAY_OF_MONTH, -3);
			return day;
		case 5:
			day.add(Calendar.DAY_OF_MONTH, -4);
			return day;
		case 6:
			day.add(Calendar.DAY_OF_MONTH, -5);
			return day;
		case 7:
			day.add(Calendar.DAY_OF_MONTH, -6);
			return day;
		}
		return day;
	}

	public static Calendar endCalOfWeek(Calendar day) {
		int temp = day.get(Calendar.DAY_OF_WEEK);
		switch (temp) {
		case 1:
			day.add(Calendar.DAY_OF_MONTH, 6);
			return day;
		case 2:
			day.add(Calendar.DAY_OF_MONTH, 5);
			return day;
		case 3:
			day.add(Calendar.DAY_OF_MONTH, 4);
			return day;
		case 4:
			day.add(Calendar.DAY_OF_MONTH, 3);
			return day;
		case 5:
			day.add(Calendar.DAY_OF_MONTH, 2);
			return day;
		case 6:
			day.add(Calendar.DAY_OF_MONTH, 1);
			return day;
		case 7:
			return day;
		}
		return day;
	}

	public static Date StrToShortDate(String str) {
		if (str.length() == 0) {
			return null;
		}
		int start = str.indexOf(DateSeparator);
		String year = str.substring(0, start);
		++start;
		int start1 = str.indexOf(DateSeparator, start);
		String month = str.substring(start, start1);
		String day = str.substring(start1 + 1, start1 + 3);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));

		return cal.getTime();
	}

	public static Calendar StrToCalendar(String str) {
		if (str.length() == 0) {
			return null;
		}
		int start = str.indexOf(DateSeparator);
		String year = str.substring(0, start);
		++start;
		int start1 = str.indexOf(DateSeparator, start);
		String month = str.substring(start, start1);
		String day = str.substring(start1 + 1, start1 + 3);
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.YEAR, Integer.parseInt(year));
		cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
		cal.set(Calendar.DATE, Integer.parseInt(day));
		return cal;
	}

	public static String dayOfWeek(Calendar day) {
		int temp = day.get(Calendar.DAY_OF_WEEK);
		switch (temp) {
		case 1:
			return "Sun";
		case 2:
			return "Mon";
		case 3:
			return "Tue";
		case 4:
			return "Wed";
		case 5:
			return "Thu";
		case 6:
			return "Fri";
		case 7:
			return "Sat";
		}
		return "";
	}

	public static String dayOfWeek(String inDay) {
		Calendar cal = Calendar.getInstance();

		cal.setTime(StrToShortDate(inDay));
		int temp = cal.get(Calendar.DAY_OF_WEEK);
		switch (temp) {
		case 1:
			return "Sun";
		case 2:
			return "Mon";
		case 3:
			return "Tue";
		case 4:
			return "Wed";
		case 5:
			return "Thu";
		case 6:
			return "Fri";
		case 7:
			return "Sat";
		}
		return "";
	}

	public static Date StrToDate(String datetimeStr, String pattern)
			throws ParseException {
		return StrToDate(datetimeStr, pattern, null);
	}

	public static Date StrToDate(String datetimeStr, String pattern,
			Locale locale) throws ParseException {
		if ((datetimeStr == null) || (datetimeStr.equals(""))
				|| (datetimeStr.equals(" "))) {
			return null;
		}
		Date date = null;
		if (locale == null)
			locale = Locale.getDefault();
		SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
		try {
			date = format.parse(datetimeStr);
			return date;
		} catch (ParseException e) {
			throw e;
		}
	}

	public static String DateToStr(Date datetime, String pattern) {
		return DateToStr(datetime, pattern, null);
	}

	public static String DateToStr(Date datetime, String pattern, Locale locale) {
		String strdate = "";
		if (datetime == null) {
			return "";
		}
		if (locale == null)
			locale = Locale.getDefault();
		SimpleDateFormat simformat = new SimpleDateFormat(pattern, locale);
		strdate = simformat.format(datetime);
		return strdate;
	}

	public static Timestamp StrToTimestamp(String timestampStr, String pattern)
			throws ParseException {
		Date date = null;
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		try {
			date = format.parse(timestampStr);
		} catch (ParseException e) {
			throw e;
		}
		return new Timestamp(date.getTime());
	}

	public static String changeFormat(String original,
			SimpleDateFormat originalFormat, SimpleDateFormat newFormat) {
		if ("".equals(original))
			return "";
		try {
			Date date = originalFormat.parse(original);
			return newFormat.format(date);
		} catch (ParseException e) {
		}
		return "";
	}

	public static Date getRelativeTime(Date original, int intervalUnit,
			int intervalCount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(original);
		calendar.add(intervalUnit, intervalCount);
		return calendar.getTime();
	}

	public static boolean ifInSameDay(Date d1, Date d2) {
		String a = DateToStr(d1, ShortDatePattern);
		String b = DateToStr(d2, ShortDatePattern);

		return (a.equals(b));
	}

	public static InputStream getStream(String resource) throws Exception {
		return DateConvert.class.getClassLoader().getResourceAsStream(resource);
	}

	public static InputStream getFileStream(String filename) throws Exception {
		return new BufferedInputStream(new FileInputStream(filename));
	}

}