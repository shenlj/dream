package com.mrs.sysmgr.web.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.mrs.sysmgr.entity.Task;
import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.TaskService;
import com.wholetech.commons.Constants;
import com.wholetech.commons.MRSConstants;
import com.wholetech.commons.service.BaseService;
import com.wholetech.commons.web.BaseAction;
import com.wholetech.commons.web.JSONResult;
@ParentPackage("default")
@Namespace("/sysmgr")
@Results( { @Result(name = "waiteInfoList", value = "/app/waiteList.jsp") }

)
public class TaskAction extends BaseAction<Task> {

	private TaskService taskService;

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@Override
	protected BaseService getBaseService() {
		// TODO Auto-generated method stub
		return this.taskService;
	}
	
	public String getRemindTask(){
		Object o  = this.getSession().getAttribute(Constants.USER_IN_SESSION);
		if(o!=null){
			User u = (User)o;
			List<Task> taskList = (List<Task>)this.taskService.findListByHql("hql_getRemindTask",u.getId());
			renderText(JSONResult.list2Json(taskList, "title,action,param,operateTime").toString());
		}
		else {
			renderText("[]");
		}
		return NONE;
	}
	
	public String saveTaskClick(){
		
		String id = this.getParameter("id");
		Task t = this.taskService.get(id);
		t.setRemind(Task.REMINDED);
		try{
			this.taskService.update(t);
			this.renderJson(true,"success");
		} catch (Exception e) {
			this.renderJson(false, "删除失败");
		}
		return NONE;
	}
	
	
	public String getTaskNumByType(){
		Object o  = this.getSession().getAttribute(Constants.USER_IN_SESSION);
		if(o!=null){
			User u = (User)o;
			List numList = (List<Task>)this.taskService.findListByHql("hql_getTaskNumByType",u.getId());
			Map numMap = new HashMap();
			for(int i =0;i<numList.size();i++){
				Object[] d = (Object[])numList.get(i);
				Long typeNum = (Long)d[0];
				String type = d[1].toString();
				if(type.equals(MRSConstants.TASK_TYPE_WRITE)){//报告编写
					numMap.put("write", typeNum);
				}else if(type.equals(MRSConstants.TASK_TYPE_INDEXFILL)){//数据填报
					numMap.put("indexfill", typeNum);
				}else if(type.equals(MRSConstants.TASK_TYPE_MATERIACOLE)){//素材搜集
					numMap.put("materiacole", typeNum);
				}else if(type.equals(MRSConstants.TASK_TYPE_TASKSH)){//审核任务
					numMap.put("tasksh", typeNum);
				}
			}
			renderText(JSONResult.map2Json(numMap).toString());
		}
		else {
			renderText("[]");
		}
		return NONE;
	}
	
	public String getDealTask(){
		User u = (User)this.getSession().getAttribute(Constants.USER_IN_SESSION);
		if(u!=null){
			this.taskService.getTaskPage(page, u.getId());
			renderText(JSONResult.page2Json(this.page, this.scolumns));
		}else {
			renderText("[]");
		}
		return NONE;
	}
	
}
