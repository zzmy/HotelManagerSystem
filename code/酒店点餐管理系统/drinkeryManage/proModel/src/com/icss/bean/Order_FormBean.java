package com.icss.bean;

import java.util.Date;

/**
 * �������Ӧ��Bean
 * @author �����
 * @version 1.0 2015.1.2
 *
 */

public class Order_FormBean {
	private long num;        //Ψһ�Ķ������
	private int desk_num;   //̨��
	private Date datetime;  //��������
	private double money;   //������
	private int user_id; //����Ա
	
	public Order_FormBean() {
		// TODO Auto-generated constructor stub
	}
	public long getNum() {
		return num;
	}
	public void setNum(long l) {
		this.num = l;
	}
	public int getDesk_num() {
		return desk_num;
	}
	public void setDesk_num(int desk_num) {
		this.desk_num = desk_num;
	}
	public Date getDatetime() {
		return datetime;
	}
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}	
}


