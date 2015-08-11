package com.icss.dao.desk;

/**
 * 餐台管理数据对象工厂类
 * @author 华莹
 * @version 1.0 2014-12-31
 */
public class DeskFactory {
	private DeskFactory() {
	}
	/**
	 * 获得餐台管理数据访问对象
	 * @return 数据访问对象
	 */ 
	public static DeskDaoImpl getInitialise() {
		return new DeskDaoImpl();
	}
}
