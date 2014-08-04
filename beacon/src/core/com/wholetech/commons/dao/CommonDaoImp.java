package com.wholetech.commons.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.wholetech.commons.exception.DaoAutoCountException;
import com.wholetech.commons.exception.DaoSQLQueryException;
import com.wholetech.commons.exception.DaoSQLUpdateException;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.util.ArrayUtil;
import com.wholetech.commons.util.DateUtil;
import com.wholetech.commons.util.SqlBuilder;
import com.wholetech.commons.web.SystemDateFilter;

public class CommonDaoImp extends HibernateDaoSupport implements CommonDao {

	/** 日志管理器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected HibernateDAOHelper daoHelper = new HibernateDAOHelper();

	protected SqlGetter sqlGetter;

	public void setSqlGetter(final SqlGetter sqlGetter) {

		this.sqlGetter = sqlGetter;
	}

	@Override
	public int executeHql(final String hqlOrKey, final Object... values) {

		// 首先刷新一下缓存，以免跟其他Hibernate操作一起被Service调用时，造成执行顺序跟调用顺序不一致。
		flush();
		final String hql = getQLByKey(hqlOrKey, values);
		final Object[] params = getFilteredParams(values);
		logger.debug("执行hql语句{}, 参数是{}.", hql, params);

		try {
			return getHibernateTemplate().bulkUpdate(hql, params);
		} catch (final DataAccessException e) {
			throw new DaoSQLUpdateException(hql, params, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public int executeSql(final String sqlOrKey, final Object... values) {

		// 首先刷新一下缓存，以免跟其他Hibernate操作一起被Service调用时，造成执行顺序跟调用顺序不一致。
		flush();
		final String sql = getQLByKey(sqlOrKey, values);
		final Object[] params = getFilteredParams(values);
		logger.debug("执行sql语句{}, 参数是{}.", sql, params);

		try {
			return (Integer) getHibernateTemplate().execute(new HibernateCallback() {

				@Override
				public Object doInHibernate(final Session session) throws HibernateException {

					final Query query = daoHelper.getQuery(session.createSQLQuery(sql), params);
					return new Integer(query.executeUpdate());
				}
			});
		} catch (final DataAccessException e) {
			throw new DaoSQLQueryException(sql, params, e);
		}
	}

	@Override
	public List<?> findListByHql(final String hqlOrKey, final Object... values) {

		final String hql = getQLByKey(hqlOrKey, values);
		final Object[] params = getFilteredParams(values);
		logger.debug("执行hql语句{}, 参数是{}.", hql, params);

		try {
			return getHibernateTemplate().find(hql, params);
		} catch (final DataAccessException e) {
			throw new DaoSQLQueryException(hql, params, e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<?> findListBySql(final String sqlOrKey, final Object... values) {

		final String sql = getQLByKey(sqlOrKey, values);
		final Object[] params = getFilteredParams(values);
		logger.debug("执行sql语句{}, 参数是{}.", sql, params);

		try {
			return (List<?>) getHibernateTemplate().execute(new HibernateCallback() {

				@Override
				public Object doInHibernate(final Session session) throws HibernateException {

					final Query query = daoHelper.getQuery(session.createSQLQuery(sql), params);
					return query.list();
				}
			});
		} catch (final DataAccessException e) {
			throw new DaoSQLQueryException(sql, params, e);
		}
	}

	@Override
	public List<?> findListByNamedQuery(final String queryName, final Object... values) {

		logger.debug("查询query名称{},参数{}.", queryName, values);
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	@Override
	public Object findOneByHql(final String hqlOrKey, final Object... values) {

		final List<?> list = findListByHql(hqlOrKey, values);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Object findOneBySql(final String sqlOrKey, final Object... values) {

		final List<?> list = findListBySql(sqlOrKey, values);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<?> findPageByHql(final Page<?> page, final String hqlOrKey, final Object... values) {

		final String hql = getQLByKey(hqlOrKey, values);
		final Object[] params = getFilteredParams(values);
		logger.debug("执行hql语句{}, 参数是{}.", hql, params);

		final SessionFactory sf = getSessionFactory();

		try {
			getHibernateTemplate().execute(new HibernateCallback() {

				@Override
				public Object doInHibernate(final Session session) throws HibernateException {

					SQLQuery countQuery = null;
					if (page.isAutoCount()) {
						final String countSql = daoHelper.getCountSql(hql, sf);
						logger.debug("将hql转成sql后，自动构造查询总条数的sql语句：[{}]。", countSql);
						countQuery = session.createSQLQuery(countSql);
					}

					return getPage(page, countQuery, session.createQuery(hql), params);
				}
			});
		} catch (final DataAccessException e) {
			throw new DaoSQLQueryException(hql, params, e);
		}
		return page;
	}

	@Override
	public boolean setTotalByCountSql(final Page page, final String sqlOrKey, final Object... values) {

		final Number count = (Number) findOneBySql(sqlOrKey, values);
		final int iCount = count.intValue();
		page.setTotalCount(iCount);

		if (iCount > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<?> findPageBySql(final Page<?> page, final String sqlOrKey, final Object... values) {

		final String sql = getQLByKey(sqlOrKey, values);
		final Object[] params = getFilteredParams(values);
		logger.debug("执行sql语句{}, 参数是{}.", sql, params);
		try {
			getHibernateTemplate().execute(new HibernateCallback() {

				@Override
				public Object doInHibernate(final Session session) throws HibernateException {

					SQLQuery countQuery = null;
					if (page.isAutoCount()) {
						final String countSql = daoHelper.buildCountQueryString(sql);
						logger.debug("查询总条数语句：[{}]。", countSql);
						countQuery = session.createSQLQuery(countSql);
					}

					return getPage(page, countQuery, session.createSQLQuery(sql), params);
				}
			});
		} catch (final DataAccessException e) {
			throw new DaoSQLQueryException(sql, params, e);
		}
		return page;
	}

	@Override
	public void flush() {

		getHibernateTemplate().flush();
	}

	@Override
	public Object get(final Class<?> pojoClass, final Serializable id) {

		return getHibernateTemplate().get(pojoClass, id);
	}

	@Override
	public Date getSystemDate() {

		Date sysDate = null;
		sysDate = SystemDateFilter.getSystemDate();
		if (sysDate != null) {
			return sysDate;
		}

		Session session = null;
		FlushMode fm = null;
		try {
			session = getSession();
			fm = session.getFlushMode();
			session.setFlushMode(FlushMode.MANUAL);
			final Query query = session.createSQLQuery(sqlGetter.getSql("sql_getSystemDate"));
			final Object object = query.list().get(0);
			sysDate = DateUtil.convertStringToDate("yyyy-MM-dd hh:mm:ss.SSS", object.toString());
			SystemDateFilter.setSystemDate(sysDate);
			return sysDate;
		} catch (final Exception e) {
			logger.error("获取系统时间出错", e);
			throw new DaoSQLQueryException(sqlGetter.getSql("sql_getSystemDate"), null, e);
		} finally {
			if (fm != null) {
				// 恢复之前的刷新模式。
				session.setFlushMode(fm);
			}
			closeSession(session);
		}
	}

	/**
	 * 关闭session
	 */
	private void closeSession(Session session) {

		if (session == null) {
			/* 在事务内才可以这样用false */
			session = getSession(false);
		}
		if (!SessionFactoryUtils.isSessionTransactional(session, getSessionFactory())) {
			releaseSession(session);
			session = null;
		}
	}

	/**
	 * 描述：通过Translator把HQL翻译成SQL
	 * 
	 * @param
	 * @return String
	 */
	protected String getCountSql(final String originalHql, final org.hibernate.SessionFactory sessionFactory) {

		final QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(originalHql, originalHql,
				Collections.EMPTY_MAP, (org.hibernate.engine.SessionFactoryImplementor) sessionFactory);

		queryTranslator.compile(Collections.EMPTY_MAP, false);

		return "select count(*) from (" + queryTranslator.getSQLString() + ") tmp_count_t";
	}

	/**
	 * 描述：获取过滤后的参数数组
	 * 
	 * @return Object[]
	 */
	private Object[] getFilteredParams(final Object... values) {

		return ArrayUtil.removeElements(values, SqlBuilder.IGNORE_FILTER);
	}

	/**
	 * 描述：获取过滤后的HQL/SQL，如果传入的key以hql或者sql打头则在配置文件中获取该SQL/HQL，
	 * 否则认为传入的key即为SQL/HQL串
	 * 
	 * @return String
	 */
	private String getQLByKey(final String key, final Object... values) {

		if (key.startsWith("hql") || key.startsWith("sql")) {
			return SqlBuilder.parseSql(sqlGetter.getSql(key), values);
		} else {
			return SqlBuilder.parseSql(key, values);
		}
	}

	@Override
	public String formatSql(final String sqlKey, final String[] arguments) {

		return sqlGetter.getSql(sqlKey, arguments);
	}

	/**
	 * 描述：获取查询结果并封装至page对象内。
	 * 
	 * @param page
	 *            传入的page对象
	 * @param countQuery
	 *            查询总数的query
	 * @param resultQuery
	 *            查询结果的query
	 * @param values
	 *            传入的参数
	 * @return Page<?>
	 */
	private Page<?> getPage(final Page<?> page, final Query countQuery, final Query resultQuery, final Object[] values) {

		if (page.isAutoCount()) {
			try {
				page.setTotalCount(daoHelper.countResult(countQuery, values));
			} catch (final Exception e) {
				throw new DaoAutoCountException(e);
			}
		}
		if (page.getTotalCount() > 0) {
			final Query query = daoHelper.getQuery(resultQuery, values);
			query.setFirstResult(page.getIdisplayStart());
			query.setMaxResults(page.getIdisplayLength());
			page.setData(query.list());
		}
		return page;
	}
}
