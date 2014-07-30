package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.SingleStepDataDao;
import com.mrs.sysmgr.entity.SingleStepData;
import com.mrs.sysmgr.service.SingleStepDataService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class SingleStepDataServiceImp extends BaseServiceImp<SingleStepData> implements SingleStepDataService {

	private SingleStepDataDao singleStepDataDao;
	
	public void setSingleStepDataDao(SingleStepDataDao singleStepDataDao) {
		this.singleStepDataDao = singleStepDataDao;
	}
	
	@Override
	protected BaseDao<SingleStepData> getBaseDao() {
		return this.singleStepDataDao;
	}
	
}
