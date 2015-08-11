package com.icss.dao.order_form;

/**
 * 订单管理数据对象工厂类
 * @author 赵玉璐
 * @version 1.0 2015.1.2
 */

public class Order_FormFactory {
	//该类的构造方法私有化，不允许其它用户创建对象
	private Order_FormFactory(){
		
	}
	//添加静态方法，通过该方法获得dao唯一的对象
	/**
	 * 获得订单管理数据访问对象
	 * @return 数据访问对象
	 */ 
	public static Order_FormDao getInitialise(){
		return new Order_FormDaoImpl();
	}
}
