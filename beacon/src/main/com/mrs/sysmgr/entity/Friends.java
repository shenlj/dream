package com.mrs.sysmgr.entity;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Friends.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：朋友
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */

public class Friends extends BaseStandardEntity {

	private String userid;
	private String frienduserid;
	private String remark;
	private String remark1;
	private String remark2;



	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFrienduserid() {
		return this.frienduserid;
	}

	public void setFrienduserid(String frienduserid) {
		this.frienduserid = frienduserid;
	}



	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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