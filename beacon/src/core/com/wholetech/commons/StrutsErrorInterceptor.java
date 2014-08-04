/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.wholetech.commons;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class StrutsErrorInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 7563014655616490865L;

	private static final Logger logger = LoggerFactory.getLogger(StrutsErrorInterceptor.class);

	private static final String resultName = "struts-error";

	@Override
	protected String doIntercept(final ActionInvocation invocation) throws Exception {

		final ActionContext invocationContext = invocation.getInvocationContext();
		final Map<String, Object> errors = invocationContext.getConversionErrors();

		if (errors.isEmpty()) {
			return invocation.invoke();
		} else {
			StrutsErrorInterceptor.logger.error("struts拦截器中有错误出现，导向到{}", StrutsErrorInterceptor.resultName);
			return StrutsErrorInterceptor.resultName;
		}

	}

}
