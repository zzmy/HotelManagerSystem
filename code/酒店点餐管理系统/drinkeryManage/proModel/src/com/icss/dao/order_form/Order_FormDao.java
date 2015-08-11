package com.icss.dao.order_form;

import java.util.Vector;

import com.icss.bean.Order_FormBean;

/**
 * 订单单号管理数据访问接口
 * @author 李振元 赵玉璐
 * @version 1.1 2015.1.11
 *
 */

public interface Order_FormDao {
	/**
	 * 添加新订单
	 * @param bean 新订单Bean
	 * @return 执行SQL语句后影响的行数
	 * @throws Exception 执行SQL时抛出的数据库异常
	 */
	int addOrder_Form(Order_FormBean bean) throws Exception;
	
	/**
	 * 根据主键NUM值删除台号
	 * @param num 主键
	 * @return 执行SQL语句后影响的行数
	 * @throws Exception 执行SQL时抛出的数据库异常
	 */
	int deleteOrder_Form(int num) throws Exception;
	
	/**
	 * 更改订单
	 * @param num 主键
	 * @param desk_num 台号
	 * @param money 结算日期
	 * @param user_id 操作员编号
	 * @return 执行SQL语句后影响的行数
	 * @throws Exception 执行SQL时抛出的数据库异常
	 */
	int updateOrder_Form(Order_FormBean bean) throws Exception;
	/**
	 * 查询最大订单号
	 * @return
	 * @throws Exception
	 */
	public String queryMaxNum() throws Exception;
	/**
	 * 查询订单中的全部数据
	 * @return
	 */
	Vector<Vector<Object>> queryAllData()throws Exception;
	
	/**
	 * 查询最早订单的时间
	 * @return 最早订单的时间
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMinYear() throws Exception;
	/**
	 * 为日结查询订单编号、台号、开台时间、金额
	 * @param time 日期精确到天'yymmdd' 
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryDayDia(String time) throws Exception;
	/**
	 * 为月结查询开台总数、销费总额、平均销费额、最大销费额、最小销费额
	 * @param time 日期(yyyymmdd)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMonthDia(String time) throws Exception;
	/**
	 * 为年结查询月、日对应的销费总额
	 * @param year 年(yyyy)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryYearDia(String year) throws Exception;
	/**
	 * 为年结查询月度总计
	 * @param year(yyyy)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryMonthSum(String year) throws Exception;
	/**
	 * 为年结查询日度总计
	 * @param year(yyyy)
	 * @return
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryDaySum(String year) throws Exception;
}
