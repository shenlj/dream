package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.dao.RoleDao;
import com.mrs.sysmgr.entity.Role;
import com.mrs.sysmgr.service.RoleService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseServiceImp;

public class RoleServiceImp extends BaseServiceImp<Role> implements RoleService {

	private RoleDao roleDao;

	@Override
	protected BaseDao getBaseDao() {

		// TODO Auto-generated method stub
		return roleDao;
	}

	public void setRoleDao(final RoleDao roleDao) {

		this.roleDao = roleDao;
	}

	@Override
	public void saveRole(final Role role) {

		roleDao.saveOrUpdate(role);
	}

	@Override
	public Page<Role> getRolePage(final Page<Role> page) {

		return (Page<Role>) roleDao.findPageByHql(page, "hql_sys_getRolePage");
	}

}
