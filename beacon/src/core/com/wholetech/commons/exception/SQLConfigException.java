package com.wholetech.commons.exception;

public class SQLConfigException extends ConfigException {

	private String sqlKey;

	public SQLConfigException() {

	}

	public SQLConfigException(final String sqlKey) {

		this.sqlKey = sqlKey;
	}

	@Override
	public String getMessage() {

		return "SQL配置有问题，请检查键值[" + sqlKey + "]配置";
	}

}
