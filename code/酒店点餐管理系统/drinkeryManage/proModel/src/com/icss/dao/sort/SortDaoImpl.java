package com.icss.dao.sort;

import java.util.Vector;

import com.icss.bean.SortBean;
import com.icss.dao.BaseDao;
/**
 * 实现菜系表Dao
 * @author 华莹
 * @version 1.0 2015-01-02
 */
class SortDaoImpl extends BaseDao implements SortDao{

	public int addSort(SortBean bean) throws Exception {
		//创建SQL语句
		//SQL语句的规范：关键字占一行，每列占一行，连接时前后要有空格
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_sort")
		.append(" 	(id,name)")
		.append(" values(?,?)")
		;
		
		//调用父类中的方法，给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(), bean.getId(), bean.getName());
	}

	public int deleteSort(int id) throws Exception {
		//创建SQL语句
		//SQL语句的规范：关键字占一行，每列占一行，连接时前后要有空格
		StringBuffer sql = new StringBuffer()
		.append(" delete from tb_sort")
		.append(" where")
		.append(" id = ?")
		;
		
		//调用父类中的方法，给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(), id);
	}

	public Vector<Vector<Object>> queryAllData() throws Exception {
		//创建SQL语句
		//SQL语句的规范：关键字占一行，每列占一行，连接时前后要有空格
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
