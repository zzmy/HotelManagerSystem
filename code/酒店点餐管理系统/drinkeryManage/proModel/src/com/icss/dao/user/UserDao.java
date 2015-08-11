package com.icss.dao.user;

import java.util.Vector;

import com.icss.bean.UserBean;

/**
 * �û���Ϣ�������ݷ��ʽӿ�
 * @author ����Ԫ
 * @version 1.0 2014-12-31
 */
public interface UserDao {
	/**
	 * ������û�
	 * @param bean	���û�Bean
	 * @return		ִ��SQL����Ӱ�������
	 * @throws Exception	ִ��SQL���ʱ�׳����쳣
	 */
	int addUser(UserBean bean) throws Exception;
	/**
	 * ��������ֵɾ���û�
	 * @param id	����
	 * @return		ִ��SQL����Ӱ�������
	 * @throws Exception	ִ��SQL���ʱ�׳����쳣
	 */
	int deleteUser(UserBean bean) throws Exception;
	/**
	 * �����û���Ϣ
	 * @param pwdOld	������
	 * @param pwdNew1	�������һ��
	 * @param pwdNew2	������ڶ���
	 * @return			ִ��SQL����Ӱ�������
	 * @throws Exception	ִ��SQL���ʱ�׳����쳣
	 */
	int updateUser(UserBean bean) throws Exception;
	/**
	 * ��ѯ�û���Ϣ���е�ȫ������
	 * @return 
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
	/**
	 * �����û��������ѯ�û���Ϣ
	 * @return ָ���û���Ϣ
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryUser(String name, String pwd) throws Exception;
}
