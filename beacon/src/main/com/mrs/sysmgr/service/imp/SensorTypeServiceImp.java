package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.SensorTypeDao;
import com.mrs.sysmgr.entity.SensorType;
import com.mrs.sysmgr.service.SensorTypeService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class SensorTypeServiceImp extends BaseServiceImp<SensorType> implements SensorTypeService {

	private SensorTypeDao sensorTypeDao;
	
	public void setSensorTypeDao(SensorTypeDao sensorTypeDao) {
		this.sensorTypeDao = sensorTypeDao;
	}
	
	@Override
	protected BaseDao<SensorType> getBaseDao() {
		return this.sensorTypeDao;
	}
	
}
