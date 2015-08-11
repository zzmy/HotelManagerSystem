package com.icss.bean;

/**
 * 菜品表对应的Bean
 * @author 华莹
 * @version 1.0 2015-01-02
 */
public class MenuBean {
	int num;//菜品号
	int sort_id;//菜系编号
	String name;//商品名
	String code;//助记码
	String unit;//单位
	double unit_price;//单价
	String state;//菜品状态
	
	public MenuBean(){}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getSort_id() {
		return sort_id;
	}

	public void setSort_id(int sort_id) {
		this.sort_id = sort_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(double unit_price) {
		this.unit_price = unit_price;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
