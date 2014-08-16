package com.beacon.demo.service;

import com.beacon.demo.entity.User;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseService;

public interface UserService extends BaseService<User> {
	
	public User getUserByLoginID(String loginID);
	
	public boolean checkPasswd(User user,String passwd);
	
	public boolean checkUserStatus(User user);
	
	public void saveUser(User user) ;

	public Page<User> getUserList(Page<User> page, String name);
}
