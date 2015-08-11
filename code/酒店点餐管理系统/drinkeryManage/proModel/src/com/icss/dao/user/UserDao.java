package com.icss.dao.user;

import java.util.Vector;

import com.icss.bean.UserBean;

/**
 * 用户信息管理数据访问接口
 * @author 李振元
 * @version 1.0 2014-12-31
 */
public interface UserDao {
	/**
	 * 添加新用户
	 * @param bean	新用户Bean
	 * @return		执行SQL语句后影响的行数
	 * @throws Exception	执行SQL语句时抛出的异常
	 */
	int addUser(UserBean bean) throws Exception;
	/**
	 * 根据主键值删除用户
	 * @param id	主键
	 * @return		执行SQL语句后影响的行数
	 * @throws Exception	执行SQL语句时抛出的异常
	 */
	int deleteUser(UserBean bean) throws Exception;
	/**
	 * 更改用户信息
	 * @param pwdOld	旧密码
	 * @param pwdNew1	新密码第一遍
	 * @param pwdNew2	新密码第二遍
	 * @return			执行SQL语句后影响的行数
	 * @throws Exception	执行SQL语句时抛出的异常
	 */
	int updateUser(UserBean bean) throws Exception;
	/**
	 * 查询用户信息表中的全部数据
	 * @return 
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryAllData() throws Exception;
	/**
	 * 根据用户名密码查询用户信息
	 * @return 指定用户信息
	 * @throws Exception
	 */
	Vector<Vector<Object>> queryUser(String name, String pwd) throws Exception;
}
