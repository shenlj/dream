package com.wholetech.commons.web;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 用来获取系统时间，当执行完毕后，要清空当前线程记录的系统时间。
 * 
 */
public class SystemDateFilter implements Filter {

	// 记录当前线程的系统时间。
	private static ThreadLocal<Date> systemDate = new ThreadLocal<Date>();
	
	// 如果是后台任务，systemDate被设置之后是不会清除掉的，所以需要用fromBrowser判断一下，
	// 如果当前线程不是来自于浏览器请求，则不能设置系统时间。
	private static ThreadLocal<Boolean> fromBrowser = new ThreadLocal<Boolean>();
	
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		fromBrowser.set(true);
		chain.doFilter(request, response);
		// 执行完毕后，
		systemDate.set(null);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// 
	}
	
	public static void setSystemDate(Date sysdate) {
		if (fromBrowser.get() != null) {
			systemDate.set(sysdate);
		}
	}
	
	public static Date getSystemDate() {
		return systemDate.get();
	}

}
