package com.icss.dao.desk;

import java.util.Vector;
import com.icss.bean.DeskBean;
import com.icss.dao.BaseDao;

/**
 * 实现餐台管理Dao
 * @author 华莹
 * @version 1.0 2014-12-31
 */
public class DeskDaoImpl extends BaseDao implements DeskDao{
	@Override
	public int addDesk(DeskBean bean) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_desk")
		.append("  	(num, seating)")
		.append(" values (?, ?)")
		;
		
		return executeUpdate(sql.toString(), bean.getNum(), bean.getSeating());
	}
	@Override
	public int deleteDesk(int id) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" delete from tb_desk")
		.append(" where")
		.append("  	num=?")
		;
		return executeUpdate(sql.toString(), id);
	}
	@Override
	public Vector<Vector<Object>> queryAllData() throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	num,")
		.append("  	seating")
		.append(" from tb_desk")
		;
		return executeQuery(sql.toString());
	}
}
