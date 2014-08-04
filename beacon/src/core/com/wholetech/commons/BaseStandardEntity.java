package com.wholetech.commons;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实体类的父类。
 * 该类要求实体必须数据库中有以下字段：
 * 自然主键 - id</li>
 * 操作时间 - operateTime
 * 操作类型 - OperateType
 * 操作员编码 - OperatorCode
 * 操作员姓名 - OperatorName
 * <p>
 * 实体类要重写toString/equals/hashCode三个方法。
 * <p>
 */
public class BaseStandardEntity extends BaseObject {

	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	private String operatorCode;
	private String operatorName;
	private String operateType;
	private Date operateTime;

	public Date getOperateTime() {

		return operateTime;
	}

	public void setOperateTime(final Date operateTime) {

		this.operateTime = operateTime;
	}

	@Override
	public String toString() {

		logger.warn("You should overwrite 'toString' method for {} so that it's readable when log this class",
				this.getClass().getSimpleName());

		return this.getClass().getSimpleName() + '@' + getId();
	}

	@Override
	public boolean equals(final Object o) {

		if (this == o) {
			return true;
		}
		if (!(o instanceof BaseStandardEntity)) {
			return false;
		}
		final BaseStandardEntity object = (BaseStandardEntity) o;

		// 如果两个id都为空，则认为是不等的对象
		if (getId() == null && object.getId() == null) {
			return false;
		}

		return new EqualsBuilder().append(getId(), object.getId()).isEquals();
	}

	public String getOperatorCode() {

		return this.operatorCode;
	}

	public void setOperatorCode(final String operatorCode) {

		this.operatorCode = operatorCode;
	}

	public String getOperatorName() {

		return this.operatorName;
	}

	public void setOperatorName(final String operatorName) {

		this.operatorName = operatorName;
	}

	public String getOperateType() {

		return this.operateType;
	}

	public void setOperateType(final String operateType) {

		this.operateType = operateType;
	}
}
