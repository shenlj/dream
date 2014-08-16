// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   StringUtil.java

package com.wholetech.commons.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 扩展自org.apache.commons.lang.StringUtils，提供操作String的工具函数。
 */
public final class StringUtil extends org.apache.commons.lang.StringUtils{

	public StringUtil() {
		
	}
	
	/**
	 * 使用指定的分割符(可以是多个)做String分词。参数seperators中的所有字符都作为分隔符。
	 * <p>
	 * 如果参数include标志为true，则分隔符也作为分词的一部分返回；如果为false，则分隔符将被跳过。
	 * 
	 * @param seperators 分隔符字符串。
	 * @param list 待分词的字符串。
	 * @param include 返回的分词中是否包含分隔符。
	 * @return 分割后的字符串数组。
	 */
	public static String[] split(String seperators, String list, boolean include) {
		StringTokenizer tokens = new StringTokenizer(list, seperators, include);
		String result[] = new String[tokens.countTokens()];
		int i = 0;
		
		while (tokens.hasMoreTokens())
			result[i++] = tokens.nextToken();
		return result;
	}

	/**
	 * 将字符串转成boolean类型。
	 * 如果字符串等于"true"、"t"、"1"，则返回true，否则返回false。
	 * @param tfString 待转换字符串。
	 * @return
	 */
	public static boolean booleanValue(String tfString) {
		String trimmed = tfString.trim().toLowerCase();
		return "true".equals(trimmed) 
				|| "1".equals(trimmed) 
				|| "t".equals(trimmed);
	}
	
	/**
	 * 根据提供的标示符,分割字符串。
	 * 参数delim中的字符都会被作为分隔符使用，分隔符不会被作为分词的一部分。
	 * 
	 * @param str 待分割字符串。
	 * @param delim 分隔符。
	 * @return
	 */
	public static final String[] explodeString(String str, String delim) {
		if (str == null || str.equals("")) {
			String[] retstr = new String[1];
			retstr[0] = "";
			return retstr;
		}
		StringTokenizer st = new StringTokenizer(str, delim);
		String[] retstr = new String[st.countTokens()];
		int i = 0;
		
		while (st.hasMoreTokens()) {
			retstr[i] = st.nextToken();
			i++;
		}
		
		if (i == 0) {
			retstr[0] = str;
		}
		
		return retstr;
	}

	/**
	 * 判断是否为整型数字类型。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[1-9][0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 过滤空值。
	 * 如果参数src不为空，则返回src的字符串表示，否则返回参数defaultValue指定的值。
	 * 
	 * @param src 要过滤的对象。
	 * @param defaultValue 默认返回值。
	 * @return 过滤null之后的返回值，如果为null，则返回默认值，否则去掉两边的空格后返回。
	 */
	public static String filterNull(Object src, String defaultValue) {
		if (src != null) {
			return src.toString().trim();
		}

		return defaultValue;
	}

	

	/**
	 * 从参数src左边取参数length指定数量的字符。
	 * 如果参数src为null，则返回参数defaultValue。
	 * 
	 * @param src 源字符串。
	 * @param length 截取长度。
	 * @param defaultValue src为null情况下返回的值。
	 * @return
	 */
	public static String left(Object src, int length, String defaultValue) {
		if (src != null) {
			String temp = src.toString();

			if (temp.length() >= length) {
				return temp.substring(0, length);
			}

			return temp;
		}

		return defaultValue;
	}

	/**
	 * 从参数src右边取参数length指定数量的字符。
	 * 如果参数src为null，则返回参数defaultValue。
	 * 
	 * @param src 源字符串。
	 * @param length 截取长度。
	 * @param defaultValue src为null情况下返回的值。
	 * @return
	 */
	public static String right(Object src, int length, String defaultValue) {
		if (src != null) {
			String temp = src.toString();
			int tempLen = temp.length();

			if (tempLen >= length) {
				return temp.substring(tempLen - length, tempLen);
			}

			return temp;
		}

		return defaultValue;
	}

	/**
	 * 按照拆分字符串。如果分词为空也保存在返回的字符串数组。
	 * <br>
	 * 例如
	 * <ul>StringUtil.split("a.b.c", '.')    = ["a", "b", "c"]</ul>
 	 * <ul>StringUtil.split("a..b.c", '.')   = ["a", "", "b", "c"]</ul>
	 * 
	 * @param src 要拆分的字符串。
	 * @param flag 拆分的标记。
	 * 
	 * @return 返回拆分后的数组，内容为空也包含在拆分的字符串数组中。
	 */
	public static String[] split(String src, String flag) {
		return StringUtils.splitPreserveAllTokens(src, flag);
	}

	/**
	 * 获取不带扩展名的文件名。
	 * 比如：StringUtil.getFilenameWithNoExt("C:/dir/a.txt") = "C:/dir/a";
	 * 
	 * @param filename 要处理的文件名。
	 * @return 去掉扩展名后的文件名。
	 */
	public static String getFilenameWithNoExt(String filename) {
		return filename.substring(0, filename.lastIndexOf("."));
	}

	/**
	 * 获取文件的扩展名。
	 * 比如：StringUtil.getFilenameWithNoExt("C:/dir/a.txt") = "txt";
	 * 
	 * @param filename 要处理的文件名。
	 * @return 文件名的扩展名。
	 */
	public static String getFilenameExt(String filename) {
		return filename.substring(filename.lastIndexOf(".") + 1);
	}

	/**
	 * 将参数src指定的字符串转成整型，如果无法解析为整型数字，则返回参数defaultValue指定的值。
	 * 
	 * @param src
	 * @param defaultValue
	 * @return int 解析后的整数。
	 */
	public static int toInt(Object src, int defaultValue) {
		try {
			return Integer.parseInt(src.toString());
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
}
