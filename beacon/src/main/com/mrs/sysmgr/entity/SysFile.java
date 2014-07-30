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

  /**
   * 说明：
   */
  private static final long serialVersionUID = 1L;
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
  public SysFile(Object[] arr) {

    this.id = arr[0].toString();
    this.fileName = arr[1].toString();
    this.logicFileName = arr[2].toString();
    this.busiId = arr[3] == null ? "" : arr[3].toString();
    this.busiType = arr[4] == null ? "" : arr[4].toString();
    this.locked = arr[5] == null ? "" : arr[5].toString();
    this.remark = arr[6] == null ? "" : arr[6].toString();
    this.operatorCode = arr[7] == null ? "" : arr[7].toString();
    this.operatorName = arr[8] == null ? "" : arr[8].toString();
    this.operateTime = arr[9] == null ? null : (Date) arr[9];
    this.remark = arr[10] == null ? "" : arr[10].toString();
  }

  public String getFileName() {

    return this.fileName;
  }

  public void setFileName(String fileName) {

    this.fileName = fileName;
  }

  public String getLogicFileName() {

    return this.logicFileName;
  }

  public void setLogicFileName(String logicFileName) {

    this.logicFileName = logicFileName;
  }

  public String getBusiId() {

    return this.busiId;
  }

  public void setBusiId(String busiId) {

    this.busiId = busiId;
  }

  public String getBusiType() {

    return this.busiType;
  }

  public void setBusiType(String busiType) {

    this.busiType = busiType;
  }

  public String getLocked() {

    return this.locked;
  }

  public void setLocked(String locked) {

    this.locked = locked;
  }

  public String getRemark() {

    return this.remark;
  }

  public void setRemark(String remark) {

    this.remark = remark;
  }

  public String getId() {

    return this.id;
  }

  public void setId(String id) {

    this.id = id;
  }

  public String getOperatorCode() {

    return this.operatorCode;
  }

  public void setOperatorCode(String operatorCode) {

    this.operatorCode = operatorCode;
  }

  public String getOperatorName() {

    return this.operatorName;
  }

  public void setOperatorName(String operatorName) {

    this.operatorName = operatorName;
  }

  public String getOperateType() {

    return this.operateType;
  }

  public void setOperateType(String operateType) {

    this.operateType = operateType;
  }

  public Date getOperateTime() {

    return this.operateTime;
  }

  public void setOperateTime(Date operateTime) {

    this.operateTime = operateTime;
  }

}
