package com.mrs.sysmgr.entity;

import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Role.java
 * 功能说明：角色实体
 * 
 * ===============================================================================
 * 修改记录：
 * 修改作者    日期      修改内容
 * 
 *
 * 
 * 
 * ===============================================================================
 *  Copyright (c) 2007-2008 Digitalchina CO.,LTD.  All rights reserved.
 */
public class Role extends BaseStandardEntity implements java.io.Serializable{

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
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}
	
	public String getPrincipalId() {
        return this.principalId;
    }
    
    public void setPrincipalId(String principalId) {
        this.principalId = principalId;
    }
	
	public String getPrincipalName() {
		return this.principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return this.permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	public String getAuthority() {
		return name;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}