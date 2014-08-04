package com.mrs.sysmgr.web.action;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.LoginAuditService;
import com.mrs.sysmgr.service.OrganizationService;
import com.mrs.sysmgr.service.UserService;
import com.opensymphony.xwork2.Action;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.web.BaseAction;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results({

})
public class LoginAction extends BaseAction<User> {

	private UserService userService;
	private LoginAuditService loginAuditService;
	private OrganizationService organizationService;

	public void setUserService(final UserService userService) {

		this.userService = userService;
	}

	public void setLoginAuditService(final LoginAuditService loginAuditService) {

		this.loginAuditService = loginAuditService;
	}

	public void setOrganizationService(final OrganizationService organizationService) {

		this.organizationService = organizationService;
	}

	@Override
	protected BaseService getBaseService() {

		// TODO Auto-generated method stub
		return null;
	}

	public String saveLogout() {

		if (getSession() != null) {
			loginAuditService.savelogout(getSession());
		}
		return Action.NONE;
	}

	public String saveLogin() {

		final String loginid = getParameter("loginID");
		final String password = getParameter("passwd");

		final User user = userService.getUserByLoginID(loginid);

		if (user == null) {
			this.renderJson(true, "用户不存在!");
		} else if (!userService.checkUserStatus(user)) {
			this.renderJson(true, "用户为锁定状态，请联系管理员!");
		} else if (!userService.checkPasswd(user, password)) {
			this.renderJson(true, "密码不正确!");
		} else {
			loginAuditService.saveLoginAudit(user, getRequest(), getSession());
			user.setOrganization(organizationService.get(user.getOrganization().getId()));// 取一下organization
			getLoginObject();
			this.renderJson(true, "success");
		}
		return Action.NONE;
	}
}
