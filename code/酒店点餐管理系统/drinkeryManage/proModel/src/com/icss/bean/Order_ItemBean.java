package com.icss.bean;
/**
 * 订单详情表对应的Bean
 * @author 钟明媛
 * @version 1.0 2015-01-02
 */
public class Order_ItemBean{
	private int id;             //流水号
	private Long order_form_num; //订单编号
	private int menu_num;       //菜单编号
	private int amount;         //数量
	private double total;       //菜品小计金额
	
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
