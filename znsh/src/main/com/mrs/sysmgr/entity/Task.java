package com.mrs.sysmgr.entity;

import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.mrs.sysmgr.common.SysmgrConstants;
import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.util.DateUtil;

/**
 * 文件名：Task.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：競賽任務
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@XmlRootElement(name = "Task")
@XmlAccessorType( XmlAccessType.FIELD )
public class Task extends BaseStandardEntity {
	private static final long serialVersionUID = 1L;
	private String taskname;
	private Double endmark = 0d;
	private String partyuser;
	private String taskstate;
	private Date taskstartTime;
	private Date taskendTime;
	private String startTimeStr;
	private String endTimeStr;
	private String remark1;
	private String remark2;
	private Integer partyUserNumber = 0;
	private String createDate;
	private String model;
	private String userpic;
	private String username;

	public String getTaskname() {

		return this.taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public Double getEndmark() {
		return this.endmark;
	}

	public void setEndmark(Double endmark) {
		this.endmark = endmark;
	}

	public String getPartyuser() {
		return this.partyuser;
	}

	public void setPartyuser(String partyuser) {
		this.partyuser = partyuser;
	}

	public String getTaskstate() {
		return this.taskstate;
	}

	public void setTaskstate(String taskstate) {
		this.taskstate = taskstate;
	}

	public Date getTaskstartTime() {
		return this.taskstartTime;
	}

	public void setTaskstartTime(Date taskstartTime) throws ParseException {
		this.taskstartTime = taskstartTime;
		this.startTimeStr=DateUtil.format(taskstartTime,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

	public Date getTaskendTime() {
		return this.taskendTime;
	}

	public void setTaskendTime(Date taskendTime) throws ParseException {
		this.taskendTime = taskendTime;
		this.endTimeStr=DateUtil.format(taskendTime,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
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

	
	public Integer getPartyUserNumber() {

		return partyUserNumber;
	}

	public void setPartyUserNumber(Integer partyUserNumber) {

		this.partyUserNumber = partyUserNumber;
	}

	public String getCreateDate() {

		return createDate;
	}

	public void setCreateDate(String createDate) {

		this.createDate = createDate;
	}

	public String getModel() {

		return model;
	}

	public void setModel(String model) {

		if (StringUtils.isBlank(model)) {
			model = SysmgrConstants.STR_EMPTY;
		}
		this.model = model;
	}

	public String getUserpic() {

		return userpic;
	}

	public void setUserpic(String userpic) {

		this.userpic = userpic;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		if (StringUtils.isBlank(username)) {
			username = SysmgrConstants.STR_EMPTY;
		}
		this.username = username;
	}

	
	public String getStartTimeStr() {
	
		return startTimeStr;
	}

	
	public void setStartTimeStr(String startTimeStr) throws ParseException {
	
		this.startTimeStr = startTimeStr;
		this.taskstartTime=DateUtil.parse(startTimeStr, DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

	
	public String getEndTimeStr() {
	
		return endTimeStr;
	}

	
	public void setEndTimeStr(String endTimeStr) throws ParseException {
	
		this.endTimeStr = endTimeStr;
		this.taskendTime  = DateUtil.parse(endTimeStr, DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

}