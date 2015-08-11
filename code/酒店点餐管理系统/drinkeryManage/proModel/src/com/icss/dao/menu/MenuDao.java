package com.icss.dao.menu;

import java.util.Vector;

import com.icss.bean.MenuBean;

/**
 * ��Ʒ���ݹ�����ʽӿ�
 * @author ��Ө ����Ԫ ������
 * @version 1.0 2015-01-02
 */
public interface MenuDao {
	/**
	 * ����²�Ʒ
	 * @param Bean�²�Ʒ
	 * @return ִ��sql����Ӱ�������
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	int addMenu(MenuBean bean) throws Exception;
	
	/**
	 * ɾ����Ʒ
	 * @param num ����
	 * @return ִ��sql����Ӱ�������
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	int deleteMenu(int num) throws Exception;
	
	/**
	 * ���²�Ʒ
	 * @param bean
	 * @return ִ��sql����Ӱ�������
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	int updateMenu(MenuBean bean) throws Exception;
	
	/**
	 * ��ѯ��Ʒ�������е�����
	 * @return
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
	/**
	 * ��ѯ��Ʒ���������
	 * @return
	 * @throws Exception
	 */
	String queryMaxNum() throws Exception;
	
	/**
	 * ���������������Ʒ��Ų�ѯ
	 * @author ������
	 * @version 1.1 2015-01-07
	 */
	
	/**
	 * �����������ѯ����Ҫ��Ĳ�Ʒ
	 * @return ����Ҫ��Ĳ�Ʒ��Ϣ
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	Vector<Vector<Object>> queryByCode(String code) throws Exception;
	
	/**
	 * ���ݲ�Ʒ��Ų�ѯ����Ҫ��Ĳ�Ʒ
	 * @return ����Ҫ��Ĳ�Ʒ��Ϣ
	 * @throws Exception ִ��sql���ʱ�׳������ݿ��쳣
	 */
	Vector<Vector<Object>> queryByNum(int num) throws Exception;

}
