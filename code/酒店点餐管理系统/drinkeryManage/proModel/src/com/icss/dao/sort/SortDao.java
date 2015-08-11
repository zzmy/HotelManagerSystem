package com.icss.dao.sort;

import java.util.Vector;

import com.icss.bean.SortBean;

/**
 * 菜系数据管理访问接口
 * @author 华莹
 * @version 1.0 2015-01-02
 */
public interface SortDao {
	/**
	 * 添加新菜系
	 * @param 新菜系 
	 * @return 执行sql语句后影响的行数
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	int addSort(SortBean bean) throws Exception;
	
	/**
	 * 根据主键ID号删除菜系
	 * @param id 主键
	 * @return 执行sql语句后影响的内容
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	int deleteSort(int id) throws Exception;
	
	/**
	 * 查询菜系表的所有数据
	 * @return 
	 * @throws Exception 执行sql语句时抛出的数据库异常
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
}
