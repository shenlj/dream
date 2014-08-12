package com.wholetech.commons.entity;

import java.util.Date;

/**
 * 公共的附件信息实体。如果业务实体带有附件信息，可以使用这个。
 * 
 * @author zhouyza
 */

public class SysFile {

	private String id;
	private String fileName;
	private String logicFileName;
	private String busiId;
	private String busiType;
	private String subType;
	private String locked;
	private long downloadNum;

	private String updateUserId;
	private String updateUserName;
	private Date updateDate;

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
		this.subType = arr[5] == null ? "" : arr[5].toString();
		this.locked = arr[6] == null ? "" : arr[6].toString();
		this.updateUserId = arr[7] == null ? "" : arr[7].toString();
		this.updateUserName = arr[8] == null ? "" : arr[8].toString();
		this.updateDate = arr[9] == null ? null : (Date) arr[9];
		this.downloadNum = arr[10] == null ? 0 : Long.parseLong(arr[10].toString());
	}

	public String getId() {

		return this.id;
	}

	public void setId(String id) {

		this.id = id;
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

	public String getUpdateUserId() {

		return this.updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {

		this.updateUserId = updateUserId;
	}

	public String getUpdateUserName() {

		return this.updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {

		this.updateUserName = updateUserName;
	}

	public Date getUpdateDate() {

		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {

		this.updateDate = updateDate;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public long getDownloadNum() {
		return downloadNum;
	}

	public void setDownloadNum(long downloadNum) {
		this.downloadNum = downloadNum;
	}

}
