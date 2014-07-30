package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.MedalDao;
import com.mrs.sysmgr.entity.Medal;
import com.mrs.sysmgr.service.MedalService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class MedalServiceImp extends BaseServiceImp<Medal> implements MedalService {

	private MedalDao medalDao;
	
	public void setMedalDao(MedalDao medalDao) {
		this.medalDao = medalDao;
	}
	
	@Override
	protected BaseDao<Medal> getBaseDao() {
		return this.medalDao;
	}
	
}
