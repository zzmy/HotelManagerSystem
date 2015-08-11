package com.icss.bean;

import java.util.Date;

/**
 * 订单表对应的Bean
 * @author 赵玉璐
 * @version 1.0 2015.1.2
 *
 */

public class Order_FormBean {
	private long num;        //唯一的订单编号
	private int desk_num;   //台号
	private Date datetime;  //订单日期
	private double money;   //结算金额
	private int user_id; //操作员
	
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


