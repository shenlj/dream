package com.wholetech.commons.query;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.wholetech.commons.Constants;

/**
 * 封装一批查询参数的对象。
 * 
 */
public class QueryObject {

	private static String FILTER_PREFIX = "search$";

	private List<QueryParam> params = new ArrayList<QueryParam>();

	/**
	 * 添加一个参数到该批查询参数中。
	 * 
	 * @param queryParam
	 */
	public void addQueryParam(QueryParam queryParam) {
		this.params.add(queryParam);
	}

	/**
	 * 添加查询参数。
	 * 
	 * @param property
	 *            String 参数名称。
	 * @param clazz
	 *            Class 参数类型。
	 * @param matchType
	 *            QueryParam.MatchType 匹配类型。
	 * @param value
	 *            参数值。
	 */
	public void addQueryParam(String property, Class<?> clazz, QueryParam.MatchType matchType, String value) {
		QueryParam qp = new QueryParam(property, value, clazz, matchType);
		this.addQueryParam(qp);
	}

	/**
	 * 添加查询参数。
	 * 如果参数值为null或者空字符串，则不会添加到查询对象中。
	 * 
	 * @param property
	 *            String 参数名称。
	 * @param clazz
	 *            Class 参数类型。
	 * @param matchType
	 *            QueryParam.MatchType 匹配类型。
	 * @param value
	 *            参数值。
	 */
	public void addQueryParamFilterNull(String property, Class<?> clazz, QueryParam.MatchType matchType, String value) {
		if (StringUtils.isNotEmpty(value)) {
			QueryParam qp = new QueryParam(property, value, clazz, matchType);
			this.addQueryParam(qp);
		}
	}

	public List<QueryParam> getQueryParams() {
		return this.params;
	}

	/**
	 * 从request中获取参数，将参数中的查询条件封装成QueryObject返回。
	 * 从request中获取参数时，会根据参数名的前缀来过滤参数，前缀具体是什么请参见{@link Constants.FILTER_PREFIX}；
	 * 该前缀加上QueryParam中的参数规则，则形成jsp页面中定义参数的具体形式。参见{@link QueryParam.build}
	 * 
	 * @param request
	 * @return QueryObject
	 */
	public static QueryObject fromHttpRequest(HttpServletRequest request) {
		QueryObject qo = new QueryObject();

		// 从request中获取含属性前缀名的参数,构造去除前缀名后的参数Map.
		Map<String, String> filterParamMap = getParametersStartingWith(request, FILTER_PREFIX);

		// 分析参数Map,构造PropertyFilter列表
		for (Map.Entry<String, String> entry : filterParamMap.entrySet()) {
			String filterName = entry.getKey();
			String value = entry.getValue();
			// 如果value值为空,则忽略此filter.
			if (StringUtils.isNotBlank(value)) {
				QueryParam queryParam = QueryParam.build(filterName, value);
				qo.addQueryParam(queryParam);
			}
		}
		return qo;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, String> getParametersStartingWith(HttpServletRequest request, String prefix) {
		Enumeration<String> paramNames = request.getParameterNames();
		Map<String, String> params = new TreeMap<String, String>();
		if (prefix == null) {
			prefix = "";
		}
		while (paramNames != null && paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			if ("".equals(prefix) || paramName.startsWith(prefix)) {
				String unprefixed = paramName.substring(prefix.length());
				String[] values = request.getParameterValues(paramName);
				if (values == null || values.length == 0) {// NOSONAR
					// Do nothing, no values found at all.
				} else {
					params.put(unprefixed, values[0]);
				}
			}
		}
		return params;
	}

	@Override
	public String toString() {
		return this.params.toString();
	}

}
