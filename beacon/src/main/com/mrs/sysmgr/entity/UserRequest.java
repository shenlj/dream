package com.mrs.sysmgr.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名：UserRequest.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：用户添加好友请求信息
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@XmlRootElement(name = "UserRequest")
public class UserRequest extends BaseStandardEntity {
	private static final long serialVersionUID = 1L;
	private String userid;
	private String friendid;
	private String requestZt;
	private String requestMsg;
	private String replay;
	private String remark1;
	private String remark2;

	public String getUserid() {

		return userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
	}

	public String getFriendid() {

		return friendid;
	}

	public void setFriendid(String friendid) {

		this.friendid = friendid;
	}

	public String getRequestZt() {

		return requestZt;
	}

	public void setRequestZt(String requestZt) {

		this.requestZt = requestZt;
	}

	public String getRequestMsg() {

		return requestMsg;
	}

	public void setRequestMsg(String requestMsg) {

		this.requestMsg = requestMsg;
	}

	public String getReplay() {

		return replay;
	}

	public void setReplay(String replay) {

		this.replay = replay;
	}

	public String getRemark1() {

		return remark1;
	}

	public void setRemark1(String remark1) {

		this.remark1 = remark1;
	}

	public String getRemark2() {

		return remark2;
	}

	public void setRemark2(String remark2) {

		this.remark2 = remark2;
	}

	

}