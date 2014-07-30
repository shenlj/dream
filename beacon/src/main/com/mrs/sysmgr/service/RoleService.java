package com.mrs.sysmgr.service;

import com.mrs.sysmgr.entity.Role;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseService;

public interface RoleService extends BaseService<Role> {
	
	public Page<Role> getRolePage(Page<Role> page);
	
	public void saveRole(Role role);
}
