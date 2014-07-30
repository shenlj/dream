package com.mrs.sysmgr.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.wholetech.commons.BaseStandardEntity;

@XmlRootElement(name = "MyFile")
public class MyFile  extends BaseStandardEntity{
	 /**
	 * 说明：
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	 private String userid;
	 private byte[] userpic;
	 private String userpicStr;
	

	public String getFileName() {
	
		return fileName;
	}
	
	public void setFileName(String fileName) {
	
		this.fileName = fileName;
	}

	public String getUserid() {
	
		return userid;
	}


	
	public void setUserid(String userid) {
	
		this.userid = userid;
	}

	
	public byte[] getUserpic() {
	
		return userpic;
	}

	
	public void setUserpic(byte[] userpic) {
	
		this.userpic = userpic;
	}

	
	public String getUserpicStr() {
	
		return userpicStr;
	}

	
	public void setUserpicStr(String userpicStr) {
	
		this.userpicStr = userpicStr;
	}
}
