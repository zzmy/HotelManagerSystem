package com.icss.tool;

import java.util.Calendar;

/**
 * 获取时间
 * @author 钟明媛
 * @version 1.0 2015-01-08
 */

public class Today {

	private static final Calendar NOW = Calendar.getInstance();

	private static final int YEAR = NOW.get(Calendar.YEAR);

	private static final int MONTH = NOW.get(Calendar.MONTH) + 1;

	private static final int DAY = NOW.get(Calendar.DAY_OF_MONTH);

	private static final int WEEK = NOW.get(Calendar.DAY_OF_WEEK);

	//
	public static String getDate() {
		return YEAR + "-" + MONTH + "-" + DAY;
	}

	//为了正确显示开台时间
	public static String getDateOfNum() {
		String y = YEAR + "";
		String m = MONTH + "";
		String d = DAY + "";
		if (MONTH < 10)
			m = "0" + MONTH;
		if (DAY < 10)
			d = "0" + DAY;
		return y + m + d;
	}

	public static String getDateOfShow() {
		return YEAR + "年" + MONTH + "月" + DAY + "日";
	}

	public static String getDayOfWeek() {
		String dayOfWeek = "";
		switch (WEEK) {
		case 1:
			dayOfWeek = "星期日";
			break;
		case 2:
			dayOfWeek = "星期一";
			break;
		case 3:
			dayOfWeek = "星期二";
			break;
		case 4:
			dayOfWeek = "星期三";
			break;
		case 5:
			dayOfWeek = "星期四";
			break;
		case 6:
			dayOfWeek = "星期五";
		case 7:
			dayOfWeek = "星期六";
			break;
		}
		return dayOfWeek;
	}

	public static String getTime() {
		Calendar NOW = Calendar.getInstance();
		StringBuffer time = new StringBuffer();
		int HOUR = NOW.get(Calendar.HOUR_OF_DAY);
		if(HOUR < 10)
		{
			time.append(0);
		}
		time.append(HOUR);
		int MINUTE = NOW.get(Calendar.MINUTE);
		if(MINUTE < 10)
		{
			time.append(0);
		}
		time.append(MINUTE);
		int SECOND = NOW.get(Calendar.SECOND);
		if(SECOND < 10)
		{
			time.append(0);
		}
		time.append(SECOND);
		return time.toString();
	}

	public static void main(String[] args) {
		System.out.println(Today.getDayOfWeek());
	}

	public static int getYEAR() {
		return YEAR;
	}

	public static int getDAY() {
		return DAY;
	}

	public static int getMONTH() {
		return MONTH;
	}
}
