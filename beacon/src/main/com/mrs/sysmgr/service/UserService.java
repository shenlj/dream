package com.mrs.sysmgr.service;

import com.mrs.sysmgr.entity.User;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseService;

public interface UserService extends BaseService<User> {
	
	public User getUserByLoginID(String loginID);
	
	public boolean checkPasswd(User user,String passwd);
	
	public boolean checkUserStatus(User user);
	
	public Page<User> getUserByOrgID(Page<User> page, String orgid) ;
	
	public void saveUser(User user) ;
	
	public Page<User> getUserList(Page<User> page, String orgid,String name,String roleid);
	
	public Page<User> getHasUserList(Page<User> page,String roleid);
}
