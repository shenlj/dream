package com.wholetech.commons;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseObject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 定义对象以toString方式显示的样式
	 */
	protected final static ToStringStyle TO_STRING_STYLE = ToStringStyle.MULTI_LINE_STYLE;

	private String id;

	private boolean added;

	public void setId(final String id) {

		if (StringUtils.isBlank(id)) {
			this.id = null;
		} else {
			this.id = id;
		}
	}

	public String getId() {

		return this.id;
	}

	public boolean isAdded() {

		return this.added;
	}

	public void setAdded(final boolean added) {

		this.added = added;
	}

	@Override
	public String toString() {

		return ToStringBuilder.reflectionToString(this,
				BaseObject.TO_STRING_STYLE);
	}

	@Override
	public boolean equals(final Object o) {

		return EqualsBuilder.reflectionEquals(this, o);
	}

}
