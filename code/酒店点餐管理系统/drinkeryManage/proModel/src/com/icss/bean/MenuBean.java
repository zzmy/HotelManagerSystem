package com.icss.bean;

/**
 * ��Ʒ���Ӧ��Bean
 * @author ��Ө
 * @version 1.0 2015-01-02
 */
public class MenuBean {
	int num;//��Ʒ��
	int sort_id;//��ϵ���
	String name;//��Ʒ��
	String code;//������
	String unit;//��λ
	double unit_price;//����
	String state;//��Ʒ״̬
	
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
