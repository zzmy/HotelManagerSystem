package com.icss.dao.order_form;

/**
 * �����������ݶ��󹤳���
 * @author �����
 * @version 1.0 2015.1.2
 */

public class Order_FormFactory {
	//����Ĺ��췽��˽�л��������������û���������
	private Order_FormFactory(){
		
	}
	//��Ӿ�̬������ͨ���÷������daoΨһ�Ķ���
	/**
	 * ��ö����������ݷ��ʶ���
	 * @return ���ݷ��ʶ���
	 */ 
	public static Order_FormDao getInitialise(){
		return new Order_FormDaoImpl();
	}
}
