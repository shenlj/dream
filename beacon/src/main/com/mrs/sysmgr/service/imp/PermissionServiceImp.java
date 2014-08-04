package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.dao.PermissionDao;
import com.mrs.sysmgr.entity.Permission;
import com.mrs.sysmgr.service.PermissionService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseServiceImp;

public class PermissionServiceImp extends BaseServiceImp<Permission> implements PermissionService {

	private PermissionDao permissionDao;

	public void setPermissionDao(final PermissionDao permissionDao) {

		this.permissionDao = permissionDao;
	}

	@Override
	protected BaseDao getBaseDao() {

		// TODO Auto-generated method stub
		return permissionDao;
	}

	@Override
	public void saveRole(final Permission permission) {

		permissionDao.saveOrUpdate(permission);
	}

	@Override
	public Page<Permission> getPermissionPage(final Page<Permission> page) {

		// TODO Auto-generated method stub
		return (Page<Permission>) permissionDao.findPageByHql(page, "hql_sys_getPermissionPage");
	}

}
