package com.mrs.sysmgr.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： CompetitionParty.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：竞赛任务过程
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@XmlRootElement(name = "CompetitionParty")
@XmlAccessorType( XmlAccessType.FIELD )
public class CompetitionParty extends BaseStandardEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  
	private String taskid;
	  
	private String userid;
	private Long step;
	private Double locx;
	private Double locy;
	private Double rate;
	private String remark1;
	private String remark2;
	private Double stepkm;
	private Double calorie;
	private Long minutes;
	private String overDate;
	private Date syncDate;


	public String getTaskid() {

		return this.taskid;
	}

	public void setTaskid(String taskid) {

		this.taskid = taskid;
	}

	public String getUserid() {

		return this.userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
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

	public Double getStepkm() {

		return stepkm;
	}

	public void setStepkm(Double stepkm) {

		this.stepkm = stepkm;
	}

	public Double getCalorie() {

		return calorie;
	}

	public void setCalorie(Double calorie) {

		this.calorie = calorie;
	}

	public Long getMinutes() {

		return minutes;
	}

	public void setMinutes(Long minutes) {

		this.minutes = minutes;
	}

	public String getOverDate() {

		return overDate;
	}

	public void setOverDate(String overDate) {

		this.overDate = overDate;
	}

	public Long getStep() {

		return step;
	}

	public void setStep(Long step) {

		this.step = step;
	}

	public Double getLocx() {

		return locx;
	}

	public void setLocx(Double locx) {

		this.locx = locx;
	}

	public Double getLocy() {

		return locy;
	}

	public void setLocy(Double locy) {

		this.locy = locy;
	}

	public Double getRate() {

		return rate;
	}

	public void setRate(Double rate) {

		this.rate = rate;
	}

	
	public Date getSyncDate() {
	
		return syncDate;
	}

	
	public void setSyncDate(Date syncDate) {
	
		this.syncDate = syncDate;
	}


}
