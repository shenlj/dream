package com.mrs.sysmgr.web.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.Permission;
import com.mrs.sysmgr.entity.Role;
import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.PermissionService;
import com.mrs.sysmgr.service.RoleService;
import com.mrs.sysmgr.service.UserService;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.util.MD5Util;
import com.wholetech.commons.util.SqlBuilder;
import com.wholetech.commons.util.StringUtil;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results( { @Result(name = "newUser", value = "/app/sysmgr/user_edit.jsp") }
)
public class UserAction extends BaseAction<User> {

	private UserService userService;
	private RoleService roleService;
	private PermissionService permissionService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@Override
	protected BaseService getBaseService() {
		// TODO Auto-generated method stub
		return this.userService;
	}
	
	
	public String getUserList(){
		
		String orgid  = this.getParameter("orgid");
		String username = this.getParameter("username");
		String roleid = this.getParameter("roleid");
		
		if(StringUtil.isEmpty(orgid)){
			orgid = SqlBuilder.IGNORE_FILTER;
		}
		if(StringUtil.isEmpty(roleid)){
			roleid = SqlBuilder.IGNORE_FILTER;
		}
		if(StringUtil.isEmpty(username)){
			username = SqlBuilder.IGNORE_FILTER;
		}
		else{
			username = "%"+username+"%";
		} 
		
		this.userService.getUserList(page, orgid,username,roleid);
		List<User> userList =  this.page.getData();
		if(userList!=null){
			for(User ur:userList){
				ur.setOrgname(ur.getOrganization().getOrgName());
			}
		}
		renderText(JSONResult.page2Json(this.page, this.scolumns));
		return NONE;
	}
	
	
	public String getHasUserList(){
		
		String roleid = this.getParameter("roleid");
		this.userService.getHasUserList(page,roleid);
		List<User> userList =  this.page.getData();
		if(userList!=null){
			for(User ur:userList){
				ur.setOrgname(ur.getOrganization().getOrgName());
			}
		}
		renderText(JSONResult.page2Json(this.page, this.scolumns));
		return NONE;
		
	}

	public String getUserByOrgID() {

		String orgid = this.getRequest().getParameter("orgid");
		if(StringUtil.isNotEmpty(orgid)){
			this.userService.getUserByOrgID(page, orgid);
		}
		renderText(JSONResult.page2Json(this.page, this.scolumns));
		return NONE;
	}
	
	public String changeStatus() {

		String id = this.getRequest().getParameter("id");
		String status = this.getRequest().getParameter("status");
		try {
			User user = this.userService.get(id);
			if (user != null && status.equals(user.getStatus())) {
				user.setStatus(status.equals("1") ? "0" : "1");
				this.userService.update(user);
			}
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××用户删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return NONE;
	}

	public String deleteUser() {

		String id = this.getRequest().getParameter("id");
		try {
			User user = this.userService.get(id);
			this.userService.remove(user);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××用户删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return NONE;
	}

	public void prepareSaveUser() throws Exception {

		prepareModel();
	}

	public String saveUser() {

		try {
			User user = (User)this.entityForm;
			user.setPasswd(MD5Util.getMD5(user.getPasswd().getBytes()));
			this.userService.saveUser(this.entityForm);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××用户保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return NONE;
	}

	public String loadUser() {
		try {
			this.prepareLoad();
			getRequest().setAttribute("entity", this.entityForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "newUser";
	}
	
	public String checkLoginid(){
		
		String loginid = this.getRequest().getParameter("loginid");
		try {
			List list = this.userService.findListByHql("hql_sysmgr_getUserByLoginID", loginid);
			if(list.size()>0){
				this.renderJson(true, "true");
			}
			else{
				this.renderJson(true, "false");
			}	
		} catch (Exception e) {
			this.logger.error("×××教养失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return NONE;
	}
	
	public String getUserRole(){
		
		String id = this.getParameter("id");
		User user  = this.userService.get(id);
		renderText(JSONResult.list2Json(user.getRoles(), "id,name").toString());
		return NONE;
	}
	
	
	public String getUserPermission(){
		String id = this.getParameter("id");
		User user  = this.userService.get(id);
		renderText(JSONResult.list2Json(user.getPermissions(), "id,name").toString());
		return NONE;
	}
	
	public String saveUserRole(){
		
		String id = this.getParameter("id");
		String roles = this.getParameter("roles");
		try {
			User usr = this.userService.get(id);
			Set<Role> roleList = new HashSet();
			String[] rolesAr = roles.split(",");
			for(String roleid:rolesAr){
				roleList.add(this.roleService.get(roleid));
			}
			usr.setRoles(roleList);
			this.userService.update(usr);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.renderJson(false, "设置用户角色失败!");
		}
		return NONE;
	}
	
	
	public String saveUserPermission(){
		
		String id = this.getParameter("id");
		String permissions = this.getParameter("permissions");
		try {
			User usr = this.userService.get(id);
			Set<Permission> permissionList = new HashSet();
			String[] permissionAr = permissions.split(",");
			for(String permissionid:permissionAr){
				permissionList.add(this.permissionService.get(permissionid));
			}
			usr.setPermissions(permissionList);
			this.userService.update(usr);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.renderJson(false, "设置用户角色失败!");
		}
		return NONE;
	}
}
