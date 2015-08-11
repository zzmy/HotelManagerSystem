package com.icss.tool;

import java.util.Calendar;

/**
 * ��ȡʱ��
 * @author ������
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

	//Ϊ����ȷ��ʾ��̨ʱ��
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
		return YEAR + "��" + MONTH + "��" + DAY + "��";
	}

	public static String getDayOfWeek() {
		String dayOfWeek = "";
		switch (WEEK) {
		case 1:
			dayOfWeek = "������";
			break;
		case 2:
			dayOfWeek = "����һ";
			break;
		case 3:
			dayOfWeek = "���ڶ�";
			break;
		case 4:
			dayOfWeek = "������";
			break;
		case 5:
			dayOfWeek = "������";
			break;
		case 6:
			dayOfWeek = "������";
		case 7:
			dayOfWeek = "������";
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
