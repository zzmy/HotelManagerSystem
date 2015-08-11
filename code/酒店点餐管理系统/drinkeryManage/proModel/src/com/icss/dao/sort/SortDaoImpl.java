package com.icss.dao.sort;

import java.util.Vector;

import com.icss.bean.SortBean;
import com.icss.dao.BaseDao;
/**
 * ʵ�ֲ�ϵ��Dao
 * @author ��Ө
 * @version 1.0 2015-01-02
 */
class SortDaoImpl extends BaseDao implements SortDao{

	public int addSort(SortBean bean) throws Exception {
		//����SQL���
		//SQL���Ĺ淶���ؼ���ռһ�У�ÿ��ռһ�У�����ʱǰ��Ҫ�пո�
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_sort")
		.append(" 	(id,name)")
		.append(" values(?,?)")
		;
		
		//���ø����еķ��������ʺŸ�ֵʱҪ��ֵ���ʺŵ�˳��һһ��Ӧ
		return executeUpdate(sql.toString(), bean.getId(), bean.getName());
	}

	public int deleteSort(int id) throws Exception {
		//����SQL���
		//SQL���Ĺ淶���ؼ���ռһ�У�ÿ��ռһ�У�����ʱǰ��Ҫ�пո�
		StringBuffer sql = new StringBuffer()
		.append(" delete from tb_sort")
		.append(" where")
		.append(" id = ?")
		;
		
		//���ø����еķ��������ʺŸ�ֵʱҪ��ֵ���ʺŵ�˳��һһ��Ӧ
		return executeUpdate(sql.toString(), id);
	}

	public Vector<Vector<Object>> queryAllData() throws Exception {
		//����SQL���
		//SQL���Ĺ淶���ؼ���ռһ�У�ÿ��ռһ�У�����ʱǰ��Ҫ�пո�
		StringBuffer sql = new StringBuffer()
		.append(" SELECT ")
		.append("    id,")
		.append("  	 name")
		.append(" FROM ")
		.append("    tb_sort ")
		;
				 
		return executeQuery(sql.toString());
	}

}
