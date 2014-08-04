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
import com.opensymphony.xwork2.Action;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results({ @Result(name = "newUser", value = "/app/sysmgr/permission_edit.jsp") })
public class PermissionAction extends BaseAction<Permission> {

	private PermissionService permissionService;
	private UserService userService;
	private RoleService roleService;

	public void setPermissionService(final PermissionService permissionService) {

		this.permissionService = permissionService;
	}

	public void setUserService(final UserService userService) {

		this.userService = userService;
	}

	public void setRoleService(final RoleService roleService) {

		this.roleService = roleService;
	}

	@Override
	protected BaseService getBaseService() {

		// TODO Auto-generated method stub
		return permissionService;
	}

	public String getPermission() {

		permissionService.getPermissionPage(this.page);
		renderText(JSONResult.page2Json(page, scolumns));
		return Action.NONE;
	}

	public void prepareSavePermission() throws Exception {

		prepareModel();
	}

	public String loadPermission() {

		try {
			prepareLoad();
			getRequest().setAttribute("entity", entityForm);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "newUser";
	}

	public String savePermissionRole() {

		final String id = getParameter("id");
		final String roles = getParameter("roles");
		try {
			final Permission permission = permissionService.get(id);
			final Set<Role> roleList = new HashSet();
			final String[] roleAr = roles.split(",");
			for (final String roleId : roleAr) {
				roleList.add(roleService.get(roleId));
			}
			permission.setRoles(roleList);
			permissionService.update(permission);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			this.renderJson(false, "设置权限角色失败!");
		}
		return Action.NONE;
	}

	public String getPermissionRole() {

		final String id = getParameter("id");
		final Permission permission = permissionService.get(id);
		renderText(JSONResult.list2Json(permission.getRoles(), "id,name").toString());
		return Action.NONE;
	}

	public String deletePermission() {

		final String id = getRequest().getParameter("id");
		try {
			final Permission permission = permissionService.get(id);
			permissionService.remove(permission);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××用户删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return Action.NONE;
	}

	public String changeStatus() {

		final String id = getRequest().getParameter("id");
		final String status = getRequest().getParameter("status");
		try {
			final Permission permission = permissionService.get(id);
			if (permission != null && status.equals(permission.getStatus())) {
				permission.setStatus(status.equals("1") ? "0" : "1");
				permissionService.update(permission);
			}
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××角色更改状态失败：×××" + e.toString());
			this.renderJson(false, "角色更改状态");
		}
		return Action.NONE;
	}

	public String savePermission() {

		try {
			final Permission permission = entityForm;
			permissionService.saveRole(permission);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××权限保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return Action.NONE;
	}

	public String getListWithRole() {

		final String roleid = getParameter("roleid");
		final Role role = roleService.get(roleid);
		final List<Permission> rolelist = permissionService.getAll();
		rolelist.removeAll(role.getPermissions());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return Action.NONE;
	}

	public String getList() {

		final String id = getParameter("id");
		final User user = userService.get(id);
		final List<Permission> rolelist = permissionService.getAll();
		rolelist.removeAll(user.getPermissions());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return Action.NONE;
	}
}
