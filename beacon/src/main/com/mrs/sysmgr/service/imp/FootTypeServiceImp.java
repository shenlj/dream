package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.FootTypeDao;
import com.mrs.sysmgr.entity.FootType;
import com.mrs.sysmgr.service.FootTypeService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class FootTypeServiceImp extends BaseServiceImp<FootType> implements FootTypeService {

	private FootTypeDao footTypeDao;
	
	public void setFootTypeDao(FootTypeDao footTypeDao) {
		this.footTypeDao = footTypeDao;
	}
	
	@Override
	protected BaseDao<FootType> getBaseDao() {
		return this.footTypeDao;
	}
	
}
