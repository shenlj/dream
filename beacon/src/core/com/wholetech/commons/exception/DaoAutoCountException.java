package com.wholetech.commons.exception;

public class DaoAutoCountException extends RuntimeException {

	public DaoAutoCountException() {

	}

	public DaoAutoCountException(final Exception e) {

		super(e);
	}

	@Override
	public String getMessage() {

		return "系统使用默认规则构建查询count语句执行出错，你可以尝试自己写一个count语句进行查询。";
	}

}
