package com.icss.dao.user;

/**
 * 用户信息管理数据对象工厂类
 * @author 李振元
 * @version 1.0 2014-12-31
 */
public class UserFactory {
	private UserFactory() {
	}
	/**
	 * 获得用户信息管理数据访问对象
	 * @return 数据访问对象
	 */ 
	public static UserDaoImpl getInitialise() {
		return new UserDaoImpl();
	}
}
