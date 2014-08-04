package com.mrs.sysmgr.entity;

import java.util.HashSet;
import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Permission.java
 * 功能说明：权限实体
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2007-2008 Digitalchina CO.,LTD. All rights reserved.
 */
public class Permission extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID = -1927100045811348514L;
	/**
	 * 说明：权限名称
	 */
	private String name;

	private String permissionType;

	private String url;

	/**
	 * 说明：权限角色
	 */
	private Set<Role> roles = new HashSet<Role>(0);

	private Set<User> users = new HashSet<User>(0);

	/**
	 * 说明：描述
	 */
	private String descn;

	/**
	 * 说明：状态:1表示有效,否则为0
	 */
	private String status;

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

	public String getDescn() {

		return descn;
	}

	public void setDescn(final String descn) {

		this.descn = descn;
	}

	public String getStatus() {

		return status;
	}

	public void setStatus(final String status) {

		this.status = status;
	}

	public Set<Role> getRoles() {

		return roles;
	}

	public void setRoles(final Set<Role> roles) {

		this.roles = roles;
	}

	public String getAuthorize() {

		return this.authorize;
	}

	public void setAuthorize(final String authorize) {

		this.authorize = authorize;
	}

	public String getPermissionType() {

		return this.permissionType;
	}

	public void setPermissionType(final String permissionType) {

		this.permissionType = permissionType;
	}

	public String getUrl() {

		return this.url;
	}

	public void setUrl(final String url) {

		this.url = url;
	}

	public Set<User> getUsers() {

		return this.users;
	}

	public void setUsers(final Set<User> users) {

		this.users = users;
	}

}
