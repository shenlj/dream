
package com.mrs.sysmgr.entity;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名：StepData.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明： 步走信息
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@XmlRootElement(name = "StepData")
@XmlAccessorType( XmlAccessType.FIELD )
public class StepData extends BaseStandardEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  
	private String userid;
	  
	private String sensorid;
	  
	private String sensornumber;
	private Double calorie;
	private Long steps;
	private Long faststeps;
	private Long slowsteps;
	private Long stairsteps;
	private Long minutes;
	private Date daytime;
	private Double stepkm;
	private byte[] steppackage;
	private String remark1;
	private String remark2;
	private String remark3;
	private Date operatetime;
	private DaySleep daySleep;
	private Double maxcal;
	private Double maxstepkm;
	private Long maxminutes;
	private byte[] sleepMinutes;//睡眠时间段，每4个字节表示一个时间段，格式为HHMM,

	public DaySleep getDaySleep() {

		return daySleep;
	}

	
	public void setDaySleep(DaySleep daySleep) {

		this.daySleep = daySleep;
	}

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

	public Double getCalorie() {
		return this.calorie;
	}

	public void setCalorie(Double calorie) {
		this.calorie = calorie;
	}

	public Long getSteps() {
		return this.steps;
	}

	public void setSteps(Long steps) {
		this.steps = steps;
	}

	public Long getFaststeps() {
		return this.faststeps;
	}

	public void setFaststeps(Long faststeps) {
		this.faststeps = faststeps;
	}

	public Long getSlowsteps() {
		return this.slowsteps;
	}

	public void setSlowsteps(Long slowsteps) {
		this.slowsteps = slowsteps;
	}

	public Long getStairsteps() {
		return this.stairsteps;
	}

	public void setStairsteps(Long stairsteps) {
		this.stairsteps = stairsteps;
	}

	public Long getMinutes() {
		return this.minutes;
	}

	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}

	public Date getDaytime() {
		return this.daytime;
	}

	public void setDaytime(Date daytime) {
		this.daytime = daytime;
	}

	public Double getStepkm() {
		return this.stepkm;
	}

	public void setStepkm(Double stepkm) {
		this.stepkm = stepkm;
	}

	public byte[] getSteppackage() {
		return this.steppackage;
	}

	public void setSteppackage(byte[] steppackage) {
		this.steppackage = steppackage;
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

	public Date getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Date operatetime) {
		this.operatetime = operatetime;
	}

	public Double getMaxcal() {

		return maxcal;
	}

	public void setMaxcal(Double maxcal) {

		this.maxcal = maxcal;
	}

	public Double getMaxstepkm() {

		return maxstepkm;
	}

	public void setMaxstepkm(Double maxstepkm) {

		this.maxstepkm = maxstepkm;
	}

	public Long getMaxminutes() {

		return maxminutes;
	}

	public void setMaxminutes(Long maxminutes) {

		this.maxminutes = maxminutes;
	}


	
	public byte[] getSleepMinutes() {
	
		return sleepMinutes;
	}


	
	public void setSleepMinutes(byte[] sleepMinutes) {
	
		this.sleepMinutes = sleepMinutes;
	}

}