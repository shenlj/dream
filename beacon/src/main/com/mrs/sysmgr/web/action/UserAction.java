package com.mrs.sysmgr.web.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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
import com.wholetech.commons.util.MD5Util;
import com.wholetech.commons.util.SqlBuilder;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results({ @Result(name = "newUser", value = "/app/sysmgr/user_edit.jsp") })
public class UserAction extends BaseAction<User> {

	private UserService userService;
	private RoleService roleService;
	private PermissionService permissionService;

	public void setUserService(final UserService userService) {

		this.userService = userService;
	}

	public void setRoleService(final RoleService roleService) {

		this.roleService = roleService;
	}

	public void setPermissionService(final PermissionService permissionService) {

		this.permissionService = permissionService;
	}

	@Override
	protected BaseService getBaseService() {

		// TODO Auto-generated method stub
		return userService;
	}

	public String getUserList() {

		String orgid = getParameter("orgid");
		String username = getParameter("username");
		String roleid = getParameter("roleid");

		if (StringUtils.isEmpty(orgid)) {
			orgid = SqlBuilder.IGNORE_FILTER;
		}
		if (StringUtils.isEmpty(roleid)) {
			roleid = SqlBuilder.IGNORE_FILTER;
		}
		if (StringUtils.isEmpty(username)) {
			username = SqlBuilder.IGNORE_FILTER;
		}
		else {
			username = "%" + username + "%";
		}

		userService.getUserList(this.page, orgid, username, roleid);
		final List<User> userList = page.getData();
		if (userList != null) {
			for (final User ur : userList) {
				ur.setOrgname(ur.getOrganization().getOrgName());
			}
		}
		renderText(JSONResult.page2Json(page, scolumns));
		return Action.NONE;
	}

	public String getHasUserList() {

		final String roleid = getParameter("roleid");
		userService.getHasUserList(this.page, roleid);
		final List<User> userList = page.getData();
		if (userList != null) {
			for (final User ur : userList) {
				ur.setOrgname(ur.getOrganization().getOrgName());
			}
		}
		renderText(JSONResult.page2Json(page, scolumns));
		return Action.NONE;

	}

	public String getUserByOrgID() {

		final String orgid = getRequest().getParameter("orgid");
		if (StringUtils.isNotEmpty(orgid)) {
			userService.getUserByOrgID(this.page, orgid);
		}
		renderText(JSONResult.page2Json(page, scolumns));
		return Action.NONE;
	}

	public String changeStatus() {

		final String id = getRequest().getParameter("id");
		final String status = getRequest().getParameter("status");
		try {
			final User user = userService.get(id);
			if (user != null && status.equals(user.getStatus())) {
				user.setStatus(status.equals("1") ? "0" : "1");
				userService.update(user);
			}
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××用户删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return Action.NONE;
	}

	public String deleteUser() {

		final String id = getRequest().getParameter("id");
		try {
			final User user = userService.get(id);
			userService.remove(user);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××用户删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return Action.NONE;
	}

	public void prepareSaveUser() throws Exception {

		prepareModel();
	}

	public String saveUser() {

		try {
			final User user = entityForm;
			user.setPasswd(MD5Util.getMD5(user.getPasswd().getBytes()));
			userService.saveUser(entityForm);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××用户保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return Action.NONE;
	}

	public String loadUser() {

		try {
			prepareLoad();
			getRequest().setAttribute("entity", entityForm);
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "newUser";
	}

	public String checkLoginid() {

		final String loginid = getRequest().getParameter("loginid");
		try {
			final List list = userService.findListByHql("hql_sysmgr_getUserByLoginID", loginid);
			if (list.size() > 0) {
				this.renderJson(true, "true");
			}
			else {
				this.renderJson(true, "false");
			}
		} catch (final Exception e) {
			logger.error("×××教养失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return Action.NONE;
	}

	public String getUserRole() {

		final String id = getParameter("id");
		final User user = userService.get(id);
		renderText(JSONResult.list2Json(user.getRoles(), "id,name").toString());
		return Action.NONE;
	}

	public String getUserPermission() {

		final String id = getParameter("id");
		final User user = userService.get(id);
		renderText(JSONResult.list2Json(user.getPermissions(), "id,name").toString());
		return Action.NONE;
	}

	public String saveUserRole() {

		final String id = getParameter("id");
		final String roles = getParameter("roles");
		try {
			final User usr = userService.get(id);
			final Set<Role> roleList = new HashSet();
			final String[] rolesAr = roles.split(",");
			for (final String roleid : rolesAr) {
				roleList.add(roleService.get(roleid));
			}
			usr.setRoles(roleList);
			userService.update(usr);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			this.renderJson(false, "设置用户角色失败!");
		}
		return Action.NONE;
	}

	public String saveUserPermission() {

		final String id = getParameter("id");
		final String permissions = getParameter("permissions");
		try {
			final User usr = userService.get(id);
			final Set<Permission> permissionList = new HashSet();
			final String[] permissionAr = permissions.split(",");
			for (final String permissionid : permissionAr) {
				permissionList.add(permissionService.get(permissionid));
			}
			usr.setPermissions(permissionList);
			userService.update(usr);
			this.renderJson(true, "success");
		} catch (final Exception e) {
			this.renderJson(false, "设置用户角色失败!");
		}
		return Action.NONE;
	}
}
