package com.mrs.sysmgr.entity;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： FootType.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：食物对照表
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */

public class FootType extends BaseStandardEntity {

	private static final long serialVersionUID = 1L;
	private String footname;
	private byte[] footpic;
	private Double cal;
	private String remark1;
	private String remark2;




	public String getFootname() {
		return this.footname;
	}

	public void setFootname(String footname) {
		this.footname = footname;
	}

	public byte[] getFootpic() {
		return this.footpic;
	}

	public void setFootpic(byte[] footpic) {
		this.footpic = footpic;
	}

	public Double getCal() {
		return this.cal;
	}

	public void setCal(Double cal) {
		this.cal = cal;
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