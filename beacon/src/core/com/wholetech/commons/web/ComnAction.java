package com.wholetech.commons.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;
import com.wholetech.commons.ssh.extend.DateConverter;

/**
 * 简单封装Struts DispatchAction的基类. 提供一些基本的简化函数,将不断增强.
 */
public class ComnAction extends ActionSupport {

	private static final long serialVersionUID = -42856136017302010L;
	/** 日志管理器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	static {
		ComnAction.registConverter();
	}

	/**
	 * 设置Struts 中数字<->字符串转换，字符串为空值时,数字默认为null，而不是0.
	 * 也可以在web.xml中设置struts的参数达到相同效果，在这里设置可以防止用户漏设web.xml.
	 */
	public static void registConverter() {

		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
		ConvertUtils.register(new LongConverter(null), Long.class);
		ConvertUtils.register(new ByteConverter(null), Byte.class);
		ConvertUtils.register(new DateConverter("yyyy-MM-dd"), Date.class);
	}

	/**
	 * 以属性文件方式保存Action-level消息
	 *
	 * @param key
	 *            配置文件中要获取信息的key
	 * @param values
	 *            要保存的信息
	 */
	protected void saveMessage(final String key, final String... values) {

		addActionMessage(getText(key, values));
	}

	/**
	 * 以文本方式保存Action-level消息
	 *
	 * @param value
	 *            要保存的信息
	 */
	protected void saveMessage(final String value) {

		addActionMessage(value);
	}

	/**
	 * 以属性文件方式保存Action-level错误消息
	 *
	 * @param key
	 *            配置文件中要获取信息的key
	 * @param values
	 *            要保存的信息
	 */
	protected void saveError(final String key, final String... values) {

		addActionError(getText(key, values));
	}

	/**
	 * 以文本方式保存Action-level错误消息
	 *
	 * @param value
	 *            要保存的信息
	 */
	protected void saveError(final String value) {

		addActionError(value);
	}

	/**
	 * 设置错误信息集合
	 *
	 * @param errors
	 *            错误信息集合
	 */
	protected void saveError(final List errors) {

		setActionErrors(errors);
	}

	/**
	 * 直接信息集合
	 *
	 * @param messages
	 *            信息集合
	 */
	protected void saveMessage(final List messages) {

		setActionMessages(messages);
	}

	/**
	 * 以字符串形式返回信息
	 *
	 * @param text
	 *            所要输出的信息
	 */
	public void renderText(final String text) {

		renderToView("text/plain", text);
	}

	/**
	 * 以html形式返回信息
	 *
	 * @param text
	 *            所要输出的信息
	 */
	public void renderHtml(final String text) {

		renderToView("text/html", text);
	}

	/**
	 * 以XML形式返回信息
	 *
	 * @param text
	 *            所要输出的信息
	 */
	public void renderXML(final String text) {

		renderToView("text/xml", text);
	}

	/**
	 * 根据不同的类型（plain,html,xml等），返回页面信息
	 *
	 * @param type
	 *            返回的类型
	 * @param text
	 *            要返回的信息
	 */
	private void renderToView(final String type, final String text) {

		try {
			final HttpServletResponse response = getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.getWriter().write(text);
		} catch (final IOException e) {
			this.logger.error("向Response输出{}时出错" + text);
		}
	}

	/**
	 * 输出json结果类型。
	 * eg.
	 * 
	 * <pre>
	 * Object[] results = new Object[] { &quot;name&quot;, &quot;xiaohu&quot;, &quot;age&quot;, 66, &quot;sex&quot;, &quot;女&quot; };
	 * this.renderJson(true, results);
	 * </pre>
	 * 
	 * 通过上述代码，输出到response中的json串是{"success":true, "name":"xiaohu", "age": 66, "sex":"女"}
	 *
	 * @param success
	 *            boolean类型，是否成功的标志。
	 * @param appdx
	 *            其他一些附加信息，是一个对象数组，约定为两两一组的键值。比如["propa","propa value","propb", "propb value"].
	 */
	protected void renderJson(final boolean success, final Object[] appdx) {

		final JSONObject jsonObj = new JSONObject();

		jsonObj.accumulate("success", success);

		int i = 0;
		while (i < appdx.length) {
			jsonObj.accumulate(appdx[i].toString(), appdx[i + 1]);
			i += 2;
		}
		renderText(jsonObj.toString());
	}

	/**
	 * 输出json结果类型。
	 * eg. <li>this.renderJson(true); 对应输出json串为：{"success": true}</li> <li>this.renderJson(false, "something error");
	 * 对应输出json串为：{"success":false, "msg":"something error"}</li>
	 *
	 * @param success
	 *            boolean类型，是否成功的标志。
	 * @param msg
	 *            消息类型
	 */
	protected void renderJson(final boolean success, final String... msg) {

		Object[] appdx = null;
		if (msg != null && msg.length > 0) {
			appdx = new String[] { "msg", msg[0] };
		} else {
			appdx = new String[] {};
		}

		renderJson(success, appdx);
	}

	protected void renderJson(final JSON json) {

		renderToView("text/json", json.toString());
	}

	protected HttpServletRequest getRequest() {

		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {

		return ServletActionContext.getResponse();
	}

	protected ValueStack getValueStatck() {

		return ActionContext.getContext().getValueStack();
	}

	protected HttpSession getSession() {

		return ServletActionContext.getRequest().getSession();
	}

	protected ServletContext getServletContext() {

		return ServletActionContext.getServletContext();
	}

	protected String getParameter(final String param) {

		return getRequest().getParameter(param);
	}

	public Object getBean(final String beanName) {

		Object bean = null;
		bean = WebApplicationContextUtils.getRequiredWebApplicationContext(
				getServletContext()).getBean(beanName);
		return bean;
	}

}
