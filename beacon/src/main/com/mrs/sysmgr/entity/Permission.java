package com.mrs.sysmgr.entity;

import java.util.HashSet;
import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 文件名： Permission.java
 * 功能说明：权限实体
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
public class Permission extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID= -1927100045811348514L;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getAuthorize() {
		return authorize;
	}

	public void setAuthorize(String authorize) {
		this.authorize = authorize;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
}