package com.mrs.sysmgr.entity;

import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Resource.java
 * 功能说明：资源实体
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2007-2008 Digitalchina CO.,LTD. All rights reserved.
 */
public class Resource extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID = 1847521769330726313L;
	/**
	 * 说明：资源名称
	 */
	private String name;

	/**
	 * 说明：资源类别
	 */
	private String resType;

	/**
	 * 说明：资源串
	 */
	private String resString;

	/**
	 * 说明：菜单标示:1表示应用于菜单,否则为0
	 */
	private String forMenu;

	/**
	 * 说明：描述
	 */
	private String descn;

	/**
	 * 说明：资源权限
	 */
	private Set<Permission> permissions;

	/**
	 * 说明：辅助字段，用于判定是否授权
	 */
	private transient String authorize;

	public String getName() {

		return name;
	}

	public void setName(final String name) {

		this.name = name;
	}

	public String getForMenu() {

		return this.forMenu;
	}

	public void setForMenu(final String forMenu) {

		this.forMenu = forMenu;
	}

	public String getResString() {

		return resString;
	}

	public void setResString(final String resString) {

		this.resString = resString;
	}

	public String getDescn() {

		return descn;
	}

	public void setDescn(final String descn) {

		this.descn = descn;
	}

	public Set<Permission> getPermissions() {

		return permissions;
	}

	public void setPermissions(final Set<Permission> permissions) {

		this.permissions = permissions;
	}

	public String getAuthorize() {

		return this.authorize;
	}

	public void setAuthorize(final String authorize) {

		this.authorize = authorize;
	}

	public String getResType() {

		return this.resType;
	}

	public void setResType(final String resType) {

		this.resType = resType;
	}
}
