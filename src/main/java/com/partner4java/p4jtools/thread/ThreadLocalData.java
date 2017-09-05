package com.partner4java.p4jtools.thread;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 从当前线程中获取一些基础数据或运算对象等
 * 
 * @author partner4java
 * 
 */
public class ThreadLocalData {
	private static ThreadLocal<Date> now = new ThreadLocal<Date>();

	/**
	 * 获取当前时间<br/>
	 * 不能用户精准判断，存在误差。
	 * 
	 * @return
	 */
	public static Date getNow() {
		if (now.get() == null) {
			now.set(new Date());
		}
		return now.get();
	}

	private static ThreadLocal<SimpleDateFormat> dateFormatFull = new ThreadLocal<SimpleDateFormat>();

	public static SimpleDateFormat getDateFormatFull() {
		if (dateFormatFull.get() == null) {
			dateFormatFull.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		}
		return dateFormatFull.get();
	}

	/**
	 * 获取当前天数累加日期字符串
	 * 
	 * @param day
	 * @return
	 */
	public static String getDateStr(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return getDateFormatDay().format(calendar.getTime());
	}

	/**
	 * 格式化格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseFull(String date) {
		try {
			return getDateFormatFull().parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化格式"yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFull(Date date) {
		try {
			return getDateFormatFull().format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ThreadLocal<SimpleDateFormat> dateFormatDay = new ThreadLocal<SimpleDateFormat>();

	public static SimpleDateFormat getDateFormatDay() {
		if (dateFormatDay.get() == null) {
			dateFormatDay.set(new SimpleDateFormat("yyyy-MM-dd"));
		}
		return dateFormatDay.get();
	}

	/**
	 * 格式化格式"yyyy-MM-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseDay(String date) {
		try {
			return getDateFormatDay().parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化格式"yyyy-MM-dd"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date) {
		try {
			return getDateFormatDay().format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ThreadLocal<SimpleDateFormat> dateFormatNoSlip = new ThreadLocal<SimpleDateFormat>();

	public static SimpleDateFormat getDateFormatNoSlip() {
		if (dateFormatNoSlip.get() == null) {
			dateFormatNoSlip.set(new SimpleDateFormat("yyyyMMdd"));
		}
		return dateFormatNoSlip.get();
	}

	/**
	 * 格式化格式"yyyyMMdd"
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseNoSlip(String date) {
		try {
			return getDateFormatNoSlip().parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化格式"yyyyMMdd"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatNoSlip(Date date) {
		try {
			return getDateFormatNoSlip().format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ThreadLocal<DecimalFormat> decimalFormat = new ThreadLocal<DecimalFormat>();

	public static DecimalFormat getDecimalFormat() {
		if (decimalFormat.get() == null) {
			decimalFormat.set(new DecimalFormat("#.##"));
		}
		return decimalFormat.get();
	}

	/**
	 * 格式化数字 "#.##"
	 * 
	 * @param number
	 * @return
	 */
	public static String formatDouble(double number) {
		return getDecimalFormat().format(number);
	}

	private static ThreadLocal<SimpleDateFormat> dateFormatFullNoSlip = new ThreadLocal<SimpleDateFormat>();

	public static SimpleDateFormat getDateFormatFullNoSlip() {
		if (dateFormatFullNoSlip.get() == null) {
			dateFormatFullNoSlip.set(new SimpleDateFormat("yyyyMMddHHmm"));
		}
		return dateFormatFullNoSlip.get();
	}

	/**
	 * 格式化格式"yyyyMMddHHmm"
	 * 
	 * @param date
	 * @return
	 */
	public static Date parseFullNoSlip(String date) {
		try {
			return getDateFormatFullNoSlip().parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化格式"yyyyMMddHHmm"
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFullNoSlip(Date date) {
		try {
			return getDateFormatFullNoSlip().format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ThreadLocal<SimpleDateFormat> dateFormatMinute = new ThreadLocal<SimpleDateFormat>();

	/**
	 * yyyy-MM-dd HH:mm格式
	 * 
	 * @return
	 */
	public static SimpleDateFormat getDateFormatMinute() {
		if (dateFormatMinute.get() == null) {
			dateFormatMinute.set(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		}
		return dateFormatMinute.get();
	}

	/**
	 * yyyy-MM-dd HH:mm格式
	 * 
	 * @return
	 */
	public static String formatMinute(Date date) {
		try {
			return getDateFormatMinute().format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static ThreadLocal<SimpleDateFormat> dateFormatMonthDay = new ThreadLocal<SimpleDateFormat>();
	
	/**
	 * MM-dd格式
	 * 
	 * @return
	 */
	public static SimpleDateFormat getDateFormatMonthDay() {
		if (dateFormatMonthDay.get() == null) {
			dateFormatMonthDay.set(new SimpleDateFormat("MM-dd"));
		}
		return dateFormatMonthDay.get();
	}

	/**
	 * MM-dd格式
	 * 
	 * @return
	 */
	public static String formatMonthDay(Date date) {
		try {
			return getDateFormatMonthDay().format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
