package com.icss.dao.order_item;

import java.util.Vector;

import com.icss.bean.Order_ItemBean;

/**
 * 订单详情表数据访问接口
 * @author 钟明媛
 * @version 1.0 2015-01-02
 */

public interface Order_ItemDao{
	/**
	 * 添加新流水号
	 * @param bean 新流水号Bean
	 * @return 执行SQL语句后影响的行数
	 * @throws Exception 执行SQL时抛出的数据库异常
	 */
	int addOrder_Item(Order_ItemBean bean) throws Exception;
	
	/**
	 * 根据主键ID值删除流水号
	 * @param id 主键
	 * @return 执行SQL语句后影响的行数
	 * @throws Exception 执行SQL时抛出的数据库异常
	 */
	int deleteOrder_Item(int id) throws Exception;
	
	/**
	 * 更改用户信息
	 * @param bean 进行修改后相对应的流水号Bean
	 * @return 执行SQL语句后影响的行数
	 * @throws Exception 执行SQL语句时抛出的异常
	 */
	int updateOrder_Item(Order_ItemBean bean) throws Exception;
	
	/**
	 * 查询订单详情表中的全部数据
	 * @return
	 */
	Vector<Vector<Object>> queryAllData()throws Exception;
	/**
	 * 为日结查询订单num 指定订单号的菜品、数量
	 * @param num 订单编号
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMenuAmount(String num) throws Exception;
}
