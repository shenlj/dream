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

	public void setUserDao(final UserDao userDao) {

		this.userDao = userDao;
	}

	@Override
	protected BaseDao<User> getBaseDao() {

		return userDao;
	}

	@Override
	public User getUserByLoginID(final String loginID) {

		userDao.getSystemDate();
		final User usr = (User) userDao.findOneByHql("hql_sysmgr_getUserByLoginID", loginID);
		return usr;
	}

	@Override
	public boolean checkPasswd(final User user, final String passwd) {

		if (user.getPasswd().equals(MD5Util.getMD5(passwd.getBytes()))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkUserStatus(final User user) {

		if (user.getStatus() != null && User.STATUS_NORMAL.equals(user.getStatus())) {
			return true;
		}
		return false;
	}

	@Override
	public void saveUser(final User user) {

		userDao.saveOrUpdate(user);
	}

	public void addUserToSession() {

	}

	@Override
	public Page<User> getUserByOrgID(final Page<User> page, final String orgid) {

		return (Page<User>) userDao.findPageByHql(page, "hql_sys_getUserByOrgID", orgid, SqlBuilder.IGNORE_FILTER);
	}

	@Override
	public Page<User> getHasUserList(final Page<User> page, final String roleid) {

		final Page<User> pagex = (Page<User>) userDao.findPageByHql(page, "hql_sys_getHasUserList", roleid);
		return pagex;
	}

	@Override
	public Page<User> getUserList(final Page<User> page, final String orgid, final String name, final String roleid) {

		// Page<User> pagex = (Page<User>) this.userDao.findPageByHql(page,
		// "from User us where us.status='1' and us.id not in (select user.id from User as user left join user.roles as r where r.id=?)  and us.organization.id = ?  order by us.username",roleid,"2");
		final Page<User> pagex = (Page<User>) userDao.findPageByHql(page, "hql_sys_getUserList", roleid, orgid, name);
		return pagex;
	}

}
