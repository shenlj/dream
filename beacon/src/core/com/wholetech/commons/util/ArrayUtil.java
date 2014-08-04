package com.wholetech.commons.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 针对数组的工具类。继承自org.apache.commons.lang.ArrayUtils。
 */

public final class ArrayUtil extends org.apache.commons.lang.ArrayUtils {

	public static final String EMPTY_STRING_ARRAY[] = new String[0];

	public ArrayUtil() {

	}

	/**
	 * 将一个字符串按照指定分隔符转换成字符串数组。
	 *
	 * @param value
	 *            原始字符串。
	 * @param delim
	 *            指定分隔符。
	 * @return 分割后的字符串数组。
	 */
	public static String[] toStringArray(final String value, final String delim) {

		if (value != null) {
			return StringUtil.split(delim, value);
		} else {
			return ArrayUtil.EMPTY_STRING_ARRAY;
		}
	}

	/**
	 * 将一个任意类型数组转换成字符串数组。
	 *
	 * @param array
	 *            原始的数组。
	 * @return 转换后的字符串数组。
	 */
	public static String[] toString(final Object[] array) {

		String[] result = null;
		if (ArrayUtils.isEmpty(array)) {
			return result;
		} else {
			int i = 0;
			result = new String[array.length];
			for (final Object obj : array) {
				result[i] = String.valueOf(obj);
				i++;
			}
			return result;
		}
	}

	/**
	 * 将一个数组转换成ArrayList。
	 *
	 * @param array
	 *            被转换的数组。
	 * @return ArrayList
	 */
	public static List toList(final Object array) {

		if (array == null) {
			return new ArrayList();
		}

		if (array instanceof Object[]) {
			return Arrays.asList((Object[]) array);
		}

		final int size = Array.getLength(array);
		final ArrayList list = new ArrayList();
		for (int i = 0; i < size; i++) {
			list.add(Array.get(array, i));
		}

		return list;
	}

	/**
	 * 判断一个字符串数组是否是空字符串数组。如果每一个元素都是空字符串，则也被认为是空字符串数组。
	 *
	 * @param array
	 *            被判断的字符串。
	 * @return true or false
	 */
	public static boolean isEmptyStringArray(final String[] array) {

		if (array == null || array.length == 0) {
			return true;
		}

		for (final String item : array) {
			if (!StringUtils.isEmpty(item)) {
				return false;
			}
		}

		return true;
	}

	public static Object[] removeElements(final Object[] arr, final Object target) {

		final List list = new ArrayList(arr.length);
		for (final Object element : arr) {
			if (target == element
					|| (target != null && target.equals(element))) {

			} else {
				list.add(element);
			}
		}

		return list.toArray();
	}
}
