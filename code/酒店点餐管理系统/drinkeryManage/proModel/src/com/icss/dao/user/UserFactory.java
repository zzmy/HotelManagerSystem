package com.icss.dao.user;

/**
 * �û���Ϣ�������ݶ��󹤳���
 * @author ����Ԫ
 * @version 1.0 2014-12-31
 */
public class UserFactory {
	private UserFactory() {
	}
	/**
	 * ����û���Ϣ�������ݷ��ʶ���
	 * @return ���ݷ��ʶ���
	 */ 
	public static UserDaoImpl getInitialise() {
		return new UserDaoImpl();
	}
}
