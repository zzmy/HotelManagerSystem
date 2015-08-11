package com.icss.dao.user;

import java.util.Vector;

/**
 * 实现用户信息管理的Dao
 * @author 李振元
 * @version 1.0 2014-12-31
 */
import com.icss.bean.UserBean;
import com.icss.dao.BaseDao;
public class UserDaoImpl extends BaseDao implements UserDao{
	@Override
	public int addUser(UserBean bean) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" insert into tb_user")
		.append(" 	(name,")
		.append(" 	sex,")
		.append(" 	birthday,")
		.append(" 	id_card,")
		.append(" 	password,")
		.append(" 	powerlevel)")
		.append(" values")
		.append("	(?, ?, to_date(?,'yyyy-mm-dd'), ?, ?, ?)")
		;
		return executeUpdate(
				sql.toString(), 
				bean.getName(), 
				bean.getSex(), 
				bean.getBirthday(), 
				bean.getId_card(), 
				bean.getPassword(),
				bean.getPowerlevel());
	}
	@Override
	public int deleteUser(UserBean bean) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" update tb_user")
		.append(" set")
		.append(" 	freeze='禁用'")
		.append(" where")
		.append("	id=?")
		;
		return executeUpdate(sql.toString(), bean.getId());
	}
	@Override
	public int updateUser(UserBean bean) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" update tb_user")
		.append(" set")
		.append(" 	password=?")
		.append(" where")
		.append(" 	id=?")
		;
		return executeUpdate(sql.toString(), bean.getPassword(), bean.getId());
	}
	@Override
	public Vector<Vector<Object>> queryAllData() throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	id,")
		.append(" 	name,")
		.append(" 	sex,")
		.append(" 	birthday,")
		.append(" 	id_card,")
		.append(" 	password,")
		.append(" 	freeze")
		.append(" from tb_user")
		;
		return executeQuery(sql.toString());
	}
	@Override
	public Vector<Vector<Object>> queryUser(String name, String pwd) throws Exception {
		StringBuffer sql = new StringBuffer()
		.append(" select")
		.append(" 	id,")
		.append(" 	name,")
		.append(" 	sex,")
		.append(" 	birthday,")
		.append(" 	id_card,")
		.append(" 	password,")
		.append(" 	powerlevel,")
		.append(" 	freeze")
		.append(" from tb_user")
		.append(" where")
		.append(" 	name=?")
		.append(" 	and password=?")
		;
		return executeQuery(sql.toString(), name, pwd);
	}
}