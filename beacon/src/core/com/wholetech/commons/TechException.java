package com.wholetech.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.util.MessageReader;

public class TechException extends RuntimeException {

	private static final long serialVersionUID = 6070169841199286424L;
	private static Logger logger = LoggerFactory.getLogger(TechException.class);

	/** 错误代码,默认为未知错误 */
	private String errorCode = "unknown.error";

	/** 错误信息中的参数 */
	protected Object[] errorArgs = null;

	/**
	 * 错误配置信息的读取器.
	 */
	private static MessageReader errmsgReader = new MessageReader(Constants.ERROR_BUNDLE_KEY);

	public TechException(final String errorCode) {

		this.errorCode = errorCode;

		if (TechException.logger.isDebugEnabled()) {
			if (TechException.errmsgReader.getMessage(errorCode) == null) {
				TechException.logger.warn("在错误国际化消息配置文件中没有对应于[{}]的配置信息。", errorCode);
			}
			TechException.logger.debug("你构建了异常，未指定原始异常");
		}
	}

	public TechException(final String errorCode, final Object[] errorArgs) {

		this.errorCode = errorCode;
		this.errorArgs = errorArgs;

		if (TechException.logger.isDebugEnabled()) {
			if (TechException.errmsgReader.getMessage(errorCode) == null) {
				TechException.logger.warn("在错误国际化消息配置文件中没有对应于[{}]的配置信息。", errorCode);
			}
			TechException.logger.debug("你构建了异常，未指定原始异常");
		}
	}

	public TechException(final String errorCode, final Object[] errorArgs, final Throwable cause) {

		super(cause);
		this.errorCode = errorCode;
		this.errorArgs = errorArgs;

		if (TechException.logger.isDebugEnabled()) {
			if (TechException.errmsgReader.getMessage(errorCode) == null) {
				TechException.logger.warn("在错误国际化消息配置文件中没有对应于[{}]的配置信息。", errorCode);
			}
		}
	}

	public TechException(final String errorCode, final Throwable cause) {

		super(cause);
		this.errorCode = errorCode;

		if (TechException.logger.isDebugEnabled()) {
			if (TechException.errmsgReader.getMessage(errorCode) == null) {
				TechException.logger.warn("在错误国际化消息配置文件中没有对应于[{}]的配置信息。", errorCode);
			}
		}
	}

	/**
	 * 获得出错信息. 读取i18N properties文件中Error Code对应的message,并组合参数获得i18n的出错信息.
	 */
	@Override
	public String getMessage() {

		// 否则用errorCode查询Properties文件获得出错信息
		return TechException.errmsgReader.getMessage(this.errorCode, this.errorArgs);
	}

	public String getErrorCode() {

		return this.errorCode;
	}

}
