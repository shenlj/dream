package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.CompetitionPartyDao;
import com.mrs.sysmgr.entity.CompetitionParty;
import com.mrs.sysmgr.service.CompetitionPartyService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class CompetitionPartyServiceImp extends BaseServiceImp<CompetitionParty> implements CompetitionPartyService {

	private CompetitionPartyDao competitionPartyDao;
	
	public void setCompetitionPartyDao(CompetitionPartyDao competitionPartyDao) {
		this.competitionPartyDao = competitionPartyDao;
	}
	
	@Override
	protected BaseDao<CompetitionParty> getBaseDao() {
		return this.competitionPartyDao;
	}
	
}
