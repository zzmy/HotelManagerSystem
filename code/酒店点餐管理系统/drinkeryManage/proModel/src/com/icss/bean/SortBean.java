package com.icss.bean;
/**
 * 菜系表对应的Bean
 * @author 华莹
 * @version 1.0 2014-12-31
 */
public class SortBean {
	int id;//菜系编号
	String name;//菜系名
	
	public SortBean(){}

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
}
