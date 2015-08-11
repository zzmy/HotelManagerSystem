package com.icss.bean;

/**
 * 用户信息表对应的Bean
 * @author 李振元
 * @version 1.0 2014-12-31
 */
public class UserBean {
	private int id;			//用户编号
	private String name;	//用户名
	private String sex;		//性别
	private String birthday;	//出生日期
	private String id_card;		//身份证号
	private String password;	//密码
	private String freeze;		//状态
	private int powerlevel;		//权限等级
	
	public UserBean() {
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getId_card() {
		return id_card;
	}
	public void setId_card(String id_card) {
		this.id_card = id_card;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFreeze() {
		return freeze;
	}
	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}
	public int getPowerlevel() {
		return powerlevel;
	}
	public void setPowerlevel(int powerlevel) {
		this.powerlevel = powerlevel;
	}
}
