package com.icss.dao.desk;

/**
 * ��̨�������ݶ��󹤳���
 * @author ��Ө
 * @version 1.0 2014-12-31
 */
public class DeskFactory {
	private DeskFactory() {
	}
	/**
	 * ��ò�̨�������ݷ��ʶ���
	 * @return ���ݷ��ʶ���
	 */ 
	public static DeskDaoImpl getInitialise() {
		return new DeskDaoImpl();
	}
}
