package com.icss.dao.sort;

/**
 * ��ϵ�����ݹ���Ĺ�����
 * @author ��Ө
 * @version 1.0 2015-01-02
 */
public class SortFactory {
	//����Ĺ��췽��Ҫ˽�л��������������û���������
	private SortFactory()
	{}
	//��Ӿ�̬������ͨ���÷������daoΨһ�Ķ���
	/**
	 * ��ò�ϵ�������ݷ��ʶ���
	 * @return ���ݷ��ʶ���
	 */ 
	public static SortDao getInitialise()
	{
		return new SortDaoImpl();
	}
}
