/*
 * @(#)LoginAudit.java 2010-11-23
 * 
 * jeaw 版权所有2006~2015。
 */

package com.mrs.sysmgr.entity;

import java.util.Date;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 登录审计日志实体。<br>
 * 
 */
public class LoginAudit extends BaseStandardEntity implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	// 简单属性。
	private String loginId;
	private String userIpAddr;
	private String appsysName;
	private String deployCode;
	private String sessionID;
	private Date loginTime;
	private Date logoutTime;	

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getUserIpAddr() {
		return userIpAddr;
	}

	public void setUserIpAddr(String userIpAddr) {
		this.userIpAddr = userIpAddr;
	}

	public String getAppsysName() {
		return appsysName;
	}

	public void setAppsysName(String appsysName) {
		this.appsysName = appsysName;
	}

	public String getDeployCode() {
		return deployCode;
	}

	public void setDeployCode(String deployCode) {
		this.deployCode = deployCode;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}
	
}