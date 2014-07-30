
package com.mrs.sysmgr.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;

import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.util.DateUtil;

/**
 * 文件名：ZnshResult.java
 * 作者： 署名
 * 日期： 2011-12-27
 * 功能说明：接口返回结果容器
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */

@XmlRootElement(name = "ZnshResult")
@XmlAccessorType( XmlAccessType.FIELD )
public class ZnshResult extends BaseStandardEntity {

	private byte[] steppackage;
	private Map<String, Object> stepMap;
	private Boolean successFlg;
	private String msgObj;
	private String userpic;// 用户照片
	private String userid;
	private String userName;// 姓名
	private String sex;// 性别
	private Double totalKm;// 总公里 数
	private Double totalCal;// 总热量
	private Long totalSteps;// 总的步数
	private Integer rank;// 排名
	private Double totalTime;// 总时间
	private Double avgSpeep;// 平均速度
	private Double avgTime;// 平均时间
	private Double avgSteps;// 平均步数
	private Double avgCal;// 平均热量
	private String taskid;
	private String taskname;// 任务名称
	private Double endmark;// 截止条件
	private String taskstate;// 任务状态
	private Date taskstartTime;// 开始日期
	private Date taskendTime;// 结束日期
	private String startTimeStr;// 开始日期
	private String endTimeStr;// 结束日期
	private Integer partyUserNumber;// 人数限制
	private String createDate;// 创建日期
	private String model;// 模式
	private String overdate;// 完成日期
	private Double teamKm;// 团队公里
	private Double rate;// 贡献率
	private List<StepData> stepDataList;//运动步数

	public List<StepData> getStepDataList() {
	
		return stepDataList;
	}

	
	public void setStepDataList(List<StepData> stepDataList) {
	
		this.stepDataList = stepDataList;
	}

	public ZnshResult() {

	}

	public ZnshResult(Integer rank, String userPic, String userName, String overdate, Double totalKm, Double teamKm,
			Double rate) {

		this.rank = rank;
		if(StringUtils.isBlank(userPic)){
			userPic = "";
		}
		this.userpic = userPic;
		this.userName = userName;
		this.overdate = overdate;
		this.totalKm = totalKm;
		this.teamKm = teamKm;
		this.rate = rate;
	}

	public ZnshResult(String userid, String username, String sex, String userpic, String taskid, String taskname,
			String model, Double endmark, String taskstate, Date taskstartTime, Date taskendTime,
			Integer partyUserNumber, String createDate) {
		this.userid = userid;
		this.userName = username;
		this.sex = sex;
		if(StringUtils.isBlank(userpic)){
			userpic = "";
		}
		this.userpic = userpic;
		this.taskid = taskid;
		this.taskname = taskname;
		this.model = model;
		this.endmark = endmark;
		this.taskstate = taskstate;
		this.taskstartTime = taskstartTime;
		this.startTimeStr=DateUtil.format(taskstartTime,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
		this.taskendTime = taskendTime;
		this.endTimeStr=DateUtil.format(taskendTime,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
		this.partyUserNumber = partyUserNumber;
		this.createDate = createDate;
	}

	public ZnshResult(String userid,String userPic, String userName, String sex, Double totalKm, Double totalCal,
			Long totalSteps,
				Double totalTime, Double avgSpeep, Double avgTime, Double avgSteps, Double avgCal, Integer rank) {

		this.userid = userid;
		if(StringUtils.isBlank(userPic)){
			userPic = "";
		}
		this.userpic = userPic;
		this.userName = userName;
		this.sex = sex;
		this.totalKm = totalKm;
		this.totalCal = totalCal;
		this.totalSteps = totalSteps;
		this.totalTime = totalTime;
		this.avgSpeep = avgSpeep;
		this.avgTime = avgTime;
		this.avgSteps = avgSteps;
		this.avgCal = avgCal;
		this.rank = rank;
	}

	public byte[] getSteppackage() {

		return steppackage;
	}

	public void setSteppackage(byte[] steppackage) {

		this.steppackage = steppackage;
	}

	public Map<String, Object> getStepMap() {

		return stepMap;
	}

	public void setStepMap(Map<String, Object> stepMap) {

		this.stepMap = stepMap;
	}

	public Boolean isSuccessFlg() {

		return successFlg;
	}

	public void setSuccessFlg(Boolean successFlg) {

		this.successFlg = successFlg;
	}

	public String getMsgObj() {

		return msgObj;
	}

	public void setMsgObj(String msgObj) {

		this.msgObj = msgObj;
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

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public String getSex() {

		return sex;
	}

	public void setSex(String sex) {

		this.sex = sex;
	}

	public Double getTotalKm() {

		return totalKm;
	}

	public void setTotalKm(Double totalKm) {

		this.totalKm = totalKm;
	}

	public Double getTotalCal() {

		return totalCal;
	}

	public void setTotalCal(Double totalCal) {

		this.totalCal = totalCal;
	}

	public Long getTotalSteps() {

		return totalSteps;
	}

	public void setTotalSteps(Long totalSteps) {

		this.totalSteps = totalSteps;
	}

	public Integer getRank() {

		return rank;
	}

	public void setRank(Integer rank) {

		this.rank = rank;
	}

	public Double getTotalTime() {

		return totalTime;
	}

	public void setTotalTime(Double totalTime) {

		this.totalTime = totalTime;
	}

	public Double getAvgSpeep() {

		return avgSpeep;
	}

	public void setAvgSpeep(Double avgSpeep) {

		this.avgSpeep = avgSpeep;
	}

	public Double getAvgTime() {

		return avgTime;
	}

	public void setAvgTime(Double avgTime) {

		this.avgTime = avgTime;
	}

	public Double getAvgSteps() {

		return avgSteps;
	}

	public void setAvgSteps(Double avgSteps) {

		this.avgSteps = avgSteps;
	}

	public Double getAvgCal() {

		return avgCal;
	}

	public void setAvgCal(Double avgCal) {

		this.avgCal = avgCal;
	}

	public String getUserid() {

		return userid;
	}

	public void setUserid(String userid) {

		this.userid = userid;
	}

	public String getTaskid() {

		return taskid;
	}

	public void setTaskid(String taskid) {

		this.taskid = taskid;
	}

	public String getTaskname() {

		return taskname;
	}

	public void setTaskname(String taskname) {

		this.taskname = taskname;
	}

	public Double getEndmark() {

		return endmark;
	}

	public void setEndmark(Double endmark) {

		this.endmark = endmark;
	}

	public String getTaskstate() {

		return taskstate;
	}

	public void setTaskstate(String taskstate) {

		this.taskstate = taskstate;
	}

	public Date getTaskstartTime() {

		return taskstartTime;
	}

	public void setTaskstartTime(Date taskstartTime) {

		this.taskstartTime = taskstartTime;
		this.startTimeStr=DateUtil.format(taskstartTime,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
	}

	public Date getTaskendTime() {

		return taskendTime;
	}

	public void setTaskendTime(Date taskendTime) {

		this.taskendTime = taskendTime;
		this.endTimeStr=DateUtil.format(taskendTime,DateUtil.FMT_DATE_YYYY_MM_DD_HH24_MM_SS);
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

		this.model = model;
	}

	public String getOverdate() {

		return overdate;
	}

	public void setOverdate(String overdate) {

		this.overdate = overdate;
	}

	public Double getTeamKm() {

		return teamKm;
	}

	public void setTeamKm(Double teamKm) {

		this.teamKm = teamKm;
	}

	public Double getRate() {

		return rate;
	}

	public void setRate(Double rate) {

		this.rate = rate;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(getClass() != obj.getClass()) {
			return false;
		}
		final ZnshResult other = (ZnshResult)obj;
		if(getTaskid().equals(other.getTaskid())) {
			return true;
		}
		return false;
	}


	
	public String getStartTimeStr() {
	
		return startTimeStr;
	}


	
	public void setStartTimeStr(String startTimeStr) {
	
		this.startTimeStr = startTimeStr;
	}


	
	public String getEndTimeStr() {
	
		return endTimeStr;
	}


	
	public void setEndTimeStr(String endTimeStr) {
	
		this.endTimeStr = endTimeStr;
	}

}