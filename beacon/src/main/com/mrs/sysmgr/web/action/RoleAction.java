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
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results( { @Result(name = "newUser", value = "/app/sysmgr/role_edit.jsp") }
)
public class RoleAction extends BaseAction<Role> {
	
	private RoleService roleService;
	private UserService userService;
	private PermissionService permissionService;
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	@Override
	protected BaseService getBaseService() {
		// TODO Auto-generated method stub
		return this.roleService;
	}
	
	public String checkBnPermission(){
		
		String code = this.getParameter("bnCode");
		User usr = (User)this.getLoginObject();
		
		//有权限
		if(code.equals("role_save2")){
			this.renderJson(true, "true");
		}else {
			this.renderJson(true, "false");
		}
		return NONE;
	}
	
	public String getRolePermission(){
		
		String id = this.getParameter("id");
		Role role  = this.roleService.get(id);
		renderText(JSONResult.list2Json(role.getPermissions(), "id,name").toString());
		return NONE;
	}
	
	
	public String addRoleUser(){
		
		String id = this.getRequest().getParameter("id");
		String persons = this.getRequest().getParameter("persons");
		try {
			Role role = this.roleService.get(id);
			Set allUser = role.getUsers();
			for(String userid:persons.split(",")){
				User u = this.userService.get(userid);
				allUser.add(u);
			}
			role.setUsers(allUser);
			this.roleService.update(role);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××给角色增加用户失败：×××" + e.toString());
			this.renderJson(false, "给角色增加用户失败");
		}
		return NONE;
	}
	
	public String getListWithPermission(){
		
		String permissionId = this.getParameter("permissionId");
		Permission permission  = this.permissionService.get(permissionId);
		List<Role> rolelist  = this.roleService.getAll();
		rolelist.removeAll(permission.getRoles());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return NONE;
	}
	
	public String getRole() {
		
		this.roleService.getRolePage(page);
		renderText(JSONResult.page2Json(this.page, this.scolumns));
		return NONE;
	}
	
	
	public String getList(){
		
		String id = this.getParameter("id");
		User user  = this.userService.get(id);
		List<Role> rolelist  = this.roleService.getAll();
		rolelist.removeAll(user.getRoles());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return NONE;
	}
	
	public void prepareSaveRole() throws Exception {

		prepareModel();
	}
	
	public String loadRole() {
		try {
			this.prepareLoad();
			getRequest().setAttribute("entity", this.entityForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "newUser";
	}
	
	
	public  String changetRoleStatic(){
		
		String id = this.getRequest().getParameter("id");
		String status = this.getRequest().getParameter("status");
		try {
			Role role = this.roleService.get(id);
			if (role != null && status.equals(role.getStatus())) {
				role.setStatus(status.equals("1") ? "0" : "1");
				this.roleService.update(role);
			}
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××角色更改状态失败：×××" + e.toString());
			this.renderJson(false, "角色更改状态");
		}
		return NONE;
	}
	
	public String saveRole(){
		
		try {
			Role role = (Role)this.entityForm;			
			this.roleService.saveRole(this.entityForm);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××用户保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return NONE;
	}
	
	public String saveRolePermission(){
		
		String id = this.getParameter("id");
		String permissions = this.getParameter("permissions");
		try {
			Role role = this.roleService.get(id);
			Set<Permission> permissionList = new HashSet();
			String[] permissionAr = permissions.split(",");
			for(String permissionid:permissionAr){
				permissionList.add(this.permissionService.get(permissionid));
			}
			role.setPermissions(permissionList);
			this.roleService.update(role);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.renderJson(false, "设置用户角色失败!");
		}
		return NONE;
	}

}
