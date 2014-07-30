package com.mrs.sysmgr.entity;
import java.util.Date;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： SensorData.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明： 设备信息
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */

public class SensorData extends BaseStandardEntity {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sensortype;
	private String sensorname;
	private String sensormemo;
	private String sensornumber;
	private String factory;
	private Date productdate;
	private String remark1;
	private String remark2;
	
	public String getSensortype() {
	
		return sensortype;
	}
	
	public void setSensortype(String sensortype) {
	
		this.sensortype = sensortype;
	}
	
	public String getSensorname() {
	
		return sensorname;
	}
	
	public void setSensorname(String sensorname) {
	
		this.sensorname = sensorname;
	}
	
	public String getSensormemo() {
	
		return sensormemo;
	}
	
	public void setSensormemo(String sensormemo) {
	
		this.sensormemo = sensormemo;
	}
	
	public String getSensornumber() {
	
		return sensornumber;
	}
	
	public void setSensornumber(String sensornumber) {
	
		this.sensornumber = sensornumber;
	}
	
	public String getFactory() {
	
		return factory;
	}
	
	public void setFactory(String factory) {
	
		this.factory = factory;
	}
	
	public Date getProductdate() {
	
		return productdate;
	}
	
	public void setProductdate(Date productdate) {
	
		this.productdate = productdate;
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