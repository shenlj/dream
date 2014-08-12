package com.beacon.demo.web.action;


import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.beacon.demo.entity.User;
import com.beacon.demo.service.UserService;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.ssh.extend.SpringContextHolder;
import com.wholetech.commons.util.SqlBuilder;
import com.wholetech.commons.util.StringUtil;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

@ParentPackage("default")
@Namespace("/demo")
@Results( { @Result(name = "newUser", value = "/app/sysmgr/user_edit.jsp") }
)
public class UserAction extends BaseAction<User> {
	/**
     * 说明：
     */
    private static final long serialVersionUID = 1762728679541747843L;
	private UserService userService;
	
    public UserService getUserService() {
    
    	return userService;
    }
	
    public void setUserService(UserService userService) {
    
    	this.userService = userService;
    }
	
	@Override
	protected BaseService<User> getBaseService() {

		return userService;
	}
	public String  checkLogin(){
		userService = (UserService)SpringContextHolder.getBean("userService");
		String loginid = this.getParameter("loginID");
		String password = this.getParameter("passwd");
		User user = this.userService.getUserByLoginID(loginid);
		if(user==null){
			this.renderJson(false,"用户不存在!");
		}else if(!this.userService.checkUserStatus(user)){
			this.renderJson(false,"用户为锁定状态，请联系管理员!");
		}else if(!this.userService.checkPasswd(user, password)){
			this.renderJson(false,"密码不正确!");
		}else {
			this.renderJson(true,"success");			
		}
		return NONE;
	}
	public String getUserList(){
		String username = this.getParameter("username");
		if(StringUtil.isEmpty(username)){
			username = SqlBuilder.IGNORE_FILTER;
		}
		else{
			username = "%"+username+"%";
		} 
		this.userService.getUserList(page, username);
		renderText(JSONResult.page2Json(this.page, this.scolumns));
		return NONE;
	}
}
