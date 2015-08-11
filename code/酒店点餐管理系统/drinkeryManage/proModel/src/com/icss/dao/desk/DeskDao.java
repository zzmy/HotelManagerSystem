package com.icss.dao.desk;

import java.util.Vector;

import com.icss.bean.DeskBean;
/**
 * 餐台台号管理数据访问接口
 * @author 华莹 
 * @version 1.0 2014-12-31
 */
public interface DeskDao {
	/**
	 * 添加新台号
	 * @param bean 	新台号
	 * @return		执行SQL语句后影响的行数
	 * @throws Exception	执行SQL时抛出的数据库异常
	 */
	int addDesk(DeskBean bean) throws Exception;
	/**
	 * 根据主键值删除台号
	 * @param id	主键
	 * @return		执行SQL语句后影响的行数
	 * @throws Exception	执行SQL时抛出的异常
	 */
	int deleteDesk(int id) throws Exception;
	/**
	 * 查询餐台表中的全部数据
	 * @return 
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
}
