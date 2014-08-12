package com.wholetech.commons.query;

import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 封装了查询条件。 一个查询条件有几个要素： <li>filterName -- 过滤属性名称</li> <li>value -- 过滤值</li> <li>
 * propertyType -- 过滤值类型，参看{@link QueryParam.PropertyType}</li> <li>matchType --
 * 匹配类型， 参看{@link QueryParam.MatchType}</li>
 * 
 * <br />
 * <br />
 * QueryParam将被Hibernate来构建criteria，参见
 * 
 * <br />
 * <br />
 * <br />
 * 
 */

public final class QueryParam {

	private static final String SPLIT_CHAR = "$";

	/** 属性比较类型. */
	public enum MatchType {
		EQ, LIKE, LT, GT, LE, GE, NE, IN, LLIKE, RLIKE, ISNULL, STRING, LONG, INTEGER, DOUBLE, FLOAT, DATE8, DATE12, BIGSTRING;
	}

	/** 属性数据类型. */
	public enum PropertyType {
		S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(
				Date.class), B(Boolean.class);

		private Class<?> clazz;

		PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}

	private String propertyName = null;
	private Class<?> propertyType = null;
	private Object propertyValue = null;
	private MatchType matchType = null;

	public QueryParam() {
	}

	/**
	 * 构造函数。 使用该构造函数构造QueryParam，其中的propertyType为String，matchType为EQ(相等);
	 * 
	 * @param filterName
	 *            过滤的属性属性。
	 * @param value
	 *            待比较的值.
	 */
	public QueryParam(final String filterName, final String value) {
		this.propertyName = filterName;
		this.propertyValue = value;
		this.propertyType = String.class;
		this.matchType = MatchType.EQ;
	}

	public QueryParam(final String filterName, final String value,
			Class<?> propertyType, MatchType matchType) {
		this.propertyName = filterName;
		this.propertyValue = value;
		this.propertyType = propertyType;
		this.matchType = matchType;
	}

	/**
	 * 获取比较属性名称列表.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 获取比较值.
	 */
	public Object getPropertyValue() {
		return propertyValue;
	}

	/**
	 * 获取比较值的类型.
	 */
	public Class<?> getPropertyType() {
		return propertyType;
	}

	/**
	 * 获取比较方式类型.
	 */
	public MatchType getMatchType() {
		return matchType;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public void setPropertyType(Class<?> propertyType) {
		this.propertyType = propertyType;
	}

	public void setPropertyValue(Object propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setMatchType(MatchType matchType) {
		this.matchType = matchType;
	}

	/**
	 * 根据传入的过滤条件名称和值，构建一个查询参数对象。
	 * 该函数对应了约定的一个规则。规则如下：propName_matchType_valueType,其中propName是必须的，后面两个是可选的。
	 * eg.<code>name_LIKE_S, name, name_LIKE</code>都是合乎规则的命名方式；
	 * 但是不能单独缺乏matchType，eg.<code>name_S</code>是非法的。
	 * 
	 * @param filterName
	 * @param value
	 * @return
	 */
	public static QueryParam build(String filterName, String value) {
		String prop = StringUtils.substringBefore(filterName, SPLIT_CHAR);
		String remain = StringUtils.substringAfter(filterName, SPLIT_CHAR);
		QueryParam queryParam = new QueryParam(prop, value);

		// 获取匹配类型
		if (remain.length() > 0) {
			String matchTypeCode = StringUtils.substringBefore(remain,
					SPLIT_CHAR);
			remain = StringUtils.substringAfter(remain, SPLIT_CHAR);
			queryParam.setMatchType(Enum
					.valueOf(MatchType.class, matchTypeCode));
		}

		// 获取当前数据类型
		if (remain.length() > 0) {
			String propertyTypeCode = StringUtils.substringBefore(remain,
					SPLIT_CHAR);
			Class<?> clazz = Enum.valueOf(PropertyType.class, propertyTypeCode)
					.getValue();
			queryParam.setPropertyType(clazz);

			if (QueryParam.MatchType.IN == queryParam.getMatchType()) {
				String[] arrStr = StringUtils.split(value, ",");
				Object[] arrObj = new Object[arrStr.length];
				for (int i = 0; i < arrStr.length; i++) {
					arrObj[i] = ConvertUtils.convert(arrStr[i], clazz);
				}
				queryParam.setPropertyValue(arrObj);
			} else {
				queryParam.setPropertyValue(ConvertUtils.convert(value, clazz));
			}

		} else if (QueryParam.MatchType.IN == queryParam.getMatchType()) {
			queryParam.setPropertyValue(StringUtils.split(value, ","));
		}

		return queryParam;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('{').append(this.propertyName).append(" ").append(
				this.matchType).append(" ").append(this.propertyValue).append(
				'}');
		return sb.toString();
	}

}
