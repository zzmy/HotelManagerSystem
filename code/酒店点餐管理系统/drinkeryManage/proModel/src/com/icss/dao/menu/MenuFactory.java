package com.icss.dao.menu;

/**
 * ��Ʒ�����ݹ���Ĺ�����
 * @author ��Ө
 * @version 1.0 2015-01-02
 */
public class MenuFactory {
	//����Ĺ��췽��Ҫ˽�л��������������û���������
	private MenuFactory()
	{}
	//��Ӿ�̬������ͨ���÷������daoΨһ�Ķ���
	/**
	 * ��ò�Ʒ�������ݷ��ʶ���
	 * @return ���ݷ��ʶ���
	 */ 
	public static MenuDao getInitialise()
	{
		return new MenuDaoImpl();
	}

}
