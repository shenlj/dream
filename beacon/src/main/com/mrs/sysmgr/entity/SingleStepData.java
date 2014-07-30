package com.mrs.sysmgr.entity;

import java.util.Date;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名：SensorType.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明： 单日步走信息
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */

public class SingleStepData extends BaseStandardEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userid;
	private String sensorid;
	private String sensornumber;
	private Date daytime;
	private Double singlecalorie;
	private Long singlesteps;
	private Long singlefaststeps;
	private Long singleslowsteps;
	private Long singlestairsteps;
	private Long singleminutes;
	private Date begintime;
	private Date endtime;
	private Double singlestepkm;
	private String remark1;
	private String remark2;


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

	public Date getDaytime() {
		return this.daytime;
	}

	public void setDaytime(Date daytime) {
		this.daytime = daytime;
	}

	public Double getSinglecalorie() {
		return this.singlecalorie;
	}

	public void setSinglecalorie(Double singlecalorie) {
		this.singlecalorie = singlecalorie;
	}

	public Long getSinglesteps() {
		return this.singlesteps;
	}

	public void setSinglesteps(Long singlesteps) {
		this.singlesteps = singlesteps;
	}

	public Long getSinglefaststeps() {
		return this.singlefaststeps;
	}

	public void setSinglefaststeps(Long singlefaststeps) {
		this.singlefaststeps = singlefaststeps;
	}

	public Long getSingleslowsteps() {
		return this.singleslowsteps;
	}

	public void setSingleslowsteps(Long singleslowsteps) {
		this.singleslowsteps = singleslowsteps;
	}

	public Long getSinglestairsteps() {
		return this.singlestairsteps;
	}

	public void setSinglestairsteps(Long singlestairsteps) {
		this.singlestairsteps = singlestairsteps;
	}

	public Long getSingleminutes() {
		return this.singleminutes;
	}

	public void setSingleminutes(Long singleminutes) {
		this.singleminutes = singleminutes;
	}

	public Date getBegintime() {
		return this.begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Double getSinglestepkm() {
		return this.singlestepkm;
	}

	public void setSinglestepkm(Double singlestepkm) {
		this.singlestepkm = singlestepkm;
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