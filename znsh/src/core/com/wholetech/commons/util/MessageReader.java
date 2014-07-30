package com.wholetech.commons.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

/** 
 * 读取国际化配置文件工具类。
 * 
 */
public class MessageReader {

	private ResourceBundle rb;
	
	/**
	 * 通过基础名称来构建读取工具类。
	 * <p>
	 * 比如当前baseName="messages"，你的开发环境是中文环境，那么读取的配置文件就可能是messages_zh_CN.properties。
	 * 
	 * @param baseName 相对于当前classpath的相对路径
	 */
	public MessageReader(String baseName) {
		rb = ResourceBundle.getBundle(baseName, LocaleContextHolder.getLocale());
	}
	
	/**
	 * 读取配置消息。
	 * <p>
	 * 比如配置的消息时是：{0}爱吃{1}，给出参数['小狗','骨头']，<br>
	 * 那么获取的消息为'小狗爱吃骨头'。
	 * 
	 * @param i18nCode 消息键值。
	 * @param errorArgs 消息中配置的参数对应给出的参数值。
	 * @return i18n配置文件中对应的消息；null如果没找到对应的配置消息。
	 */
	public String getMessage(String i18nCode,Object[] errorArgs) {
		String message = null;
	
		try {
			message = rb.getString(i18nCode);
			if (errorArgs != null) {
				message = MessageFormat.format(message, (Object[]) errorArgs);
			}
		} catch (MissingResourceException mse) {
			// donothing, return null
		}

		return message;
	}
	
	/**
	 * 读取配置消息。
	 * 
	 * @param i18nCode 消息键值。
	 * @return i18n配置文件中对应的消息；null如果没找到对应的配置消息。
	 */
	public String getMessage(String i18nCode) {
		return getMessage(i18nCode,null);
	}

}
