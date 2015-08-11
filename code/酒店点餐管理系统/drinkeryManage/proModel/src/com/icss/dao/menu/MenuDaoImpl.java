package com.icss.dao.menu;

import java.util.Vector;

import com.icss.bean.MenuBean;
import com.icss.dao.BaseDao;

/**
 * 实现菜品表Dao
 * @author 华莹 李振元 钟明媛
 * @version 1.1 2015-01-09
 */
class MenuDaoImpl extends BaseDao implements MenuDao{

	public int addMenu(MenuBean bean) throws Exception {
		//创建SQL语句
		//SQL语句的规范：关键字占一行，每列占一行，连接时前后要有空格
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_menu")
		.append(" (num,")
		.append(" sort_id,")
		.append(" name,")
		.append(" code,")
		.append(" unit,")
		.append(" unit_price)")
		.append(" values(?,?,?,?,?,?)")
		;
		
		//调用父类中的方法，给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(), 
				bean.getNum(),
				bean.getSort_id(),
				bean.getName(),
				bean.getCode(),
				bean.getUnit(),
				bean.getUnit_price()
				);
	}

	public int deleteMenu(int num) throws Exception {
		//创建SQL语句
		//SQL语句的规范：关键字占一行，每列占一行，连接时前后要有空格
		StringBuffer sql = new StringBuffer()
		.append(" update tb_menu")
		.append(" set")
		.append(" 	state='删除'")
		.append(" where num=?")
		;

		//调用父类中的方法，给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(), num);
	}
	
	public int updateMenu(MenuBean bean) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" update tb_menu")
		.append(" set")
		.append(" 	sort_id=?,")
		.append(" 	name=?,")
		.append(" 	code=?,")
		.append(" 	unit=?,")
		.append(" 	unit_price=?,")
		.append(" 	state=?")
		.append(" where num=?")
		;
		return executeUpdate(
				sql.toString(), 
				bean.getSort_id(), 
				bean.getName(), 
				bean.getCode(), 
				bean.getUnit(), 
				bean.getUnit_price(), 
				bean.getState(), 
				bean.getNum());
	}

	public Vector<Vector<Object>> queryAllData() throws Exception {
		//创建SQL语句
		//SQL语句的规范：关键字占一行，每列占一行，连接时前后要有空格
		StringBuffer sql = new StringBuffer()
		.append(" SELECT")
		.append("    num,")
		.append("  	 sort_id,")
		.append("    name,")
		.append("    code,")
		.append("    unit,")
		.append("    unit_price,")
		.append("    state")
		.append(" FROM")
		.append("    tb_menu")
		;
				 
		return executeQuery(sql.toString());
	}
	@Override
	public String queryMaxNum() throws Exception {
		// TODO Auto-generated method stub
		String maxNum = null;
		try {
			String sql = new String("select max(num) from tb_menu");
			Vector<Vector<Object>> table = executeQuery(sql);
			maxNum = table.get(0).get(1).toString();
		} catch (Exception e) {
			maxNum = null;
			e.printStackTrace();
		}
		return maxNum;
	}

	@Override
	public Vector<Vector<Object>> queryByCode(String code) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer()
		.append(" SELECT")
		.append("    num,")
		.append("  	 sort_id,")
		.append("    name,")
		.append("    code,")
		.append("    unit,")
		.append("    unit_price,")
		.append("    state")
		.append(" FROM")
		.append("    tb_menu")
		.append(" WHERE")
		.append("    state = '销售'")
		.append(" AND")
		.append("    code like ?")
		;

		return executeQuery(sql.toString(), code+"%");

	}

	@Override
	public Vector<Vector<Object>> queryByNum(int num) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer()
		.append(" SELECT")
		.append("    num,")
		.append("  	 sort_id,")
		.append("    name,")
		.append("    code,")
		.append("    unit,")
		.append("    unit_price,")
		.append("    state")
		.append(" FROM")
		.append("    tb_menu")
		.append(" WHERE")
		.append("    state = '销售'")
		.append(" AND")
		.append("    num = ?")
		;
				 
		return executeQuery(sql.toString(), num);
	}
}
