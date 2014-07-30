/*
 * @(#)LoginAuditServiceImp.java 2010-12-06
 * 
 * jeaw 版权所有2006~2015。
 */

package com.mrs.sysmgr.service.imp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mrs.sysmgr.dao.LoginAuditDao;
import com.mrs.sysmgr.entity.LoginAudit;
import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.LoginAuditService;
import com.wholetech.commons.Constants;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;

public class LoginAuditServiceImp extends BaseServiceImp<LoginAudit> implements LoginAuditService {
	
	private LoginAuditDao loginAuditDao;
	

	public void setLoginAuditDao(LoginAuditDao loginAuditDao) {
		this.loginAuditDao = loginAuditDao;
	}

	@Override
	public BaseDao<LoginAudit> getBaseDao() {		
		return this.loginAuditDao;
	}
	
	
	/**
	 * 记录用户登录审计日志。
	 * 
	 * @param user 登录用户实体。
	 */
	public void updateLoginAudit(User user,HttpServletRequest rq,HttpSession session) {
		
		
	}
	
	public void savelogout(HttpSession session){
		
		String auditid = (String)session.getAttribute(Constants.AuditID_Session);
		session.removeAttribute(Constants.USER_IN_SESSION);
		session.removeAttribute(Constants.AuditID_Session);
		LoginAudit audit = this.loginAuditDao.get(auditid);
		audit.setLogoutTime(loginAuditDao.getSystemDate());
		this.loginAuditDao.update(audit);
	}

	/**
	 * 记录用户登录审计日志。
	 * 
	 * @param user 登录用户实体。
	 */
	public void saveLoginAudit(User user,HttpServletRequest rq,HttpSession session) {
		User u = (User)session.getAttribute(Constants.USER_IN_SESSION);
		if(u!=null&&u.getLoginid().equals(user.getLoginid())){}//当session中存在此用户时，就不记录登录
		else {
			LoginAudit loginAudit = new LoginAudit();
			loginAudit.setUserIpAddr(rq.getRemoteAddr());
			loginAudit.setSessionID(session.getId());
			loginAudit.setLoginId(user.getLoginid());
			loginAudit.setLoginTime(loginAuditDao.getSystemDate());
			this.create(loginAudit);
			session.setAttribute(Constants.USER_IN_SESSION, user);
			session.setAttribute(Constants.AuditID_Session, loginAudit.getId());
		}
	}

	public List<String> removeLoginAudits(String[] ids) {
		List<LoginAudit> listLoginAudit = new ArrayList<LoginAudit>();
		for(String id : ids){
			listLoginAudit.add((LoginAudit)get(id));
		}
		this.batchRemove(listLoginAudit);
		return null;
	}

}