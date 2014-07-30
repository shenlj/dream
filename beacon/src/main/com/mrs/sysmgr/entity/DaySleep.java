package com.mrs.sysmgr.entity;
import java.text.ParseException;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.util.DateUtil;

/**
 * 文件名： DaySleep.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：单日睡眠
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@XmlRootElement(name = "DaySleep")
@XmlAccessorType( XmlAccessType.FIELD )
public class DaySleep extends BaseStandardEntity {

	private static final long serialVersionUID = 1L;
	  
	private String userid;
	  
	private String sensorid;
	private String sensornumber;
	private Date sleeptime;
	private Integer daysleeptime;
	private Integer daylightsleep;
	private Date lightsleepStart;
	private Date lightsleepEnd;
	private String lightsleepStartStr;
	private String lightsleepEndStr;
	private Integer daydeepsleep;
	private Date daydeepsleepStart;
	private Date daydeepsleepEnd;
	private String daydeepsleepStartStr;
	private String daydeepsleepEndStr;
	private Long getup;
	private String remark1;
	private String remark2;
	private String remark3;

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getSensorid() {
		return this.sensorid;
	}

	public void setSensorid(String sensorid) {
		this.sensorid = sensorid;
	}

	public String getSensornumber() {
		return this.sensornumber;
	}

	public void setSensornumber(String sensornumber) {
		this.sensornumber = sensornumber;
	}

	public Date getSleeptime() {
		return this.sleeptime;
	}

	public void setSleeptime(Date sleeptime) {
		this.sleeptime = sleeptime;
	}

	public Integer getDaysleeptime() {
		return this.daysleeptime;
	}

	public void setDaysleeptime(Integer daysleeptime) {
		this.daysleeptime = daysleeptime;
	}

	public Integer getDaylightsleep() {
		return this.daylightsleep;
	}

	public void setDaylightsleep(Integer daylightsleep) {
		this.daylightsleep = daylightsleep;
	}

	public Date getLightsleepStart() {
		return this.lightsleepStart;
	}

	public void setLightsleepStart(Date lightsleepStart) {
		this.lightsleepStart = lightsleepStart;
		this.lightsleepStartStr=DateUtil.format(lightsleepStart,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

	public Date getLightsleepEnd() {
		return this.lightsleepEnd;
	}

	public void setLightsleepEnd(Date lightsleepEnd) {
		this.lightsleepEnd = lightsleepEnd;
		this.lightsleepEndStr=DateUtil.format(lightsleepEnd,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

	public Integer getDaydeepsleep() {
		return this.daydeepsleep;
	}

	public void setDaydeepsleep(Integer daydeepsleep) {
		this.daydeepsleep = daydeepsleep;
	}

	public Date getDaydeepsleepStart() {
		return this.daydeepsleepStart;
	}

	public void setDaydeepsleepStart(Date daydeepsleepStart) {
		this.daydeepsleepStart = daydeepsleepStart;
	}

	public Date getDaydeepsleepEnd() {
		return this.daydeepsleepEnd;
	}

	public void setDaydeepsleepEnd(Date daydeepsleepEnd) {
		this.daydeepsleepEnd = daydeepsleepEnd;
	}

	public Long getGetup() {
		return this.getup;
	}

	public void setGetup(Long getup) {
		this.getup = getup;
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

	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	
	public String getLightsleepStartStr() {
	
		return lightsleepStartStr;
	}

	
	public void setLightsleepStartStr(String lightsleepStartStr) {
	
		this.lightsleepStartStr = lightsleepStartStr;
	}

	
	public String getLightsleepEndStr() {
	
		return lightsleepEndStr;
	}

	
	public void setLightsleepEndStr(String lightsleepEndStr) {
	
		this.lightsleepEndStr = lightsleepEndStr;
	}

	
	public String getDaydeepsleepStartStr() {
	
		return daydeepsleepStartStr;
	}

	
	public void setDaydeepsleepStartStr(String daydeepsleepStartStr)throws ParseException{
	
		this.daydeepsleepStartStr = daydeepsleepStartStr;
		this.daydeepsleepStart=DateUtil.parse(daydeepsleepStartStr, DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

	
	public String getDaydeepsleepEndStr() {
	
		return daydeepsleepEndStr;
	}

	
	public void setDaydeepsleepEndStr(String daydeepsleepEndStr)throws ParseException {
	
		this.daydeepsleepEndStr = daydeepsleepEndStr;
		this.daydeepsleepEnd=DateUtil.parse(daydeepsleepEndStr, DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}


}