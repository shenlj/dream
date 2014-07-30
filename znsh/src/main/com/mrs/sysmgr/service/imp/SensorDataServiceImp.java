package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.SensorDataDao;
import com.mrs.sysmgr.entity.SensorData;
import com.mrs.sysmgr.service.SensorDataService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class SensorDataServiceImp extends BaseServiceImp<SensorData> implements SensorDataService {

	private SensorDataDao sensorDataDao;
	
	public void setSensorDataDao(SensorDataDao sensorDataDao) {
		this.sensorDataDao = sensorDataDao;
	}
	
	@Override
	protected BaseDao<SensorData> getBaseDao() {
		return this.sensorDataDao;
	}
	
}
