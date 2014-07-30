package com.mrs.sysmgr.web.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.Organization;
import com.mrs.sysmgr.service.OrganizationService;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.util.TreeUtil;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;
import com.wholetech.commons.util.StringUtil;
@ParentPackage("default")
@Namespace("/sysmgr")
@Results( { @Result(name = "orgView", value = "/app/sysmgr/orgBasic.jsp") })
public class OrganizationAction extends BaseAction<Organization> {

	private OrganizationService organizationService;

	public void setOrganizationService(OrganizationService organizationService) {
		this.organizationService = organizationService;
	}

	@Override
	protected BaseService getBaseService() {
		// TODO Auto-generated method stub
		return this.organizationService;
	}

	public void getOrgTreeRoots() {

		List<Object[]> rsList = (List<Object[]>) this.organizationService.findListBySql("sql_sys_getOrgTreeRoots");
		JSONArray jsArray = JSONResult.list2Json(TreeUtil.changeTreeData(rsList), "id,name,isParent");
		renderText(jsArray.toString());
	}

	public void getOrgChildrens() {

		String pid = getParameter("id");
		List<Object[]> rsList = (List<Object[]>) this.organizationService.findListBySql("sql_sys_getOrgChildrens",pid);
		JSONArray jsArray = JSONResult.list2Json(TreeUtil.changeTreeData(rsList), "id,name,pId,isParent");
		renderText(jsArray.toString());
	}
	
	public void getOrgByID(){
		
		String id = getParameter("id");
		Organization  organization= this.organizationService.get(id);
		if(organization!=null){
			List<Organization> list = new ArrayList();
			list.add(organization);
			String m = JSONResult.list2Json(list, "orgCode,orgName,orgLelvl,managername,address,treePath,status,contactname,contactphone,respon").toString();
			renderText(m);
		}
	}
	
	public void prepareSaveOrg() throws Exception {

		prepareModel();
	}
	
	public String delOrg() {

		try {
			String id = this.getRequest().getParameter("id");
			this.organizationService.remove(this.organizationService.get(id));
			this.renderJson(true,"success");
		} catch (Exception e) {
			this.logger.error("×××机构删除失败：×××" + e.toString());
			this.renderJson(false, "删除失败");
		}
		return NONE;

	}
	
	public String saveOrg(){
		try {
			Organization org = this.entityForm;
			String fatherID = this.getRequest().getParameter("fatherID");
			if(StringUtil.isNotEmpty(org.getId())){
				this.organizationService.update(org);
			}else{
				if(StringUtil.isNotEmpty(fatherID)){
					Organization parentOrg = this.organizationService.get(fatherID);
					org.setFather(parentOrg);
				}
				this.organizationService.create(org);
			}
			this.renderJson(true,org.getId());
		} catch (Exception e) {
			this.logger.error("×××机构保存失败：×××" + e.toString());
			this.renderJson(false, "保存失败");
		}
		return NONE;
	}

}
