/*
 * @(#)LoginAuditService.java 2010-12-06
 * jeaw 版权所有2006~2015。
 */

package com.mrs.sysmgr.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mrs.sysmgr.entity.LoginAudit;
import com.mrs.sysmgr.entity.User;
import com.wholetech.commons.service.BaseService;

/**
 * 登录审计日志对应的服务层接口。<br>
 */
public interface LoginAuditService extends BaseService<LoginAudit> {

	/**
	 * 记录用户登录审计日志。
	 */
	public void saveLoginAudit(User user, HttpServletRequest rq, HttpSession session);

	/**
	 * 退出时清空session，记录logout时间
	 */
	public void savelogout(HttpSession session);

	/**
	 * 删除登陆审计日志。
	 *
	 * @param ids
	 *            待删除的实体流水号列表。
	 * @return 删除成功与否的消息列表。
	 */
	public List<String> removeLoginAudits(String[] ids);
}
