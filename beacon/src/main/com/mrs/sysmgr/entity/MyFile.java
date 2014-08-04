package com.mrs.sysmgr.entity;

import javax.xml.bind.annotation.XmlRootElement;

import com.wholetech.commons.BaseStandardEntity;

@XmlRootElement(name = "MyFile")
public class MyFile extends BaseStandardEntity {

	/**
	 * 说明：
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String userid;
	private byte[] userpic;
	private String userpicStr;

	public String getFileName() {

		return this.fileName;
	}

	public void setFileName(final String fileName) {

		this.fileName = fileName;
	}

	public String getUserid() {

		return this.userid;
	}

	public void setUserid(final String userid) {

		this.userid = userid;
	}

	public byte[] getUserpic() {

		return this.userpic;
	}

	public void setUserpic(final byte[] userpic) {

		this.userpic = userpic;
	}

	public String getUserpicStr() {

		return this.userpicStr;
	}

	public void setUserpicStr(final String userpicStr) {

		this.userpicStr = userpicStr;
	}
}
