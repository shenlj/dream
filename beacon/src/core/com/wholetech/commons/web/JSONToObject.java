package com.wholetech.commons.web;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 文件名：JSONToObject.java
 * 作者：
 * 日期：2011-7-4
 * 功能说明：json串转成相应的类对象实例
 * ===============================================================================
 * 修改记录：
 * 修改作者 日期 修改内容
 * ===============================================================================
 * Copyright (c) 2010-2011 .All rights reserved.
 */
public class JSONToObject {

	/**
	 * json串转成相应类对象组
	 * 
	 * @param <T>
	 * @param jsonStr
	 * @param beanClass
	 * @return
	 */
	public static <T> List<T> jsonToBeanList(final String jsonStr, final Class<T> beanClass) {

		final JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		final List<T> list = new ArrayList<T>();
		for (int i = 0; i < jsonArray.size(); i++) {
			final JSONObject jsonObj = JSONObject.fromObject(jsonArray.get(i));
			list.add((T) JSONObject.toBean(jsonObj, beanClass));
		}
		return list;
	}
}
