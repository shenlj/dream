package com.mrs.sysmgr.entity;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Medal.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：奖牌
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */


public class Medal extends BaseStandardEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String medalname;
	private String medalimage;
	private String remark;
	private String remark1;
	private String remark2;



	public String getMedalname() {
		return this.medalname;
	}

	public void setMedalname(String medalname) {
		this.medalname = medalname;
	}

	public String getMedalimage() {
		return this.medalimage;
	}

	public void setMedalimage(String medalimage) {
		this.medalimage = medalimage;
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