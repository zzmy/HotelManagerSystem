package com.icss.bean;

/**
 * 餐桌表对应的Bean
 * @author 华莹
 * @version 1.0 2014-12-31
 */
public class DeskBean {
	private int num;		//餐桌号
	private int seating;	//座位数
	
	public DeskBean() {
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSeating() {
		return seating;
	}

	public void setSeating(int seating) {
		this.seating = seating;
	};
}
