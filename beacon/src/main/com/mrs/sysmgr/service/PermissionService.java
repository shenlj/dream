package com.mrs.sysmgr.service;

import com.mrs.sysmgr.entity.Permission;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseService;

public interface PermissionService extends BaseService<Permission> {
	
	public Page<Permission> getPermissionPage(Page<Permission> page);
	
	public void saveRole(Permission permission);
}
