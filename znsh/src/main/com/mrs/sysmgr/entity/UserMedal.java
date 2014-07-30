package com.mrs.sysmgr.entity;

import java.util.Date;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名：UserMedal.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：用戶獎牌
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */

public class UserMedal extends BaseStandardEntity {

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	private String medalid;
	private Date medaltime;
	private String remark1;
	private String remark2;


	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMedalid() {
		return this.medalid;
	}

	public void setMedalid(String medalid) {
		this.medalid = medalid;
	}

	public Date getMedaltime() {
		return this.medaltime;
	}

	public void setMedaltime(Date medaltime) {
		this.medaltime = medaltime;
	}

	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

}