package com.wholetech.commons.util;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.Constants;

public class SqlBuilder {

	public static final Logger logger = LoggerFactory.getLogger(SqlBuilder.class);

	public static final String IGNORE_FILTER = "#$SSE_Fsso3484nh_)*&3fr_";

	private static final String SQL_RULE_OPEN = "/~";
	private static final String SQL_RULE_CLOSE = "~/";

	public static String insert(String sTable, String strRowName[], String[] strRowValue) {
		// 要插入列的个数
		int iLength = strRowName.length;

		StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(sTable).append(" ( ");
		for (int i = 0; i < iLength - 1; i++) {
			sql = sql.append(" ").append(strRowName[i]).append(",");
		}

		sql = sql.append(strRowName[iLength - 1]).append(") values (");
		for (int i = 0; i < iLength - 1; i++) {
			sql = sql.append(" '").append(strRowValue[i]).append("',");
		}

		sql = sql.append("'").append(strRowValue[iLength - 1]).append("')");
		return sql.toString();
	}

	public static String insert(String sTable, Object strRowName[], Object[] strRowValue) {
		int iLength = strRowName.length;// 要插入列的个数

		StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(sTable).append(" ( ");
		for (int i = 0; i < iLength - 1; i++) {
			sql = sql.append(" ").append(strRowName[i]).append(",");
		}

		sql = sql.append(strRowName[iLength - 1]).append(") values (");
		for (int i = 0; i < iLength - 1; i++) {
			if (strRowValue[i] instanceof Long) {
				sql = sql.append(strRowValue[i]).append(",");
			} else if (strRowValue[i] instanceof Date) {
				sql = sql.append(convertDate(strRowValue[i]));
			} else if (strRowValue[i] == null || strRowValue[i].toString().equals("")) {
				sql = sql.append(" null,");
			} else {
				sql = sql.append(" '").append(StringEscapeUtils.escapeSql(strRowValue[i].toString())).append("',");
			}
		}
		if (strRowValue[iLength - 1] instanceof Long) {
			sql = sql.append(strRowValue[iLength - 1]).append(")");
		} else if (strRowValue[iLength - 1] instanceof Date) {
			sql = sql.append(convertDate(strRowValue[iLength - 1]));
		} else if (strRowValue[iLength - 1] == null || strRowValue[iLength - 1].toString().equals("")) {
			sql = sql.append(" null)");
		} else {
			sql = sql.append(" '").append(StringEscapeUtils.escapeSql(strRowValue[iLength - 1].toString()))
					.append("')");
		}
		return sql.toString();
	}

	/**
	 * 可以动态配置过滤条件。
	 * 动态条件配置规则是将过滤条件放在/~ 和 ~/之间，那么会根据传入的条件，将/~ ~/给忽略掉，或者转成正常的sql。
	 * 其中如果filters中条件等于{@link SqlBuilder.IGNORE_FILTER}，则将当前条件过滤。
	 * <p>
	 * 例如： select * form user where 1=1 /~and sex=?~/ /~and name=?~/ 那么对于条件["m",
	 * IGNORE_FILTER]，则sql被解析成select * form user where 1=1 and sex=?
	 * 
	 * @param srcSQL
	 *            原始SQL，带有/~ ~/语句块。
	 * @param filters
	 *            过滤条件，解析完成后，其中的IGNORE条件也都被删除掉了。
	 * @return
	 */
	public static String parseSql(String srcSQL, Object[] filters) {
		// 转成StringBuffer，提高效率
		StringBuffer bfSQL = new StringBuffer(srcSQL);

		for (int i = 0, start = 0, end = 0; (start = bfSQL.indexOf(SQL_RULE_OPEN, end)) >= 0; i++) {
			// 从上次结束到当前开始位置，可能有?存在，则把这样的参数给迈过去。
			i += StringUtils.countMatches(bfSQL.substring(end, start), "?");

			end = bfSQL.indexOf(SQL_RULE_CLOSE, start);
			if (end == -1) {
				throw new java.lang.IllegalArgumentException("srcSQL doesn't obey the sql format rule");
			}

			// 使用==就可以判断
			if (IGNORE_FILTER == filters[i]) {
				// 如果是忽略的过滤条件，则直接移除
				bfSQL.replace(start, end + SQL_RULE_CLOSE.length(), "");
				end = start;
			} else {
				String real = bfSQL.substring(start + SQL_RULE_OPEN.length(), end);
				bfSQL.replace(start, end + SQL_RULE_CLOSE.length(), real);
				end = start + real.length();
			}

		}
		return bfSQL.toString();
	}

	/**
	 * 将过滤条件中的空白字符串的过滤条件置为忽略。
	 * 
	 * @param filters
	 * @return 处理过的参数
	 */
	public static Object[] ignoreNullBlank(Object[] filters) {
		Object[] arr = new Object[filters.length];
		for (int i = 0; i < filters.length; i++) {
			if (filters[i] == null) {
				arr[i] = IGNORE_FILTER;
			} else if (filters[i] instanceof String && StringUtils.isBlank((String) filters[i])) {
				arr[i] = IGNORE_FILTER;
			} else {
				arr[i] = filters[i];
			}
		}

		return arr;
	}

	/**
	 * 将过滤条件中的空白字符串的过滤条件置为忽略。
	 * 
	 * @deprecated 请使用ignoreNullBlank方法。
	 * @param filters
	 */
	public static void ignoreBlankString(Object[] filters) {
		for (int i = 0; i < filters.length; i++) {
			if (filters[i] instanceof String && StringUtils.isBlank((String) filters[i])) {
				filters[i] = IGNORE_FILTER;
			}
		}
	}

	private static String convertDate(Object date) {
		StringBuffer sql = new StringBuffer();
		// 这里应该使用framework.properties配置文件配置的current和数据库key 即Constants.current
		// equals Constants.oracl 类似的。
		if ("derby".equals(Constants.CURRENT_DATABASE_TYPE)) {
			return sql.append(" TIMESTAMP('").append(DateUtil.format((Date) date, DateUtil.getDateTimePattern())).append("'),").toString();
		} else if ("oracle".equals(Constants.CURRENT_DATABASE_TYPE)) {
			return sql.append(" to_date('").append(
					DateUtil.format((Date) date, DateUtil.getDateTimePattern())).append(
					"','yyyy-MM-dd HH24:mi:ss'),").toString();
		} else if ("sqlserver".equals(Constants.CURRENT_DATABASE_TYPE)) {
			return sql.append(" convert(date,").append(DateUtil.format((Date) date, DateUtil.getDateTimePattern())).append(",120),").toString();
		} else {
			return sql.append(" '").append(DateUtil.format((Date) date, DateUtil.getDateTimePattern())).append("',").toString();
		}
	}
}