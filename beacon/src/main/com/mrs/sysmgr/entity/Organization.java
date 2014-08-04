package com.mrs.sysmgr.entity;

import java.util.HashSet;
import java.util.Set;

import com.wholetech.commons.BaseStandardEntity;

/**
 * 功能说明：组织机构实体
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2007-2008 Digitalchina CO.,LTD. All rights reserved.
 */
public class Organization extends BaseStandardEntity implements java.io.Serializable {

	static final long serialVersionUID = -1444667917030767162L;
	/**
	 * 说明：组织机构代码
	 */
	private String orgCode;

	/**
	 * 说明：组织机构名称
	 */
	private String orgName;

	private Integer orgLelvl;

	private String managername;

	private String address;

	private String treePath;

	private String status;

	private String contactname;

	private String contactphone;

	private String respon;

	/**
	 * 说明：父组织机构
	 */
	private Organization father;

	/**
	 * 说明：子组织机构
	 */
	private Set<Organization> children;

	/**
	 * 说明：组织机构用户
	 */
	private Set<User> users = new HashSet<User>(0);

	public String getOrgCode() {

		return orgCode;
	}

	public void setOrgCode(final String orgCode) {

		this.orgCode = orgCode;
	}

	public String getOrgName() {

		return orgName;
	}

	public void setOrgName(final String orgName) {

		this.orgName = orgName;
	}

	public Integer getOrgLelvl() {

		return orgLelvl;
	}

	public void setOrgLelvl(final Integer orgLelvl) {

		this.orgLelvl = orgLelvl;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(final String address) {

		this.address = address;
	}

	public String getTreePath() {

		return treePath;
	}

	public void setTreePath(final String treePath) {

		this.treePath = treePath;
	}

	public String getStatus() {

		return this.status;
	}

	public void setStatus(final String status) {

		this.status = status;
	}

	/**
	 * 关联用户集合
	 *
	 * @hibernate.set lazy="true" inverse="true"
	 * @hibernate.key column="ORG_ID"
	 * @hibernate.one-to-many class="com.lixia.fitfw.admin.security.domain.User"
	 * @return
	 */
	public Set<User> getUsers() {

		return this.users;
	}

	public void setUsers(final Set<User> users) {

		this.users = users;
	}

	/**
	 * 父对象
	 *
	 * @hibernate.many-to-one column="FATHERID" not-null="false"
	 * @return
	 */
	public Organization getFather() {

		return this.father;
	}

	public void setFather(final Organization father) {

		this.father = father;
	}

	/**
	 * 子对象集合
	 *
	 * @hibernate.set inverse="true" lazy="true" order-by="code"
	 * @hibernate.key column="FATHERID"
	 * @hibernate.one-to-many class="com.lixia.fitfw.admin.security.domain.Organization"
	 * @return
	 */
	public Set<Organization> getChildren() {

		return this.children;
	}

	public void setChildren(final Set<Organization> children) {

		this.children = children;
	}

	public String getManagername() {

		return this.managername;
	}

	public void setManagername(final String managername) {

		this.managername = managername;
	}

	public String getContactname() {

		return this.contactname;
	}

	public void setContactname(final String contactname) {

		this.contactname = contactname;
	}

	public String getContactphone() {

		return this.contactphone;
	}

	public void setContactphone(final String contactphone) {

		this.contactphone = contactphone;
	}

	public String getRespon() {

		return this.respon;
	}

	public void setRespon(final String respon) {

		this.respon = respon;
	}

}
