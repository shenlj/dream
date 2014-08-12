package com.beacon.demo.service.imp;

import com.beacon.demo.dao.UserDao;
import com.beacon.demo.entity.User;
import com.beacon.demo.service.UserService;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.service.BaseServiceImp;
import com.wholetech.commons.util.MD5Util;
import com.wholetech.commons.util.SqlBuilder;


public class UserServiceImp extends BaseServiceImp<User> implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	protected BaseDao<User> getBaseDao() {

		return this.userDao;
	}

	public User getUserByLoginID(String loginID) {

		this.userDao.getSystemDate();
		User usr = (User) this.userDao.findOneByHql("hql_sysmgr_getUserByLoginID", loginID);
		return usr;
	}

	public boolean checkPasswd(User user, String passwd) {

		if (user.getPasswd().equals(MD5Util.getMD5(passwd.getBytes())))
			return true;
		else
			return false;
	}
	
	public boolean checkUserStatus(User user) {

		if (user.getStatus() != null && User.STATUS_NORMAL.equals(user.getStatus())) {
			return true;
		}
		return false;
	}
	
	public void saveUser(User user) {

	    this.userDao.saveOrUpdate(user);
	}
	
	public Page<User> getUserList(Page<User> page,String name) {
		
		Page<User> pagex =  (Page<User>) this.userDao.findPageByHql(page, "hql_sys_getUserList",name);
		return pagex;
	}
}
