package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.dao.OrganizationDao;
import com.mrs.sysmgr.entity.Organization;
import com.mrs.sysmgr.service.OrganizationService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;

public class OrganizationServiceImp extends BaseServiceImp<Organization> implements OrganizationService {

	private OrganizationDao organizationDao;

	public void setOrganizationDao(final OrganizationDao organizationDao) {

		this.organizationDao = organizationDao;
	}

	@Override
	protected BaseDao<Organization> getBaseDao() {

		// TODO Auto-generated method stub
		return organizationDao;
	}

}
