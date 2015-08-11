package com.icss.dao.order_item;

import java.util.Vector;

import com.icss.bean.Order_ItemBean;
import com.icss.dao.BaseDao;

/**
 * 实现订单详情表Dao
 * @author 钟明媛
 * @version 1.0 2015-01-02
 */
public class Order_ItemDaoImpl extends BaseDao implements Order_ItemDao {

	@Override
	public int addOrder_Item(Order_ItemBean bean) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_order_item")
		.append(" 	(order_form_num,")
		.append(" 	menu_num,")
		.append(" 	amount,")
		.append(" 	total)")
		.append(" values(?, ?, ?, ?)")
		;
		//调用父类中的方法,给问号赋值时要求值与问号的顺序一一对应
		return executeUpdate(
				sql.toString(), 
				bean.getOrder_form_num(),
				bean.getMenu_num(),
				bean.getAmount(),
				bean.getTotal());
	}

	@Override
	public int deleteOrder_Item(int id) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer()
		.append(" delete from tb_order_item")
		.append(" where")
		.append(" 	id=?")
		;
		return executeUpdate(sql.toString(), id);
	}

	@Override
	public int updateOrder_Item(Order_ItemBean bean) throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer()
		.append(" update tb_order_item")
		.append(" set")
		.append(" 	order_form_num=?,")
		.append(" 	menu_num=?,")
		.append(" 	amount=?,")
		.append(" 	total=?")
		.append(" where")
		.append(" 	id=?")
		;
		return executeUpdate(
				sql.toString(),
				bean.getOrder_form_num(),
				bean.getMenu_num(),
				bean.getAmount(),
				bean.getTotal(),
				bean.getId());
	}
	
	@Override
	public Vector<Vector<Object>> queryAllData() throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	id,")
		.append(" 	order_form_num,")
		.append(" 	menu_num,")
		.append(" 	amount,")
		.append(" 	total")
		.append(" from tb_order_item");
		return executeQuery(sql.toString());
	}	
	@Override
	public Vector<Vector<Object>> queryMenuAmount(String num) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	name,")
		.append(" 	amount")
		.append(" from tb_order_item, tb_menu")
		.append(" 	where")
		.append(" 	menu_num=num")
		.append(" 	and order_form_num=?")
		;
		return executeQuery(sql.toString(), num);
	}
}
