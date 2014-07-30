package com.mrs.sysmgr.web.action;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.LoginAuditService;
import com.mrs.sysmgr.service.OrganizationService;
import com.mrs.sysmgr.service.UserService;
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

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setLoginAuditService(LoginAuditService loginAuditService) {
		this.loginAuditService = loginAuditService;
	}
	
	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Override
	protected BaseService getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String saveLogout(){
		
		if(this.getSession()!=null){
			this.loginAuditService.savelogout(this.getSession());
		}
		return NONE;
	}
	
	public String saveLogin() {
		
		String loginid = this.getParameter("loginID");
		String password = this.getParameter("passwd");
		
		User user = this.userService.getUserByLoginID(loginid);
		
		if(user==null)
			this.renderJson(true,"用户不存在!");
		else if(!this.userService.checkUserStatus(user))
			this.renderJson(true,"用户为锁定状态，请联系管理员!");
		else if(!this.userService.checkPasswd(user, password))
			this.renderJson(true,"密码不正确!");
		else {
			this.loginAuditService.saveLoginAudit(user,this.getRequest(),this.getSession());
			user.setOrganization(this.organizationService.get(user.getOrganization().getId()));//取一下organization
			User user1 = (User)this.getLoginObject();
			this.renderJson(true,"success");			
		}
		return NONE;
	}
}
