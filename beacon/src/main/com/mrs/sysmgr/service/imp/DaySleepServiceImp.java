package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.DaySleepDao;
import com.mrs.sysmgr.entity.DaySleep;
import com.mrs.sysmgr.service.DaySleepService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class DaySleepServiceImp extends BaseServiceImp<DaySleep> implements DaySleepService {

	private DaySleepDao daySleepDao;
	
	public void setDaySleepDao(DaySleepDao daySleepDao) {
		this.daySleepDao = daySleepDao;
	}
	
	@Override
	protected BaseDao<DaySleep> getBaseDao() {
		return this.daySleepDao;
	}
	
}
