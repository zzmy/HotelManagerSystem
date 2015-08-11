package com.icss.dao.order_form;

import java.util.Vector;

import com.icss.bean.Order_FormBean;

/**
 * �������Ź������ݷ��ʽӿ�
 * @author ����Ԫ �����
 * @version 1.1 2015.1.11
 *
 */

public interface Order_FormDao {
	/**
	 * ����¶���
	 * @param bean �¶���Bean
	 * @return ִ��SQL����Ӱ�������
	 * @throws Exception ִ��SQLʱ�׳������ݿ��쳣
	 */
	int addOrder_Form(Order_FormBean bean) throws Exception;
	
	/**
	 * ��������NUMֵɾ��̨��
	 * @param num ����
	 * @return ִ��SQL����Ӱ�������
	 * @throws Exception ִ��SQLʱ�׳������ݿ��쳣
	 */
	int deleteOrder_Form(int num) throws Exception;
	
	/**
	 * ���Ķ���
	 * @param num ����
	 * @param desk_num ̨��
	 * @param money ��������
	 * @param user_id ����Ա���
	 * @return ִ��SQL����Ӱ�������
	 * @throws Exception ִ��SQLʱ�׳������ݿ��쳣
	 */
	int updateOrder_Form(Order_FormBean bean) throws Exception;
	/**
	 * ��ѯ��󶩵���
	 * @return
	 * @throws Exception
	 */
	public String queryMaxNum() throws Exception;
	/**
	 * ��ѯ�����е�ȫ������
	 * @return
	 */
	Vector<Vector<Object>> queryAllData()throws Exception;
	
	/**
	 * ��ѯ���綩����ʱ��
	 * @return ���綩����ʱ��
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMinYear() throws Exception;
	/**
	 * Ϊ�ս��ѯ������š�̨�š���̨ʱ�䡢���
	 * @param time ���ھ�ȷ����'yymmdd' 
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryDayDia(String time) throws Exception;
	/**
	 * Ϊ�½��ѯ��̨�����������ܶƽ�����Ѷ������Ѷ��С���Ѷ�
	 * @param time ����(yyyymmdd)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMonthDia(String time) throws Exception;
	/**
	 * Ϊ����ѯ�¡��ն�Ӧ�������ܶ�
	 * @param year ��(yyyy)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryYearDia(String year) throws Exception;
	/**
	 * Ϊ����ѯ�¶��ܼ�
	 * @param year(yyyy)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMonthSum(String year) throws Exception;
	/**
	 * Ϊ����ѯ�ն��ܼ�
	 * @param year(yyyy)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryDaySum(String year) throws Exception;
}
