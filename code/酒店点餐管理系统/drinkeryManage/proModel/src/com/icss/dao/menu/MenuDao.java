package com.icss.dao.menu;

import java.util.Vector;

import com.icss.bean.MenuBean;

/**
 * 菜品数据管理访问接口
 * @author 华莹 李振元 钟明媛
 * @version 1.0 2015-01-02
 */
public interface MenuDao {
	/**
	 * 添加新菜品
	 * @param Bean新菜品
	 * @return 执行sql语句后影响的行数
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	int addMenu(MenuBean bean) throws Exception;
	
	/**
	 * 删除菜品
	 * @param num 主键
	 * @return 执行sql语句后影响的内容
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	int deleteMenu(int num) throws Exception;
	
	/**
	 * 更新菜品
	 * @param bean
	 * @return 执行sql语句后影响的行数
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	int updateMenu(MenuBean bean) throws Exception;
	
	/**
	 * 查询菜品表中所有的数据
	 * @return
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
	/**
	 * 查询菜品表中最大编号
	 * @return
	 * @throws Exception
	 */
	String queryMaxNum() throws Exception;
	
	/**
	 * 新增：助记码与菜品编号查询
	 * @author 钟明媛
	 * @version 1.1 2015-01-07
	 */
	
	/**
	 * 根据助记码查询符合要求的菜品
	 * @return 符合要求的菜品信息
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	Vector<Vector<Object>> queryByCode(String code) throws Exception;
	
	/**
	 * 根据菜品编号查询符合要求的菜品
	 * @return 符合要求的菜品信息
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	Vector<Vector<Object>> queryByNum(int num) throws Exception;

}
