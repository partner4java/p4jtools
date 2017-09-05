package com.partner4java.p4jtools.time;

import java.util.Calendar;
import java.util.Date;

/**
 * 对时间的修改操作<br/>
 * 如果算出若干天后的时间。
 * 
 * @author partner4java
 * 
 */
public class EditTime {
	/**
	 * 算出几天后的日期
	 * 
	 * @param day
	 *            加天数
	 * @return
	 */
	public static Date addDay(int day) {
		return addDay(new Date(), day);
	}

	/**
	 * 算出几天后的日期
	 * 
	 * @param date
	 *            基础日期
	 * @param day
	 *            加天数
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	
	/**
	 * 减少N个小时
	 * @param date
	 * @param hours
	 * @return
	 */
	public static Date decreaseHours(Date date,int hours){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -hours);
		return calendar.getTime();
	}
}
