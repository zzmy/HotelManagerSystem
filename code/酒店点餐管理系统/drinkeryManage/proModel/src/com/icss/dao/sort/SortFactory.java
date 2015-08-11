package com.icss.dao.sort;

/**
 * 菜系表数据管理的工厂类
 * @author 华莹
 * @version 1.0 2015-01-02
 */
public class SortFactory {
	//该类的构造方法要私有化，不允许其它用户创建对象
	private SortFactory()
	{}
	//添加静态方法，通过该方法获得dao唯一的对象
	/**
	 * 获得菜系管理数据访问对象
	 * @return 数据访问对象
	 */ 
	public static SortDao getInitialise()
	{
		return new SortDaoImpl();
	}
}
