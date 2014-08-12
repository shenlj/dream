package com.wholetech.commons.ssh.extend;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AbstractRefreshableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 获取spring的ApplicationContext。通过ApplicationContext可以获取在spring配置文件中配置的类。
 */
public class SpringContextHolder extends ContextLoaderListener {
	
	private static ApplicationContext ctx = null;
	
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}
	
	/**
	 * 获取spring的ApplicationContext。
	 */
	public static ApplicationContext getContext(){
		return ctx; 
	}
	
	public static Object getBean(String name) {
		return ctx.getBean(name);
	}
	
	public static void registerService(Class<?> impCls, String name) {
		
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClassName(impCls.getName());// 实现类的名称
		beanDefinition.setScope(RootBeanDefinition.SCOPE_SINGLETON); // 默认为true, 需要增加可配置性
		beanDefinition.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);
	
		
		((DefaultListableBeanFactory) ((AbstractRefreshableWebApplicationContext) ctx)
				.getBeanFactory()).registerBeanDefinition(name, beanDefinition);

	}
}
