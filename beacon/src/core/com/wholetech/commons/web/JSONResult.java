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
    jc.setCycleDetectionStrategy(CycleDetectionStrategy.NOPROP);
    jc.registerJsonValueProcessor(Date.class, new JsonDateProcessor(Constants.DEFAULT_DATETIME_FORMAT));
    jc.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateProcessor(Constants.DEFAULT_DATETIME_FORMAT));
  }

  /**
   * 将一个jsonArray 使用page来包装出一个page来
   * 
   * @param page
   * @param rows
   * @return
   */
  public static String wrapJson(Page<?> page, JSONArray rows) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, page.getSecho());
    jsonObject.element(TOTAL_RECORDS, page.getTotalCount());
    jsonObject.element(TOTAL_DISPLAYRECORDS, page.getTotalCount());

    jsonObject.element(ROWS, rows);

    String rslt = jsonObject.toString();
    logger.debug(rslt);

    return rslt;
  }

  public static String page2Json(Page<?> page, String properties) {

    if (page.getTotalCount() == 0) {
      logger.debug(EMPTY_SECHO + page.getSecho() + EMPTY_RESULT);
      return EMPTY_SECHO + page.getSecho() + EMPTY_RESULT;
    }
    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, page.getSecho());
    jsonObject.element(TOTAL_RECORDS, page.getTotalCount());
    jsonObject.element(TOTAL_DISPLAYRECORDS, page.getTotalCount());

    JSONArray rows = list2Json(page.getData(), properties);
    jsonObject.element(ROWS, rows);

    String rslt = jsonObject.toString();
    logger.debug(rslt);

    return rslt;
  }

  public static JSONObject page2RawJson(Page<?> page, String properties) {

    if (page.getTotalCount() == 0) {
      return new JSONObject();
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, page.getSecho());
    jsonObject.element(TOTAL_RECORDS, page.getTotalCount());
    jsonObject.element(TOTAL_DISPLAYRECORDS, page.getTotalCount());

    JSONArray rows = list2Json(page.getData(), properties);
    jsonObject.element(ROWS, rows);

    return jsonObject;
  }

  /**
   * 功能描述：去除json格式数据最后一项数据后的逗号
   * 
   * @author
   * @version 1.0
   * @see
   * @param jsonBuffer
   *          StringBuffer json格式数据
   * @return
   *         StringBuffer 去除逗号后的json格式数据
   */
  private static StringBuffer deleteLastComma(final StringBuffer jsonBuffer) {

    String jsonStr = jsonBuffer.toString();
    if (jsonStr.endsWith(",")) {
      int jsonBufferLength = jsonBuffer.length();
      jsonBuffer.delete(jsonBufferLength - 1, jsonBufferLength);
    }
    return jsonBuffer;
  }

  /**
   * 将一个map转成字符串，规则为{"key1":"value1","key2":"value2"}
   * 
   * @param map
   * @return
   */
  public static String map2Json(Map map) {

    if (map == null || map.isEmpty()) {
      return "{}";
    }
    StringBuffer rslt = new StringBuffer("{");
    Set<Map.Entry> entrys = map.entrySet();
    int i = 0;
    for (Map.Entry entry : entrys) {
      if (i > 0) {
        rslt.append(COMMA);
      } else {
        i++;
      }
      rslt.append(QUOTE_IN_JSON).append(entry.getKey()).append(QUOTE_IN_JSON).append(COLON).append(QUOTE_IN_JSON)
          .append(entry.getValue()).append(QUOTE_IN_JSON);
    }
    rslt.append("}");
    return rslt.toString();
  }

  public static JSONObject array2Json(Object[] arr, String[] nameMappings) {

    if (arr == null || arr.length == 0) {
      return new JSONObject();
    }

    JSONObject rslt = new JSONObject();
    for (int i = 0; i < nameMappings.length; i++) {
      if (nameMappings[i].indexOf(".") != -1) {
        String[] subProps = nameMappings[i].split(".");
        JSONObject subRslt = rslt;
        for (int j = 0; j < subProps.length - 1; j++) {
          if (subRslt.has(subProps[j])) {
            subRslt = subRslt.getJSONObject(subProps[j]);
          } else {
            JSONObject temp = new JSONObject();
            subRslt.element(subProps[j], temp);
            subRslt = temp;
          }
        }

        subRslt.accumulate(subProps[subProps.length - 1], arr[i], jc);

      } else {
        // 不是复合属性，直接设置就可以。
        rslt.element(nameMappings[i], arr[i] == null ? JSONNull.getInstance() : arr[i], jc);
      }
    }

    return rslt;
  }

  public static JSONArray Object2Json(Object object) {

    JSONArray jsonArray = JSONArray.fromObject(object);
    return jsonArray;
  }

  /**
   * 将一个列表转成一个json数据，以jsonArray表示。
   * 列表中的对象根据要求转义的属性转成json。
   * 
   * @param collection
   *          待转换的列表。
   * @param properties
   *          其中要转的属性。
   * @return
   */
  public static JSONArray list2Json(Collection<?> collection, String properties) {

    if (collection == null || collection.isEmpty()) {
      return new JSONArray();
    }

    String[] arrProperties = properties.split(COMMA);
    Obj2JsonConverter jsonConverter = new ClaimedProperties2JsonConverter(properties);
    JSONArray rows = new JSONArray();
    for (Object obj : collection) {
      if (obj.getClass().isArray()) {
        rows.add(array2Json((Object[]) obj, arrProperties));
      } else {
        JSONObject row = jsonConverter.toJSONObject(obj);
        if (obj instanceof BaseObject) {
          BaseObject entity = (BaseObject) obj;
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
  public static String list2JsonAsPage(Collection<?> collection, String properties) {

    JSONObject jsonObject = new JSONObject();
    jsonObject.element(PAGE, 1);
    jsonObject.element(TOTAL_RECORDS, 1);
    jsonObject.element(TOTAL_DISPLAYRECORDS, 1);

    JSONArray rows = list2Json(collection, properties);
    jsonObject.element(ROWS, rows);

    return jsonObject.toString();
  }

}
