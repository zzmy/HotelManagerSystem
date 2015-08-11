package com.icss.dao.desk;

import java.util.Vector;

import com.icss.bean.DeskBean;
/**
 * ��̨̨�Ź������ݷ��ʽӿ�
 * @author ��Ө 
 * @version 1.0 2014-12-31
 */
public interface DeskDao {
	/**
	 * �����̨��
	 * @param bean 	��̨��
	 * @return		ִ��SQL����Ӱ�������
	 * @throws Exception	ִ��SQLʱ�׳������ݿ��쳣
	 */
	int addDesk(DeskBean bean) throws Exception;
	/**
	 * ��������ֵɾ��̨��
	 * @param id	����
	 * @return		ִ��SQL����Ӱ�������
	 * @throws Exception	ִ��SQLʱ�׳����쳣
	 */
	int deleteDesk(int id) throws Exception;
	/**
	 * ��ѯ��̨���е�ȫ������
	 * @return 
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
}
