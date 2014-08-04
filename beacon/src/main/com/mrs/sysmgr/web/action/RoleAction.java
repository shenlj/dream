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
@Results({ @Result(name = "newUser", value = "/app/sysmgr/role_edit.jsp") })
public class RoleAction extends BaseAction<Role> {

	private RoleService roleService;
	private UserService userService;
	private PermissionService permissionService;

	public void setRoleService(final RoleService roleService) {

		this.roleService = roleService;
	}

	public void setUserService(final UserService userService) {

		this.userService = userService;
	}

	public void setPermissionService(final PermissionService permissionService) {

		this.permissionService = permissionService;
	}

	@Override
	protected BaseService getBaseService() {

		// TODO Auto-generated method stub
		return roleService;
	}

	public String checkBnPermission() {

		final String code = getParameter("bnCode");
		getLoginObject();

		// 有权限
		if (code.equals("role_save2")) {
			this.renderJson(true, "true");
		} else {
			this.renderJson(true, "false");
		}
		return Action.NONE;
	}

	public String getRolePermission() {

		final String id = getParameter("id");
		final Role role = roleService.get(id);
		renderText(JSONResult.list2Json(role.getPermissions(), "id,name").toString());
		return Action.NONE;
	}

	public String addRoleUser() {

		final String id = getRequest().getParameter("id");
		final String persons = getRequest().getParameter("persons");
		try {
			final Role role = roleService.get(id);
			final Set allUser = role.getUsers();
			for (final String userid : persons.split(",")) {
				final User u = userService.get(userid);
				allUser.add(u);
			}
			role.setUsers(allUser);
			roleService.update(role);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××给角色增加用户失败：×××" + e.toString());
			this.renderJson(false, "给角色增加用户失败");
		}
		return Action.NONE;
	}

	public String getListWithPermission() {

		final String permissionId = getParameter("permissionId");
		final Permission permission = permissionService.get(permissionId);
		final List<Role> rolelist = roleService.getAll();
		rolelist.removeAll(permission.getRoles());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return Action.NONE;
	}

	public String getRole() {

		roleService.getRolePage(this.page);
		renderText(JSONResult.page2Json(page, scolumns));
		return Action.NONE;
	}

	public String getList() {

		final String id = getParameter("id");
		final User user = userService.get(id);
		final List<Role> rolelist = roleService.getAll();
		rolelist.removeAll(user.getRoles());
		renderText(JSONResult.list2Json(rolelist, "id,name").toString());
		return Action.NONE;
	}

	public void prepareSaveRole() throws Exception {

		prepareModel();
	}

	public String loadRole() {

		try {
			prepareLoad();
			getRequest().setAttribute("entity", entityForm);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "newUser";
	}

	public String changetRoleStatic() {

		final String id = getRequest().getParameter("id");
		final String status = getRequest().getParameter("status");
		try {
			final Role role = roleService.get(id);
			if (role != null && status.equals(role.getStatus())) {
				role.setStatus(status.equals("1") ? "0" : "1");
				roleService.update(role);
			}
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××角色更改状态失败：×××" + e.toString());
			this.renderJson(false, "角色更改状态");
		}
		return Action.NONE;
	}

	public String saveRole() {

		try {
			roleService.saveRole(entityForm);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××用户保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return Action.NONE;
	}

	public String saveRolePermission() {

		final String id = getParameter("id");
		final String permissions = getParameter("permissions");
		try {
			final Role role = roleService.get(id);
			final Set<Permission> permissionList = new HashSet();
			final String[] permissionAr = permissions.split(",");
			for (final String permissionid : permissionAr) {
				permissionList.add(permissionService.get(permissionid));
			}
			role.setPermissions(permissionList);
			roleService.update(role);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			this.renderJson(false, "设置用户角色失败!");
		}
		return Action.NONE;
	}

}
