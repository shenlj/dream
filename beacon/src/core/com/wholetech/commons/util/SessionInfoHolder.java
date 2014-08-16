package com.wholetech.commons.util;

import javax.servlet.http.HttpServletRequest;



public interface SessionInfoHolder {
	/**
	 * 判断用户是否已登录。
	 * 
	 * @param request 请求对象。
	 * @return 已登录返回true，否则返回false。
	 */
	public boolean isLogined(HttpServletRequest request);

	/**
	 * 获取当前登录用户的帐号。
	 * 
	 * @param request 请求对象。
	 * @return 当前登录用户的帐号，如果未登录，返回null。
	 */
	public String getLoginId(HttpServletRequest request);

	/**
	 * 获取当前登录用户的名称。
	 * 
	 * @param request 请求对象。
	 * @return 当前登录用户的名称，如果未登录，返回null。
	 */
	public String getLoginName(HttpServletRequest request);

	/**
	 * 获取当前登录用户的流水号。
	 * 
	 * @param request 请求对象。
	 * @return 当前登录用户的流水号，如果未登录，返回null。
	 */
	public String getUserId(HttpServletRequest request);

	/**
	 * 获取当前登录用户的客户端机器IP。
	 * 
	 * @param request 请求对象。
	 * @return 当前登录用户的客户端机器IP，如果未登录，返回null。
	 */
	public String getLoginIP(HttpServletRequest request);

	/**
	 * 获取当前登录用户的登录时刻。
	 * 
	 * @param request 请求对象。
	 * @return 当前登录用户的登录时刻，如果未登录，返回null。
	 */
	public String getLoginTime(HttpServletRequest request);
}