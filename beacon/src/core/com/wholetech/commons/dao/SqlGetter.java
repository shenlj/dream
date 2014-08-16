package com.wholetech.commons.dao;

import java.text.MessageFormat;
import java.util.List;

import com.wholetech.commons.exception.SQLConfigException;
import com.wholetech.commons.util.PropertyXmlMgr;
import com.wholetech.commons.util.StringUtil;

public class SqlGetter {

	private List<String> aliasNames;

	public void setAliasNames(List<String> aliasNames) {
		this.aliasNames = aliasNames;
	}

	/**
	 * 描述：根据key在配置的别名所对于德配置文件中查询SQL/HQL
	 * 
	 * @param key
	 *            配置文件中的属性名
	 */
	public String getSql(String key) {
		String value = null;
		for (String alias : aliasNames) {
			value = PropertyXmlMgr.getString(alias, key);
			if (StringUtil.isNotBlank(value)) {
				return value;
			}
		}
		if (StringUtil.isBlank(value)) {
			throw new SQLConfigException(key);
		}
		return value;
	}

	/**
	 * 获取sql，把sql中模式化的内容使用参数替换。
	 * 比如：
	 * sql_find_student_in_age = "select * from student where age in ({0})"；
	 * 使用String sql = sqlGetter.getSql(sql_find_student_in_age, "21,22");
	 * 将得到sql = select * from student where age in (21, 22)
	 * 
	 * <p>
	 * 注：第二个参数是数组，其中的顺序 要和配置字符串中的{index}对应起来。 arguments[0] 替换{0}, arguments[1]
	 * 替换{1}...
	 * 
	 * @param key
	 *            sql配置key
	 * @param arguments
	 *            替换参数。
	 * @return
	 */
	public String getSql(String key, String[] arguments) {
		String value = null;
		for (String alias : aliasNames) {
			value = PropertyXmlMgr.getString(alias, key);
			if (StringUtil.isNotBlank(value)) {
				// MessageFormat的一个规则，会把''解析成'
				value = value.replace("'", "''");
				value = MessageFormat.format(value, arguments);
				return value;
			}
		}
		if (StringUtil.isBlank(value)) {
			throw new SQLConfigException(key);
		}
		return value;
	}

}