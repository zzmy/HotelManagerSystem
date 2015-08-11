package com.icss.dao.order_item;

/**
 * 订单详情表数据对象工厂类
 * @author 钟明媛
 * @version 1.0 2015-01-02
 */
public class Order_ItemFactory {
	//该类的构造方法要私有化，不允许其它用户创建对象
		private Order_ItemFactory()
		{}
		//添加静态方法，通过该方法获得dao唯一的对象
		/**
		 * 获得订单详情表数据访问对象
		 * @return 数据访问对象
		 */ 
		public static Order_ItemDao getInitialise()
		{
			return new Order_ItemDaoImpl();
		}

}
