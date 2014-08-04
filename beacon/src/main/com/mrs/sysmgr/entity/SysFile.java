package com.mrs.sysmgr.entity;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文件名： SysFile.java
 * 作者： 署名
 * 日期： 2012-4-3
 * 功能说明：公共的附件信息实体。如果业务实体带有附件信息，可以使用这个
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
@XmlRootElement(name = "SysFile")
public class SysFile {

	private String id;
	private String fileName;
	private String logicFileName;
	private String busiId;
	private String busiType;
	private String locked;
	private String remark;
	private String operatorCode;
	private String operatorName;
	private String operateType;
	private Date operateTime;

	public SysFile() {

	}

	/**
	 * 通过数组构造SysFile。
	 * 要求数组的顺序对应：ID, FILENAME, LOGICFILENAME, BUSIID, BUSITYPE;
	 * 而且数量 不能少。
	 * 
	 * @param arr
	 */
	public SysFile(final Object[] arr) {

		id = arr[0].toString();
		fileName = arr[1].toString();
		logicFileName = arr[2].toString();
		busiId = arr[3] == null ? "" : arr[3].toString();
		busiType = arr[4] == null ? "" : arr[4].toString();
		locked = arr[5] == null ? "" : arr[5].toString();
		remark = arr[6] == null ? "" : arr[6].toString();
		operatorCode = arr[7] == null ? "" : arr[7].toString();
		operatorName = arr[8] == null ? "" : arr[8].toString();
		operateTime = arr[9] == null ? null : (Date) arr[9];
		remark = arr[10] == null ? "" : arr[10].toString();
	}

	public String getFileName() {

		return fileName;
	}

	public void setFileName(final String fileName) {

		this.fileName = fileName;
	}

	public String getLogicFileName() {

		return logicFileName;
	}

	public void setLogicFileName(final String logicFileName) {

		this.logicFileName = logicFileName;
	}

	public String getBusiId() {

		return busiId;
	}

	public void setBusiId(final String busiId) {

		this.busiId = busiId;
	}

	public String getBusiType() {

		return busiType;
	}

	public void setBusiType(final String busiType) {

		this.busiType = busiType;
	}

	public String getLocked() {

		return locked;
	}

	public void setLocked(final String locked) {

		this.locked = locked;
	}

	public String getRemark() {

		return remark;
	}

	public void setRemark(final String remark) {

		this.remark = remark;
	}

	public String getId() {

		return id;
	}

	public void setId(final String id) {

		this.id = id;
	}

	public String getOperatorCode() {

		return operatorCode;
	}

	public void setOperatorCode(final String operatorCode) {

		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {

		return operatorName;
	}

	public void setOperatorName(final String operatorName) {

		this.operatorName = operatorName;
	}

	public String getOperateType() {

		return operateType;
	}

	public void setOperateType(final String operateType) {

		this.operateType = operateType;
	}

	public Date getOperateTime() {

		return operateTime;
	}

	public void setOperateTime(final Date operateTime) {

		this.operateTime = operateTime;
	}

}
