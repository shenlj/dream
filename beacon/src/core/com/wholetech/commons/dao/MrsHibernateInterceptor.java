package com.wholetech.commons.dao;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.Constants;
import com.wholetech.commons.ssh.extend.SpringContextHolder;
import com.wholetech.commons.util.SessionInfoHolder;

public class MrsHibernateInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = -7105620303945395902L;

	private final Logger logger = LoggerFactory.getLogger(MrsHibernateInterceptor.class);

	// 获取当前session的信息，用于设置
	private SessionInfoHolder sessionInfoHolder;

	public void setSessionInfoHolder(final SessionInfoHolder sessionInfoHolder) {

		this.sessionInfoHolder = sessionInfoHolder;
	}

	@Override
	public void afterTransactionCompletion(final Transaction tx) {

		this.logger.debug("afterTransactionCompletion");
		super.afterTransactionCompletion(tx);
	}

	@Override
	public void beforeTransactionCompletion(final Transaction tx) {

		this.logger.debug("beforeTransactionCompletion");
		super.beforeTransactionCompletion(tx);
	}

	@Override
	public void onDelete(final Object entity, final Serializable id, final Object[] state,
			final String[] propertyNames, final Type[] types) {

		this.logger.debug("onDelete");
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(final Object entity, final Serializable id, final Object[] currentState,
			final Object[] previousState,
			final String[] propertyNames, final Type[] types) {

		this.logger.debug("onFlushDirty");
		if (entity instanceof BaseStandardEntity && ((BaseStandardEntity) entity).isAdded()) {
			return false;
		}
		boolean isLogicDelete = false;
		for (int i = 0; i < propertyNames.length; i++) {
			if ("delFlag".equals(propertyNames[i])) {
				if (Constants.PERSISTENCE_OPERATION_DELETE.equals(currentState[i])
						&& !Constants.PERSISTENCE_OPERATION_DELETE.equals(previousState[i])) {
					isLogicDelete = true;
					break;
				}
			}
		}
		// 判断是否是逻辑删除
		if (isLogicDelete) {
			setCommonProperties(currentState, propertyNames, Constants.PERSISTENCE_OPERATION_DELETE);
		} else {
			setCommonProperties(currentState, propertyNames, Constants.PERSISTENCE_OPERATION_UPDATE);
		}

		return true;
	}

	@Override
	public boolean onSave(final Object entity, final Serializable id, final Object[] state,
			final String[] propertyNames, final Type[] types) {

		this.logger.debug("onSave");
		if (entity instanceof BaseStandardEntity) {
			((BaseStandardEntity) entity).setAdded(true);
		} else {
			return false;
		}

		setCommonProperties(state, propertyNames, Constants.PERSISTENCE_OPERATION_CREATE);
		return true;
	}

	private void setCommonProperties(final Object[] state, final String[] propertyNames, final String operateType) {

		for (int i = 0; i < propertyNames.length; i++) {
			if ("delFlag".equals(propertyNames[i])) {
				state[i] = operateType;
			}

			if ("operateTime".equals(propertyNames[i])) {
				state[i] = ((CommonDao) SpringContextHolder.getBean("commonDao")).getSystemDate();
			}

			if ("operaterCode".equals(propertyNames[i])) {
				// 获取请求对象，并作为参数传递到sessionInfoHolder各方法中。
				// 以便将来可以设定request从什么地方获取，这里默认从ServletActionContext获取。
				final HttpServletRequest request = ServletActionContext.getRequest();

				state[i] = this.sessionInfoHolder.getLoginId(request);
			}
		}
	}
}
