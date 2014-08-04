package com.mrs.sysmgr.entity;

import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.Constants;

/**
 * 功能说明：用户实体
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 */
public class User extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID = 3377108008232646552L;

	/** 用户状态：正常。 */
	public static final String STATUS_NORMAL = "1";

	/** 用户状态：锁定。 */
	public static final String STATUS_LOCKED = "0";

	/** 用户性别：男。 */
	public static final String SEX_MAN = "1";

	/** 用户性别：女。 */
	public static final String SEX_WOMAN = "0";
	/**
	 * 说明：登陆号
	 */
	private String loginid;

	private String orgname;

	/**
	 * 说明：密码
	 */
	private String passwd;

	/**
	 * 说明：用户名
	 */
	private String username;

	/**
	 * 说明：状态:1表示有效,否则为0
	 */
	private String status;

	/**
	 * 说明：状态:1表示有效,否则为0
	 */
	private String sex;

	/**
	 * 说明：用户角色
	 */
	private Set<Role> roles;

	private Set<Permission> permissions;

	/**
	 * 说明：用户组织机构
	 */
	private Organization organization;
	/**
	 * 说明：辅助字段，用于判定是否授权
	 */
	private String authorize;

	public String getLoginid() {

		return loginid;
	}

	public void setLoginid(final String loginid) {

		this.loginid = loginid;
	}

	public String getPasswd() {

		return passwd;
	}

	public void setPasswd(final String passwd) {

		this.passwd = passwd;
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

	public boolean isEnabled() {

		return status != null && !status
				.equals(Constants.STATUS_INVALID);
	}

	public Organization getOrganization() {

		return organization;
	}

	public void setOrganization(final Organization organization) {

		this.organization = organization;
	}

	public String getAuthorize() {

		return authorize;
	}

	public void setAuthorize(final String authorize) {

		this.authorize = authorize;
	}

	public String getUsername() {

		return username;
	}

	public void setUsername(final String username) {

		this.username = username;
	}

	public String getSex() {

		return sex;
	}

	public void setSex(final String sex) {

		this.sex = sex;
	}

	public Set<Permission> getPermissions() {

		return permissions;
	}

	public void setPermissions(final Set<Permission> permissions) {

		this.permissions = permissions;
	}

	public String getOrgname() {

		return getOrganization().getOrgName();
	}

	public void setOrgname(final String orgname) {

		this.orgname = getOrganization().getOrgName();
	}

}
