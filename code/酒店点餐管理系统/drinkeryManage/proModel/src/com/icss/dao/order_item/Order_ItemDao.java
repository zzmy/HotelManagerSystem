package com.icss.dao.order_item;

import java.util.Vector;

import com.icss.bean.Order_ItemBean;

/**
 * ������������ݷ��ʽӿ�
 * @author ������
 * @version 1.0 2015-01-02
 */

public interface Order_ItemDao{
	/**
	 * �������ˮ��
	 * @param bean ����ˮ��Bean
	 * @return ִ��SQL����Ӱ�������
	 * @throws Exception ִ��SQLʱ�׳������ݿ��쳣
	 */
	int addOrder_Item(Order_ItemBean bean) throws Exception;
	
	/**
	 * ��������IDֵɾ����ˮ��
	 * @param id ����
	 * @return ִ��SQL����Ӱ�������
	 * @throws Exception ִ��SQLʱ�׳������ݿ��쳣
	 */
	int deleteOrder_Item(int id) throws Exception;
	
	/**
	 * �����û���Ϣ
	 * @param bean �����޸ĺ����Ӧ����ˮ��Bean
	 * @return ִ��SQL����Ӱ�������
	 * @throws Exception ִ��SQL���ʱ�׳����쳣
	 */
	int updateOrder_Item(Order_ItemBean bean) throws Exception;
	
	/**
	 * ��ѯ����������е�ȫ������
	 * @return
	 */
	Vector<Vector<Object>> queryAllData()throws Exception;
	/**
	 * Ϊ�ս��ѯ����num ָ�������ŵĲ�Ʒ������
	 * @param num �������
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMenuAmount(String num) throws Exception;
}
