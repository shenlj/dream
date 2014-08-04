package com.mrs.sysmgr.service.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mrs.sysmgr.service.WaiteListService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.wholetech.commons.util.PropertyXmlMgr;

public class WaiteListServiceImp extends BaseServiceImp implements WaiteListService {

	@Override
	protected BaseDao getBaseDao() {

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map> getWaiteList() {

		// TODO Auto-generated method stub

		new ArrayList();
		PropertyXmlMgr.getString("WAITE", "waite");
		return null;
	}

}
