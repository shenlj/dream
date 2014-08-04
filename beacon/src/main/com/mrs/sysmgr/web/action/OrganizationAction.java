package com.mrs.sysmgr.web.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.Organization;
import com.mrs.sysmgr.service.OrganizationService;
import com.opensymphony.xwork2.Action;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.util.TreeUtil;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results({ @Result(name = "orgView", value = "/app/sysmgr/orgBasic.jsp") })
public class OrganizationAction extends BaseAction<Organization> {

	private OrganizationService organizationService;

	public void setOrganizationService(final OrganizationService organizationService) {

		this.organizationService = organizationService;
	}

	@Override
	protected BaseService getBaseService() {

		// TODO Auto-generated method stub
		return organizationService;
	}

	public void getOrgTreeRoots() {

		final List<Object[]> rsList = (List<Object[]>) organizationService.findListBySql("sql_sys_getOrgTreeRoots");
		final JSONArray jsArray = JSONResult.list2Json(TreeUtil.changeTreeData(rsList), "id,name,isParent");
		renderText(jsArray.toString());
	}

	public void getOrgChildrens() {

		final String pid = getParameter("id");
		final List<Object[]> rsList = (List<Object[]>) organizationService
				.findListBySql("sql_sys_getOrgChildrens", pid);
		final JSONArray jsArray = JSONResult.list2Json(TreeUtil.changeTreeData(rsList), "id,name,pId,isParent");
		renderText(jsArray.toString());
	}

	public void getOrgByID() {

		final String id = getParameter("id");
		final Organization organization = organizationService.get(id);
		if (organization != null) {
			final List<Organization> list = new ArrayList();
			list.add(organization);
			final String m = JSONResult.list2Json(list,
					"orgCode,orgName,orgLelvl,managername,address,treePath,status,contactname,contactphone,respon")
					.toString();
			renderText(m);
		}
	}

	public void prepareSaveOrg() throws Exception {

		prepareModel();
	}

	public String delOrg() {

		try {
			final String id = getRequest().getParameter("id");
			organizationService.remove(organizationService.get(id));
			this.renderJson(true, "success");
		} catch (final Exception e) {
			logger.error("×××机构删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return Action.NONE;

	}

	public String saveOrg() {

		try {
			final Organization org = entityForm;
			final String fatherID = getRequest().getParameter("fatherID");
			if (StringUtils.isNotEmpty(org.getId())) {
				organizationService.update(org);
			} else {
				if (StringUtils.isNotEmpty(fatherID)) {
					final Organization parentOrg = organizationService.get(fatherID);
					org.setFather(parentOrg);
				}
				organizationService.create(org);
			}
			this.renderJson(true, org.getId());
		} catch (final Exception e) {
			logger.error("×××机构保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return Action.NONE;
	}

}
