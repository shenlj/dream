package com.mrs.sysmgr.entity;

import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Role.java
 * 功能说明：角色实体
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2007-2008 Digitalchina CO.,LTD. All rights reserved.
 */
public class Role extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID = -4784413569679218911L;

	private String name;

	private String status;

	private String descn;

	private String principalId;

	private String principalName;

	private String isAdmin;

	private Set<User> users;

	private Set<Permission> permissions;

	private String authorize;

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

	public String getPrincipalId() {

		return principalId;
	}

	public void setPrincipalId(final String principalId) {

		this.principalId = principalId;
	}

	public String getPrincipalName() {

		return principalName;
	}

	public void setPrincipalName(final String principalName) {

		this.principalName = principalName;
	}

	public Set<User> getUsers() {

		return users;
	}

	public void setUsers(final Set<User> users) {

		this.users = users;
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

	public String getAuthority() {

		return this.name;
	}

	public String getIsAdmin() {

		return this.isAdmin;
	}

	public void setIsAdmin(final String isAdmin) {

		this.isAdmin = isAdmin;
	}

	public String getStatus() {

		return this.status;
	}

	public void setStatus(final String status) {

		this.status = status;
	}

}
