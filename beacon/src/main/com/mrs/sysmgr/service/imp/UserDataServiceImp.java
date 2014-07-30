package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.UserDataDao;
import com.mrs.sysmgr.entity.UserData;
import com.mrs.sysmgr.service.UserDataService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class UserDataServiceImp extends BaseServiceImp<UserData> implements UserDataService {

	private UserDataDao userDataDao;
	
	public void setUserDataDao(UserDataDao userDataDao) {
		this.userDataDao = userDataDao;
	}
	
	@Override
	protected BaseDao<UserData> getBaseDao() {
		return this.userDataDao;
	}
	
}
