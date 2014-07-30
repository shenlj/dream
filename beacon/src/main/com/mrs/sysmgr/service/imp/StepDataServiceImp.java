package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.StepDataDao;
import com.mrs.sysmgr.entity.StepData;
import com.mrs.sysmgr.service.StepDataService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class StepDataServiceImp extends BaseServiceImp<StepData> implements StepDataService {

	private StepDataDao stepDataDao;
	
	public void setStepDataDao(StepDataDao stepDataDao) {
		this.stepDataDao = stepDataDao;
	}
	
	@Override
	protected BaseDao<StepData> getBaseDao() {
		return this.stepDataDao;
	}
	
}
