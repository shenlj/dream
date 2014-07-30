package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.dao.UserMedalDao;
import com.mrs.sysmgr.entity.UserMedal;
import com.mrs.sysmgr.service.UserMedalService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;

/**
 * 文件名： UserMedalServiceImp.java
 * 作者： 署名
 * 日期： 2012-4-22
 * 功能说明：章节业务处理类
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
public class UserMedalServiceImp extends BaseServiceImp<UserMedal> implements UserMedalService {

	private UserMedalDao userMedalDao;
	
	public void setUserMedalDao(UserMedalDao userMedalDao) {
		this.userMedalDao = userMedalDao;
	}
	
	@Override
	protected BaseDao<UserMedal> getBaseDao() {
		return this.userMedalDao;
	}
	
}
