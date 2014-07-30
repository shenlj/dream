package com.mrs.sysmgr.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.mrs.sysmgr.common.SysmgrConstants;
import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名：UserData.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：用戶信息
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
/*
 * @XmlRootElement-指定XML根元素名称（可选）
   @XmlAccessorType-控制属性或方法序列化
	四种方案：
	FIELD-对每个非静态，非瞬变属性JAXB工具自动绑定成XML，除非注明XmlTransient  
	NONE-不做任何处理
	PROPERTY-对具有set/get方法的属性进行绑定，除非注明XmlTransient  
	PUBLIC_MEMBER -对有set/get方法的属性或具有共公访问权限的属性进行绑定，除非注明XmlTransient   
	@XmlType-映射一个类或一个枚举类型成一个XML Schema类型
 */
@XmlRootElement(name = "UserData")
@XmlAccessorType( XmlAccessType.FIELD )
public class UserData extends BaseStandardEntity {

	private static final long serialVersionUID = 1L;
	  
	private String username;
	private String password;
	private Date regdate;
	private String birthday;
	private Long height = 0l;
	private Double weight = 0d;
	private Long waist = 0l;
	private Double stride = 0d;
	private String email;
	  
	private String sensorid = SysmgrConstants.SENSOR_ZNSH;
	private Date bindtime;
	private String remark1;
	private String remark2;
	private String sex;
	private String userpic;
	// 用于显示是否已经是朋友的状态：0 已添加：代表改好友已经被用户添加，1 添加：代表好友已在使用手环，且还未被用户添加，2 邀请：代表用户未使用手环，可以发送邀请，邀请用户体验手环
	  
	private String friendZt;
	private Double totalStepKm;// 总步走公里数
	private Double totalCal;// 总消耗能量
	private String requestid;

	public String getRequestid() {

		return requestid;
	}

	public void setRequestid(String requestid) {

		this.requestid = requestid;
	}

	public UserData() {

	}

	public UserData(String id, String username, Date regdate, String birthday,
			Long height, Double weight, Long waist, Double stride, String email, String sensorid,
			Date bindtime, String remark1, String remark2,
			String sex, String friendZt, String userpic) {

		setId(id);
		this.username = username;
		this.regdate = regdate;
		this.birthday = birthday;
		this.height = height;
		this.weight = weight;
		this.waist = waist;
		this.stride = stride;
		this.email = email;
		this.sensorid = sensorid;
		this.bindtime = bindtime;
		this.remark1 = remark1;
		this.remark2 = remark2;
		this.sex = sex;
		this.friendZt = friendZt;
		this.userpic = userpic;
	}


	public String getUsername() {

		return username;
	}

	public void setUsername(String username) {

		this.username = username;
	}

	public String getPassword() {

		return password;
	}

	public void setPassword(String password) {

		this.password = password;
	}

	public Date getRegdate() {

		return regdate;
	}

	public void setRegdate(Date regdate) {

		this.regdate = regdate;
	}

	public String getBirthday() {

		return birthday;
	}

	public void setBirthday(String birthday) {

		if (StringUtils.isBlank(birthday)) {
			birthday = SysmgrConstants.STR_EMPTY;
		}
		this.birthday = birthday;
	}

	public Long getHeight() {

		return height;
	}

	public void setHeight(Long height) {

		this.height = height;
	}

	public Double getWeight() {

		return weight;
	}

	public void setWeight(Double weight) {

		this.weight = weight;
	}

	public Long getWaist() {

		return waist;
	}

	public void setWaist(Long waist) {

		this.waist = waist;
	}

	public Double getStride() {

		return stride;
	}

	public void setStride(Double stride) {

		this.stride = stride;
	}

	public String getEmail() {

		return email;
	}

	public void setEmail(String email) {

		if (StringUtils.isBlank(email)) {
			email = SysmgrConstants.STR_EMPTY;
		}
		this.email = email;
	}

	public String getSensorid() {

		return sensorid;
	}

	public void setSensorid(String sensorid) {

		this.sensorid = sensorid;
	}

	public Date getBindtime() {

		return bindtime;
	}

	public void setBindtime(Date bindtime) {

		this.bindtime = bindtime;
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

	public String getSex() {

		return sex;
	}

	public void setSex(String sex) {

		if (StringUtils.isBlank(sex)) {
			sex = SysmgrConstants.STR_EMPTY;
		}
		this.sex = sex;
	}

	public String getFriendZt() {

		return friendZt;
	}

	public void setFriendZt(String friendZt) {

		this.friendZt = friendZt;
	}

	public String getUserpic() {

		return userpic;
	}

	public void setUserpic(String userpic) {
		if(StringUtils.isBlank(userpic)){
			userpic = "";
		}
		this.userpic = userpic;
	}

	public Double getTotalStepKm() {

		return totalStepKm;
	}

	public void setTotalStepKm(Double totalStepKm) {

		this.totalStepKm = totalStepKm;
	}

	public Double getTotalCal() {

		return totalCal;
	}

	public void setTotalCal(Double totalCal) {

		this.totalCal = totalCal;
	}
	 
	@Override
	public void setId(String id) {
		super.setId(id);
	}
	 
	@Override
	public String getId() {
		return super.getId();
	}
}