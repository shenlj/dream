package com.mrs.sysmgr.service.imp;

import com.mrs.sysmgr.dao.UserDao;
import com.mrs.sysmgr.entity.User;
import com.mrs.sysmgr.service.UserService;
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

	public void addUserToSession() {

	}

	public Page<User> getUserByOrgID(Page<User> page, String orgid) {

		return (Page<User>) this.userDao.findPageByHql(page, "hql_sys_getUserByOrgID",orgid,SqlBuilder.IGNORE_FILTER);
	}
	
	public Page<User> getHasUserList(Page<User> page,String roleid) {
		Page<User> pagex =  (Page<User>) this.userDao.findPageByHql(page, "hql_sys_getHasUserList",roleid);
		return pagex;
	}
	
	public Page<User> getUserList(Page<User> page, String orgid,String name,String roleid) {
		
		//Page<User> pagex =  (Page<User>) this.userDao.findPageByHql(page, "from User us where us.status='1' and us.id not in (select user.id from User as user left join user.roles as r where r.id=?)  and us.organization.id = ?  order by us.username",roleid,"2");
		Page<User> pagex =  (Page<User>) this.userDao.findPageByHql(page, "hql_sys_getUserList",roleid,orgid,name);
		return pagex;
	}

}
