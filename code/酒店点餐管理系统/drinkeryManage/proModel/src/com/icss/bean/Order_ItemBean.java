package com.icss.bean;
/**
 * ����������Ӧ��Bean
 * @author ������
 * @version 1.0 2015-01-02
 */
public class Order_ItemBean{
	private int id;             //��ˮ��
	private Long order_form_num; //�������
	private int menu_num;       //�˵����
	private int amount;         //����
	private double total;       //��ƷС�ƽ��
	
	public Order_ItemBean()
	{}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Long getOrder_form_num() {
		return order_form_num;
	}
	
	public void setOrder_form_num(Long order_form_num) {
		this.order_form_num = order_form_num;
	}
	
	public int getMenu_num() {
		return menu_num;
	}
	
	public void setMenu_num(int menu_num) {
		this.menu_num = menu_num;
	}
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}

}
