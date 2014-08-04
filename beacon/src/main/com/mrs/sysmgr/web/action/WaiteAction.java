package com.mrs.sysmgr.web.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.service.WaiteListService;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.web.BaseAction;

@ParentPackage("default")
@Namespace("/sysmgr")
@Results({ @Result(name = "waiteInfoList", value = "/app/waiteList.jsp") }

		)
public class WaiteAction extends BaseAction {

	@Override
	protected BaseService getBaseService() {

		// TODO Auto-generated method stub
		return waiteListService;
	}

	private WaiteListService waiteListService;

	public WaiteListService getWaiteListService() {

		return this.waiteListService;
	}

	public void setWaiteListService(final WaiteListService waiteListService) {

		this.waiteListService = waiteListService;
	}

	public String getWaiteList() {

		final List<Map> rsList = waiteListService.getWaiteList();
		getRequest().setAttribute("list", rsList);
		return "waiteInfoList";
	}

}
