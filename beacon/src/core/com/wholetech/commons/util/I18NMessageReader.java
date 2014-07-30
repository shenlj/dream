package com.wholetech.commons.util;

import com.wholetech.commons.Constants;

/**
 * 针对i18n消息提供的一个消息获取类。提供静态方法。
 * 
 */
public class I18NMessageReader {
	
	private static MessageReader mReader = new MessageReader(Constants.MESSAGE_BUNDLE_KEY);
	
	public static String getMessage(String mCode,Object[] args) {
		return mReader.getMessage(mCode, args);
	}
	
	public static String getMessage(String mCode) {
		return getMessage(mCode, null);
	}
	
}
