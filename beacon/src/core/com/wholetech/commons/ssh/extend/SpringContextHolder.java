package com.wholetech.commons.ssh.extend;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
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

	@Override
	public void contextInitialized(final ServletContextEvent event) {

		super.contextInitialized(event);
		final ServletContext context = event.getServletContext();
		SpringContextHolder.ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
	}

	/**
	 * 获取spring的ApplicationContext。
	 */
	public static ApplicationContext getContext() {

		return SpringContextHolder.ctx;
	}

	public static Object getBean(final String name) {

		return SpringContextHolder.ctx.getBean(name);
	}

	public static void registerService(final Class<?> impCls, final String name) {

		final RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClassName(impCls.getName());// 实现类的名称
		beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON); // 默认为true, 需要增加可配置性
		beanDefinition.setAutowireMode(AutowireCapableBeanFactory.AUTOWIRE_BY_NAME);

		((DefaultListableBeanFactory) ((AbstractRefreshableWebApplicationContext) SpringContextHolder.ctx)
				.getBeanFactory()).registerBeanDefinition(name, beanDefinition);

	}
}
