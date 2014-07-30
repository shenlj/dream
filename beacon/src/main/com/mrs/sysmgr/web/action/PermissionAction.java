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
@Results( { @Result(name = "newUser", value = "/app/sysmgr/permission_edit.jsp") }
)

public class PermissionAction extends BaseAction<Permission> {

	private PermissionService  permissionService;
	private UserService userService;
	private RoleService roleService;
	
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Override
	protected BaseService getBaseService() {
		// TODO Auto-generated method stub
		return this.permissionService;
	}
	
	public String getPermission() {
		
		this.permissionService.getPermissionPage(page);
		renderText(JSONResult.page2Json(this.page, this.scolumns));
		return NONE;
	}
	
	public void prepareSavePermission() throws Exception {

		prepareModel();
	}
	
	public String loadPermission() {
		try {
			this.prepareLoad();
			getRequest().setAttribute("entity", this.entityForm);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "newUser";
	}
	
	
	public String savePermissionRole(){
		
		String id = this.getParameter("id");
		String roles = this.getParameter("roles");
		try {
			Permission permission = this.permissionService.get(id);
			Set<Role> roleList = new HashSet();
			String[] roleAr = roles.split(",");
			for(String roleId:roleAr){
				roleList.add(this.roleService.get(roleId));
			}
			permission.setRoles(roleList);
			this.permissionService.update(permission);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.renderJson(false, "设置权限角色失败!");
		}
		return NONE;
	}
	
	public String getPermissionRole(){
		
		String id = this.getParameter("id");
		Permission permission  = this.permissionService.get(id);
		renderText(JSONResult.list2Json(permission.getRoles(), "id,name").toString());
		return NONE;
	}
	
	public String deletePermission(){
		
		String id = this.getRequest().getParameter("id");
		try {
			Permission permission = this.permissionService.get(id);
			this.permissionService.remove(permission);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××用户删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return NONE;
	}
	
	public String changeStatus(){
		
		String id = this.getRequest().getParameter("id");
		String status = this.getRequest().getParameter("status");
		try {
			Permission permission = this.permissionService.get(id);
			if (permission != null && status.equals(permission.getStatus())) {
				permission.setStatus(status.equals("1") ? "0" : "1");
				this.permissionService.update(permission);
			}
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××角色更改状态失败：×××" + e.toString());
			this.renderJson(false, "角色更改状态");
		}
		return NONE;
	}
	
	public String savePermission(){
		
		try {
			Permission permission = (Permission)this.entityForm;			
			this.permissionService.saveRole(permission);
			this.renderJson(true, "success");
		} catch (Exception e) {
			this.logger.error("×××权限保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return NONE;
	}
	
	
	public String getListWithRole(){
		
		String roleid = this.getParameter("roleid");
		Role role  = this.roleService.get(roleid);
		List<Permission> rolelist  = this.permissionService.getAll();
		rolelist.removeAll(role.getPermissions());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return NONE;
	}
	
	public String getList(){
		
		String id = this.getParameter("id");
		User user  = this.userService.get(id);
		List<Permission> rolelist  = this.permissionService.getAll();
		rolelist.removeAll(user.getPermissions());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return NONE;
	}
}
