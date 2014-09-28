/* ==================================================================
 * Created [2009-4-27 下午11:32:55] by Jon.King
 * ==================================================================
 * TSS
 * ==================================================================
 * mailTo:jinpujun@hotmail.com
 * Copyright (c) Jon.King, 2009-2012
 * ==================================================================
 */

package cn.com.dayang.suyou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 时间、字符串转换工具
 * </p>
 */
public class DateUtil {

	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

	private static final String sdf1reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";

	public static final SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private static final String sdf2reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";

	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			DEFAULT_DATE_PATTERN);

	private static final String sdf3reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";

	private static final SimpleDateFormat sdf3 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");

	private static final String sdf4reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";

	private static final SimpleDateFormat sdf4 = new SimpleDateFormat(
			"yyyy/MM/dd");

	/**
	 * <p>
	 * 将日期字符串解析成日期对象，支持以下格式
	 * <li>yyyy-MM-dd HH:mm:ss
	 * <li>yyyy-MM-dd
	 * <li>yyyy/MM/dd HH:mm:ss
	 * <li>yyyy/MM/dd
	 * </p>
	 * 
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static Date parse(String str) throws Exception {
		if (EasyUtils.isNullOrEmpty(str))
			return null;

		Date date = null;
		Pattern p1 = Pattern.compile(sdf1reg);
		Matcher m1 = p1.matcher(str);
		Pattern p2 = Pattern.compile(sdf2reg);
		Matcher m2 = p2.matcher(str);
		Pattern p3 = Pattern.compile(sdf3reg);
		Matcher m3 = p3.matcher(str);
		Pattern p4 = Pattern.compile(sdf4reg);
		Matcher m4 = p4.matcher(str);
		try {
			if (m1.matches()) {
				date = sdf1.parse(str);
			} else if (m2.matches()) {
				date = sdf2.parse(str);
			} else if (m3.matches()) {
				date = sdf3.parse(str);
			} else if (m4.matches()) {
				date = sdf4.parse(str);
			}
		} catch (ParseException e) {
			throw new Exception("非法日期字符串，解析失败：" + str, e);
		}
		return date;
	}

	/**
	 * <p>
	 * 将日期格式化成字符串：yyyy-MM-dd
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		if (date == null)
			return "";

		final SimpleDateFormat sdf = new SimpleDateFormat(
				DateUtil.DEFAULT_DATE_PATTERN);
		return sdf.format(date);
	}

	/**
	 * <p>
	 * 将日期格式化成字符串：yyyy-MM-dd HH:mm:ss
	 * </p>
	 * 
	 * @param date
	 * @return
	 */
	public static String formatCare2Second(Date date) {
		if (date == null)
			return "";

		return sdf1.format(date);
	}

	/**
	 * <p>
	 * 将日期格式化成相应格式的字符串，如：
	 * <li>yyyy-MM-dd HH:mm:ss
	 * <li>yyyy-MM-dd
	 * <li>yyyy/MM/dd HH:mm:ss
	 * <li>yyyy/MM/dd
	 * </p>
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null)
			return "";

		if (pattern == null || "".equals(pattern)) {
			pattern = DateUtil.DEFAULT_DATE_PATTERN;
		}

		final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

}