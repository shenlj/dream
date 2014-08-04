package com.wholetech.commons.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PermissionUrlUtil {

	private static Map<String, String> urlMap = null;

	private static void init() {

	}

	public static boolean HasUrl(final String url) {

		if (PermissionUrlUtil.urlMap == null) {
			PermissionUrlUtil.init();
		}
		if (PermissionUrlUtil.urlMap.get(url) != null) {
			return true;
		}
		return false;
	}

	/**
	 * @author
	 * @version 1.0
	 * @功能描述： 根据数据结构判断是否有下级单位
	 * @see
	 * @param rsList
	 *            需要判断的数据集合
	 * @return List<Object[]> 带有下级关系的树形结构数据
	 * @exception JeawException
	 */
	public static List<Object[]> changeTreeDataEx(final List<Object[]> rsList, final String[] OpenStr) {

		List<Object[]> rtList = null;
		if (rsList != null && rsList.size() != 0) {
			rtList = new ArrayList<Object[]>();
			for (final Object rsObj1 : rsList) {
				final Object[] rsObj = (Object[]) rsObj1;
				final Object[] rtObj = new Object[rsObj.length + 1];
				for (int i = 0; i < rsObj.length; i++) {
					if (i == rsObj.length - 1) {
						rtObj[i] = ((Number) rsObj[i]).intValue() > 0 ? true : false;
					} else {
						rtObj[i] = rsObj[i];
					}
				}
				for (final String openNodeid : OpenStr) {
					rtObj[rsObj.length] = rsObj[0].toString().equals(openNodeid) ? true : false;
				}
				rtList.add(rtObj);
			}
		}
		return rtList;
	}
}
