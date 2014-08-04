package com.mrs.sysmgr.service.imp;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mrs.sysmgr.entity.User;
import com.wholetech.commons.Constants;

public class UserLoginedFilter extends HttpServlet implements Filter {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private FilterChain chain;

	private HttpServletRequest request;

	private HttpServletResponse response;

	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
			final FilterChain chain) throws IOException, ServletException {

		request = (HttpServletRequest) servletRequest;
		response = ((HttpServletResponse) servletResponse);
		final String URI = this.request.getRequestURI();
		logger.debug("request URI====" + URI);
		final String page = URI.substring(URI.lastIndexOf("/") + 1);

		try {
			final HttpSession session = this.request.getSession();
			// 获取网站访问根目录
			final String accessPath = this.request.getContextPath();
			final User user = (User) session
					.getAttribute(Constants.USER_IN_SESSION);
			if (isExcludePage(page)) {// 不需要判断权限的请求如登录页面，则跳过
				chain.doFilter(this.request, this.response);
			}
			else if (user == null) {// 没有登录
				this.response.sendRedirect(accessPath + "/index.jsp");// 返回登陆页面（未登录或超时）
			}
			else {
				// 判断当前user是否拥有访问此url的权限
				// verifyUrl(url, st);
			}
		} catch (final Exception sx) {
			sx.printStackTrace();
		}
	}

	private final String[] excludeUrl = { "login.jsp", "noright.jsp" };

	protected boolean isExcludePage(final String url) {

		for (final String ex : this.excludeUrl) {
			if (ex.indexOf(url) >= 0) {
				return true;
			}
		}
		return false;
	}

	protected boolean noFileUrl(final String url, final HttpServletRequest request) {

		final String exclude = "login.jsp";
		if (exclude.indexOf(url) >= 0) {
			return true;
		}
		return false;
	}
}
