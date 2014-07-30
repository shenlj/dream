package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.dao.UserRequestDao;
import com.mrs.sysmgr.entity.UserRequest;
import com.mrs.sysmgr.service.UserRequestService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class UserRequestServiceImp extends BaseServiceImp<UserRequest> implements UserRequestService {

	private UserRequestDao userRequestDao;
	
	


	public void setUserRequestDao(UserRequestDao userRequestDao) {

		this.userRequestDao = userRequestDao;
	}

	@Override
	protected BaseDao<UserRequest> getBaseDao() {

		return userRequestDao;
	}
}
