package com.icss.dao.order_item;

/**
 * ������������ݶ��󹤳���
 * @author ������
 * @version 1.0 2015-01-02
 */
public class Order_ItemFactory {
	//����Ĺ��췽��Ҫ˽�л��������������û���������
		private Order_ItemFactory()
		{}
		//��Ӿ�̬������ͨ���÷������daoΨһ�Ķ���
		/**
		 * ��ö�����������ݷ��ʶ���
		 * @return ���ݷ��ʶ���
		 */ 
		public static Order_ItemDao getInitialise()
		{
			return new Order_ItemDaoImpl();
		}

}
