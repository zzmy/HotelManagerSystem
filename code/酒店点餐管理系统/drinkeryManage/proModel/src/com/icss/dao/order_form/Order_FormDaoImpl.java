package com.icss.dao.order_form;

import java.util.Vector;

import com.icss.bean.Order_FormBean;
import com.icss.dao.BaseDao;

/**
 * 实现订单管理Dao
 * @author 赵玉璐 李振元
 * @version 1.0 2015.1.2
 */

class Order_FormDaoImpl extends BaseDao implements Order_FormDao{

	@Override
	public int addOrder_Form(Order_FormBean bean) throws Exception {
		// TODO Auto-generated method stub
		//创建SQL语句
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_order_form")
		.append(" (num,")
		.append(" desk_num,")
		.append(" money,")
		.append(" user_id)")
		.append(" values(?,?,?,?)")
		;
		
		//调用父类中的方法,给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(), bean.getNum(), bean.getDesk_num(),
				bean.getMoney(),bean.getUser_id());
	}

	@Override
	public int deleteOrder_Form(int num) throws Exception {
		// TODO Auto-generated method stub
		//创建SQL语句
		StringBuffer sql = new StringBuffer()
		.append(" delete from tb_order_form")
		.append(" where")
		.append(" 	num=?")
		;
		
		//调用父类中的方法,给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(), num);
	}

	@Override
	public int updateOrder_Form(Order_FormBean bean) throws Exception {
		// TODO Auto-generated method stub
		//创建SQL语句
		StringBuffer sql = new StringBuffer()
		.append(" update tb_order_form")
		.append(" set")
		.append(" 	desk_num=?,")
		.append(" 	money=?,")
		.append(" 	user_id=?")
		.append(" where")
		.append(" 	num=?")
		;
		
		//调用父类中的方法,给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(sql.toString(),
				bean.getDesk_num(),
				bean.getMoney(),
				bean.getUser_id(),
				bean.getNum());
	}
	@Override
	public String queryMaxNum() throws Exception {
		// TODO Auto-generated method stub
		String maxNum = null;
		try {
			String sql = new String("select max(num) from tb_order_form");
			Vector<Vector<Object>> table = executeQuery(sql);
			maxNum = table.get(0).get(1).toString();
		} catch (Exception e) {
			maxNum = null;
			e.printStackTrace();
		}
		return maxNum;
	}

	@Override
	public Vector<Vector<Object>> queryAllData() throws Exception {
		// TODO Auto-generated method stub
		//创建SQL语句
		StringBuffer sql = new StringBuffer()
		.append(" select ")
		.append("    num,")
		.append("  	 desk_num,")
		.append("  	 datetime,")
		.append("  	 money,")
		.append("  	 user_id")
		.append("  from ")
		.append("       tb_order_form")
		;
		//调用父类中的方法,给问号赋值时要求值与问号的顺序一一对应
		return executeQuery(sql.toString());
	}
	@Override
	public Vector<Vector<Object>> queryMinYear() throws Exception {
		StringBuffer sql = new StringBuffer()
		.append("select")
		.append(" 	min(datetime)")
		.append(" from tb_order_form")
		;
		return executeQuery(sql.toString());
	}
	@Override
	public Vector<Vector<Object>> queryDayDia(String time) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	num,")
		.append(" 	desk_num,")
		.append(" 	datetime,")
		.append(" 	money")
		.append(" from tb_order_form")
		.append(" where")
		.append(" 	to_char(datetime, 'yyyymmdd')=?")
		;
		return executeQuery(sql.toString(), time);
	}
	@Override
	public Vector<Vector<Object>> queryMonthDia(String time) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append("    to_char(datetime,'yyyymmdd'),")
		.append("    count(num),")
		.append("    sum(money),")
		.append("    avg(money),")
		.append("    max(money),")
		.append("    min(money) ")
		.append(" from tb_order_form")
		.append(" group by")
		.append(" 	to_char(datetime,'yyyymmdd')")
		;

		return executeQuery(sql.toString());
	}
	@Override
	public Vector<Vector<Object>> queryYearDia(String year) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	to_char(datetime, 'mmdd'),")
		.append(" 	sum(money)")
		.append(" from tb_order_form")
		.append(" where to_char(datetime, 'yyyy')=?")
		.append(" group by to_char(datetime, 'mmdd')")
		;
		
		return executeQuery(sql.toString(), year);
	}
	@Override
	public Vector<Vector<Object>> queryMonthSum(String year) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	to_char(datetime, 'mm'),")
		.append(" 	sum(money)")
		.append(" from tb_order_form")
		.append(" where to_char(datetime, 'yyyy')=?")
		.append(" group by to_char(datetime, 'mm')")
		;
		
		return executeQuery(sql.toString(), year);
	}
	@Override
	public Vector<Vector<Object>> queryDaySum(String year) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	to_char(datetime, 'dd'),")
		.append(" 	sum(money)")
		.append(" from tb_order_form")
		.append(" where to_char(datetime, 'yyyy')=?")
		.append(" group by to_char(datetime, 'dd')")
		;
		
		return executeQuery(sql.toString(), year);
	}
}

