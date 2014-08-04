/*
 * @(#)LoginAudit.java 2010-11-23
 * jeaw 版权所有2006~2015。
 */

package com.mrs.sysmgr.entity;

import java.util.Date;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 登录审计日志实体。<br>
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

		return this.loginId;
	}

	public void setLoginId(final String loginId) {

		this.loginId = loginId;
	}

	public String getUserIpAddr() {

		return this.userIpAddr;
	}

	public void setUserIpAddr(final String userIpAddr) {

		this.userIpAddr = userIpAddr;
	}

	public String getAppsysName() {

		return this.appsysName;
	}

	public void setAppsysName(final String appsysName) {

		this.appsysName = appsysName;
	}

	public String getDeployCode() {

		return this.deployCode;
	}

	public void setDeployCode(final String deployCode) {

		this.deployCode = deployCode;
	}

	public Date getLoginTime() {

		return this.loginTime;
	}

	public void setLoginTime(final Date loginTime) {

		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {

		return this.logoutTime;
	}

	public void setLogoutTime(final Date logoutTime) {

		this.logoutTime = logoutTime;
	}

	public String getSessionID() {

		return this.sessionID;
	}

	public void setSessionID(final String sessionID) {

		this.sessionID = sessionID;
	}

}
