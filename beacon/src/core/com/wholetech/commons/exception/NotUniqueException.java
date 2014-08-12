package com.wholetech.commons.exception;

public class NotUniqueException extends RuntimeException {

	private static final long serialVersionUID = 1229216678876695353L;

	private String filter;

	public NotUniqueException() {
	}

	public NotUniqueException(String filter) {
		this.filter = filter;
	}

	@Override
	public String getMessage() {
		return "查询条件[" + this.filter + "查出来多条数据";
	}

}
