package com.icss.dao.sort;

import java.util.Vector;

import com.icss.bean.SortBean;

/**
 * ��ϵ���ݹ�����ʽӿ�
 * @author ��Ө
 * @version 1.0 2015-01-02
 */
public interface SortDao {
	/**
	 * ����²�ϵ
	 * @param �²�ϵ 
	 * @return ִ��sql����Ӱ�������
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	int addSort(SortBean bean) throws Exception;
	
	/**
	 * ��������ID��ɾ����ϵ
	 * @param id ����
	 * @return ִ��sql����Ӱ�������
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	int deleteSort(int id) throws Exception;
	
	/**
	 * ��ѯ��ϵ�����������
	 * @return 
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
}
