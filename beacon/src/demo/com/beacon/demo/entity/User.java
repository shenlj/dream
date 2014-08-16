package com.beacon.demo.entity;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名：User.java
 * 作者：
 * 日期：2014-8-10
 * 功能说明：
 * 
 * ===============================================================================
 * 修改记录：
 * 修改作者    日期      修改内容
 * 
 * 
 * 
 * 
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
public class User extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID = 3377108008232646552L;

    /** 用户状态：正常。 */
	public static final String STATUS_NORMAL = "1";

    /** 用户状态：锁定。 */
	public static final String STATUS_LOCKED = "0";

    /** 用户性别：男。 */
	public static final String SEX_MAN = "1";

    /** 用户性别：女。 */
	public static final String SEX_WOMAN = "0";
	/** 说明：登陆号 */
	private String loginid;

	/** 说明：密码 */
	private String passwd;

	/** 说明：用户名 */
	private String username;

    /** 说明：状态:1表示有效,否则为0 */
	private String status;

    /** 说明：状态:1表示有效,否则为0 */
	private String sex;


	public String getLoginid() {
	
		return loginid;
	}
	
	
	public void setLoginid(String loginid) {
	
		this.loginid = loginid;
	}
	
	
	public String getPasswd() {
	
		return passwd;
	}
	
	
	public void setPasswd(String passwd) {
	
		this.passwd = passwd;
	}
	
	
	public String getUsername() {
	
		return username;
	}
	
	
	public void setUsername(String username) {
	
		this.username = username;
	}
	
	
	public String getStatus() {
	
		return status;
	}
	
	
	public void setStatus(String status) {
	
		this.status = status;
	}
	
	
	public String getSex() {
	
		return sex;
	}
	
	
	public void setSex(String sex) {
	
		this.sex = sex;
	}
}
