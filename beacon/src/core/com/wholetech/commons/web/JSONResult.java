package com.wholetech.commons.web;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.BaseObject;
import com.wholetech.commons.ClaimedProperties2JsonConverter;
import com.wholetech.commons.Constants;
import com.wholetech.commons.JsonDateProcessor;
import com.wholetech.commons.Obj2JsonConverter;
import com.wholetech.commons.query.Page;

public class JSONResult {

	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(JSONResult.class);

	private static final String PAGE = "secho";
	private static final String TOTAL_RECORDS = "iTotalRecords";// 总条数
	private static final String TOTAL_DISPLAYRECORDS = "iTotalDisplayRecords";// 过滤后实际总条数
	private static final String ROWS = "aaData";
	private static final String EMPTY_SECHO = "{\"secho\":\"";
	private static final String EMPTY_RESULT = "\",\"iTotalRecords\":0,\"iTotalDisplayRecords\":0,\"aaData\":[]}";
	private static final char QUOTE_IN_JSON = '"';
	private static final String COMMA = ",";
	private static final char COLON = ':';

	private static JsonConfig jc = new JsonConfig();

	static {
		JSONResult.jc.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
		JSONResult.jc.registerJsonValueProcessor(Date.class, new JsonDateProcessor(Constants.DEFAULT_DATETIME_FORMAT));
		JSONResult.jc.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateProcessor(
				Constants.DEFAULT_DATETIME_FORMAT));
	}

	/**
	 * 将一个jsonArray 使用page来包装出一个page来
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	public static String wrapJson(final Page<?> page, final JSONArray rows) {

		final JSONObject jsonObject = new JSONObject();
		jsonObject.element(JSONResult.PAGE, page.getSecho());
		jsonObject.element(JSONResult.TOTAL_RECORDS, page.getTotalCount());
		jsonObject.element(JSONResult.TOTAL_DISPLAYRECORDS, page.getTotalCount());

		jsonObject.element(JSONResult.ROWS, rows);

		final String rslt = jsonObject.toString();
		JSONResult.logger.debug(rslt);

		return rslt;
	}

	public static String page2Json(final Page<?> page, final String properties) {

		if (page.getTotalCount() == 0) {
			JSONResult.logger.debug(JSONResult.EMPTY_SECHO + page.getSecho() + JSONResult.EMPTY_RESULT);
			return JSONResult.EMPTY_SECHO + page.getSecho() + JSONResult.EMPTY_RESULT;
		}
		final JSONObject jsonObject = new JSONObject();
		jsonObject.element(JSONResult.PAGE, page.getSecho());
		jsonObject.element(JSONResult.TOTAL_RECORDS, page.getTotalCount());
		jsonObject.element(JSONResult.TOTAL_DISPLAYRECORDS, page.getTotalCount());

		final JSONArray rows = JSONResult.list2Json(page.getData(), properties);
		jsonObject.element(JSONResult.ROWS, rows);

		final String rslt = jsonObject.toString();
		JSONResult.logger.debug(rslt);

		return rslt;
	}

	public static JSONObject page2RawJson(final Page<?> page, final String properties) {

		if (page.getTotalCount() == 0) {
			return new JSONObject();
		}

		final JSONObject jsonObject = new JSONObject();
		jsonObject.element(JSONResult.PAGE, page.getSecho());
		jsonObject.element(JSONResult.TOTAL_RECORDS, page.getTotalCount());
		jsonObject.element(JSONResult.TOTAL_DISPLAYRECORDS, page.getTotalCount());

		final JSONArray rows = JSONResult.list2Json(page.getData(), properties);
		jsonObject.element(JSONResult.ROWS, rows);

		return jsonObject;
	}

	/**
	 * 将一个map转成字符串，规则为{"key1":"value1","key2":"value2"}
	 * 
	 * @param map
	 * @return
	 */
	public static String map2Json(final Map map) {

		if (map == null || map.isEmpty()) {
			return "{}";
		}
		final StringBuffer rslt = new StringBuffer("{");
		final Set<Map.Entry> entrys = map.entrySet();
		int i = 0;
		for (final Map.Entry entry : entrys) {
			if (i > 0) {
				rslt.append(JSONResult.COMMA);
			} else {
				i++;
			}
			rslt.append(JSONResult.QUOTE_IN_JSON).append(entry.getKey()).append(JSONResult.QUOTE_IN_JSON)
					.append(JSONResult.COLON).append(JSONResult.QUOTE_IN_JSON)
					.append(entry.getValue()).append(JSONResult.QUOTE_IN_JSON);
		}
		rslt.append("}");
		return rslt.toString();
	}

	public static JSONObject array2Json(final Object[] arr, final String[] nameMappings) {

		if (arr == null || arr.length == 0) {
			return new JSONObject();
		}

		final JSONObject rslt = new JSONObject();
		for (int i = 0; i < nameMappings.length; i++) {
			if (nameMappings[i].indexOf(".") != -1) {
				final String[] subProps = nameMappings[i].split(".");
				JSONObject subRslt = rslt;
				for (int j = 0; j < subProps.length - 1; j++) {
					if (subRslt.has(subProps[j])) {
						subRslt = subRslt.getJSONObject(subProps[j]);
					} else {
						final JSONObject temp = new JSONObject();
						subRslt.element(subProps[j], temp);
						subRslt = temp;
					}
				}

				subRslt.accumulate(subProps[subProps.length - 1], arr[i], JSONResult.jc);

			} else {
				// 不是复合属性，直接设置就可以。
				rslt.element(nameMappings[i], arr[i] == null ? JSONNull.getInstance() : arr[i], JSONResult.jc);
			}
		}

		return rslt;
	}

	public static JSONArray Object2Json(final Object object) {

		final JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray;
	}

	/**
	 * 将一个列表转成一个json数据，以jsonArray表示。
	 * 列表中的对象根据要求转义的属性转成json。
	 * 
	 * @param collection
	 *            待转换的列表。
	 * @param properties
	 *            其中要转的属性。
	 * @return
	 */
	public static JSONArray list2Json(final Collection<?> collection, final String properties) {

		if (collection == null || collection.isEmpty()) {
			return new JSONArray();
		}

		final String[] arrProperties = properties.split(JSONResult.COMMA);
		final Obj2JsonConverter jsonConverter = new ClaimedProperties2JsonConverter(properties);
		final JSONArray rows = new JSONArray();
		for (final Object obj : collection) {
			if (obj.getClass().isArray()) {
				rows.add(JSONResult.array2Json((Object[]) obj, arrProperties));
			} else {
				final JSONObject row = jsonConverter.toJSONObject(obj);
				if (obj instanceof BaseObject) {
					final BaseObject entity = (BaseObject) obj;
					row.element(Constants.DEFAULT_ID_NAME, entity.getId());
				}
				rows.add(row);
			}
		}

		return rows;
	}

	/**
	 * 将一个list直接转成page json数据。
	 * 应用场景是：
	 * 一次性加载所有的数据不分页；
	 * 或者一次记载所有的数据，在前端再分页。
	 * 
	 * @param collection
	 * @param properties
	 * @return
	 */
	public static String list2JsonAsPage(final Collection<?> collection, final String properties) {

		final JSONObject jsonObject = new JSONObject();
		jsonObject.element(JSONResult.PAGE, 1);
		jsonObject.element(JSONResult.TOTAL_RECORDS, 1);
		jsonObject.element(JSONResult.TOTAL_DISPLAYRECORDS, 1);

		final JSONArray rows = JSONResult.list2Json(collection, properties);
		jsonObject.element(JSONResult.ROWS, rows);

		return jsonObject.toString();
	}

}
