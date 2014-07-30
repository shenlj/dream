package com.wholetech.commons.exception;

import java.io.Serializable;

public class EntityNotExistException extends RuntimeException {

	private static final long serialVersionUID = 1625670497132159785L;
	private Serializable entityId;
	private Class<?> clazz;

	public EntityNotExistException() {

	}

	public EntityNotExistException(Class<?> clazz, Serializable id) {
		this.entityId = id;
		this.clazz = clazz;
	}

	@Override
	public String getMessage() {
		StringBuilder builder = new StringBuilder("实体[");
		builder.append(clazz.getSimpleName()).append('@').append(this.entityId)
				.append("]不存在。");
		return builder.toString();
	}

}
