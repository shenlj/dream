package com.wholetech.commons;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 定义对象以toString方式显示的样式
	 */
	protected final static ToStringStyle TO_STRING_STYLE = ToStringStyle.MULTI_LINE_STYLE;

	private String id;
	
	public void setId(String id) {
		if (StringUtils.isBlank(id)) {
			this.id = null;
		} else {
			this.id = id;
		}
	}

	public String getId() {
		return id;
	}

	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this,
				TO_STRING_STYLE);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

}
