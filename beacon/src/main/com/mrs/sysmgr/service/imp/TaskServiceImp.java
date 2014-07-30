package com.mrs.sysmgr.service.imp;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.service.BaseServiceImp;
import com.mrs.sysmgr.dao.TaskDao;
import com.mrs.sysmgr.entity.Task;
import com.mrs.sysmgr.service.TaskService;

/**
 * @author yourname here
 * ===============================================================================
 * 修改记录（如果这个文件不是你创建的，要修改的话请务必写下修改记录）：
 * 修改作者        日期                    修改内容
 *
 * ===============================================================================
 */
public class TaskServiceImp extends BaseServiceImp<Task> implements TaskService {

	private TaskDao taskDao;
	
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	
	@Override
	protected BaseDao<Task> getBaseDao() {
		return this.taskDao;
	}
	
}
