package com.wholetech.commons.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 该类负责从session中获取一些存储的信息。 比如：
 * 操作员登录成功之后，操作员信息会放到session中，这个类可以方便的从session中获取这些信息。
 */
public interface SessionInfoHolder {

	/**
	 * 判断用户是否已登录。
	 *
	 * @param request
	 *            请求对象。
	 * @return 已登录返回true，否则返回false。
	 */
	public boolean isLogined(HttpServletRequest request);

	/**
	 * 获取当前登录用户的帐号。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的帐号，如果未登录，返回null。
	 */
	public String getLoginId(HttpServletRequest request);

	/**
	 * 获取当前登录用户的名称。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的名称，如果未登录，返回null。
	 */
	public String getLoginName(HttpServletRequest request);

	/**
	 * 获取当前登录用户的流水号。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的流水号，如果未登录，返回null。
	 */
	public String getUserId(HttpServletRequest request);

	/**
	 * 获取当前登录用户的客户端机器IP。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的客户端机器IP，如果未登录，返回null。
	 */
	public String getLoginIP(HttpServletRequest request);

	/**
	 * 获取当前登录用户的登录时刻。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的登录时刻，如果未登录，返回null。
	 */
	public String getLoginTime(HttpServletRequest request);

	/**
	 * 获取当前登录用户的组织机构代码。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的组织机构代码，如果未登录，返回null。
	 */
	public String getOrgCode(HttpServletRequest request);

	/**
	 * 获取当前登录用户的组织机构名称。
	 * 
	 * @param request
	 * @return 组织机构名称。
	 */
	public String getOrgName(HttpServletRequest request);

	/**
	 * 获取当前登录用户的组织机构path。
	 * 比如 root.a.b
	 * 
	 * @param request
	 * @return 组织机构名称。
	 */
	public String getOrgPath(HttpServletRequest request);

	/**
	 * 获取当前登录用户的电子邮件。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的电子邮件，如果未登录，返回null。
	 */
	public String getEmail(HttpServletRequest request);

	/**
	 * 获取当前登录用户的移动电话。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的移动电话，如果未登录，返回null。
	 */
	public String getMobilePhone(HttpServletRequest request);

	/**
	 * 获取当前登录用户的密码（登录时输入的明码）。
	 *
	 * @param request
	 *            请求对象。
	 * @return 当前登录用户的密码（登录时输入的明码），如果未登录，返回null。
	 */
	public String getPasswd(HttpServletRequest request);
}
