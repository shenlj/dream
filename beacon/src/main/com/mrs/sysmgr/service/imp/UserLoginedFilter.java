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
	
	private FilterConfig filterConfig;

    private FilterChain chain;

    private HttpServletRequest request;

    private HttpServletResponse response;

    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
    }

    public void destroy() {

        this.filterConfig = null;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

    	this.request = (HttpServletRequest) servletRequest;
        this.response = ((HttpServletResponse) servletResponse);
        String URI = request.getRequestURI();
        this.logger.debug("request URI====" + URI);
        String page = URI.substring(URI.lastIndexOf("/") + 1);
        
        try {
        	HttpSession session = request.getSession();
            // 获取网站访问根目录
            String accessPath = request.getContextPath();
            User user = (User) session
                    .getAttribute(Constants.USER_IN_SESSION);
            if (isExcludePage(page)) {// 不需要判断权限的请求如登录页面，则跳过
                chain.doFilter(request, response);
            }
            else if (user == null) {// 没有登录
                response.sendRedirect(accessPath + "/index.jsp");// 返回登陆页面（未登录或超时）
            }
            else {
                // 判断当前user是否拥有访问此url的权限
                //verifyUrl(url, st);
            }
        }
        catch (Exception sx) {
            sx.printStackTrace();
        }
    }
    
    private final String[] excludeUrl = { "login.jsp", "noright.jsp" };

    protected boolean isExcludePage(String url) {

        for (String ex : excludeUrl) {
            if (ex.indexOf(url) >= 0) {
                return true;
            }
        }
        return false;
    }

    protected boolean noFileUrl(String url, HttpServletRequest request) {

        String exclude = "login.jsp";
        if (exclude.indexOf(url) >= 0) {
            return true;
        }
        return false;
    }

    private void verifyUrl(String url, User st) throws IOException,
            ServletException {

        // 获取user拥有的所有资源串,挨个验证
        String surl = "app/sys/user/role.jsp";

        if (url.indexOf(surl) >= 0) {
            chain.doFilter(request, response);
        }
        else {
            String noPerJsp = "xxxnoPer.jsp";
            response.sendRedirect("xxxnoPer.jsp");
        }
    }
}
