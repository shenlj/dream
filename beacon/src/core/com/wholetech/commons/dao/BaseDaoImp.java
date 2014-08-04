package com.wholetech.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.wholetech.commons.BaseObject;
import com.wholetech.commons.exception.ConfigException;
import com.wholetech.commons.exception.NotUniqueException;
import com.wholetech.commons.query.Page;
import com.wholetech.commons.util.GenericsUtil;

/**
 * Hibernate Entity Dao基类。
 * 本实现中的所有方法都是针对泛型T的。
 *
 * @see {@link CommonDaoImp}
 */
abstract public class BaseDaoImp<T> extends CommonDaoImp implements BaseDao<T> {

	private static final String BATCHTYPE_SAVE = "save";
	private static final String BATCHTYPE_UPDATE = "update";
	private static final String BATCHTYPE_MERGE = "merge";
	private static final String BATCHTYPE_REMOVE = "remove";
	private static final String BATCHTYPE_SAVEORUPDATE = "saveOrUpdate";

	/** Dao所管理的Entity类型. */
	protected Class<T> entityClass;

	protected Class<T> getEntityClass() {

		return this.entityClass;
	}

	@SuppressWarnings("unchecked")
	public BaseDaoImp() {

		this.entityClass = GenericsUtil.getGenericClass(getClass());
	}

	@Override
	@SuppressWarnings("unchecked")
	public T get(final Serializable id) {

		return getHibernateTemplate().get(getEntityClass(), id);
	}

	@Override
	public T getClone(final Serializable id) {

		final T o = get(id);
		getHibernateTemplate().evict(o);
		return o;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> getAll() {

		logger.debug("获取所有{}实体", this.getEntityClass().getName());
		return getHibernateTemplate().loadAll(getEntityClass());
	}

	@Override
	public Serializable create(final T pojo) {

		getHibernateTemplate().save(pojo);
		return getIdentifier(pojo);
	}

	@Override
	public Serializable saveOrUpdate(final T pojo) {

		logger.debug("创建或更新实体{}", pojo.getClass().getName());
		getHibernateTemplate().saveOrUpdate(pojo);
		return getIdentifier(pojo);
	}

	@Override
	public void update(final T pojo) {

		getHibernateTemplate().update(pojo);
	}

	@Override
	public void remove(final T pojo) {

		getHibernateTemplate().delete(pojo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void batchUpdate(final Collection detachedInstances) {

		this.executeBatch(detachedInstances, BaseDaoImp.BATCHTYPE_UPDATE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void batchSave(final Collection transientInstances) {

		this.executeBatch(transientInstances, BaseDaoImp.BATCHTYPE_SAVE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void batchMerge(final Collection detachedInstances) {

		this.executeBatch(detachedInstances, BaseDaoImp.BATCHTYPE_MERGE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void batchSaveOrUpdate(final Collection instances) {

		this.executeBatch(instances, BaseDaoImp.BATCHTYPE_SAVEORUPDATE);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void batchRemove(final Collection persistentInstances) {

		this.executeBatch(persistentInstances, BaseDaoImp.BATCHTYPE_REMOVE);
	}

	private void executeBatch(final Collection instances, final String batchType) {

		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(final Session session)
					throws HibernateException {

				HibernateDAOHelper.checkWriteOperationAllowed(
						getHibernateTemplate(), session);
				if (!instances.isEmpty()) {
					instances.size();
					// int i = 0;
					for (final Object pojo : instances) {
						if (BaseDaoImp.BATCHTYPE_SAVE.equals(batchType)) {
							session.save(pojo);
						} else if (BaseDaoImp.BATCHTYPE_UPDATE.equals(batchType)) {
							session.update(pojo);
						} else if (BaseDaoImp.BATCHTYPE_MERGE.equals(batchType)) {
							session.merge(pojo);
						} else if (BaseDaoImp.BATCHTYPE_SAVEORUPDATE.equals(batchType)) {
							session.saveOrUpdate(pojo);
						} else if (BaseDaoImp.BATCHTYPE_REMOVE.equals(batchType)) {
							session.refresh(pojo);
							session.delete(pojo);
						}

						session.flush();
					}
				}
				return null;
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public T findUnique(final String name, final Object value) {

		logger.debug("查询属性{},值{}.", name, value);
		try {
			return (T) getHibernateTemplate().execute(
					new HibernateCallback() {

						@Override
						public Object doInHibernate(final Session session)
								throws HibernateException {

							final Criteria criteria = session
									.createCriteria(getEntityClass());
							criteria.add(Restrictions.eq(name, value));
							return criteria.uniqueResult();
						}
					});
		} catch (final HibernateException e) {
			throw new NotUniqueException(name + "=" + value);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findList(final String name, final Object value) {

		logger.debug("查询属性{},值{}.", name, value);
		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(final Session session)
					throws HibernateException {

				final Criteria criteria = session.createCriteria(getEntityClass());
				criteria.add(Restrictions.eq(name, value));
				return criteria.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findList(final Map<String, Object> filter) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(final Session session)
					throws HibernateException {

				final Criteria criteria = session.createCriteria(getEntityClass());
				for (final Map.Entry<String, Object> entry : filter.entrySet()) {
					criteria.add(Restrictions.eq(entry.getKey(), entry
							.getValue()));
				}
				return criteria.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findList(final Map<String, ?> filterMap,
			final FilterSetter filterSetter) {

		return getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(final Session session)
					throws HibernateException {

				final Criteria criteria = session.createCriteria(getEntityClass());
				filterSetter.setUpCriteria(criteria, filterMap);
				return criteria.list();
			}
		});
	}

	@Override
	@SuppressWarnings("unchecked")
	public Page<T> findPage(final Page<T> page, final Map<String, ?> filterMap,
			final FilterSetter filterSetter) {

		getHibernateTemplate().execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(final Session session)
					throws HibernateException {

				final Criteria criteria = session.createCriteria(getEntityClass());
				if (filterSetter != null) {
					filterSetter.setUpCriteria(criteria, filterMap);
				} else {
					for (final Map.Entry<String, ?> entry : filterMap.entrySet()) {
						criteria.add(Restrictions.eq(entry.getKey(), entry
								.getValue()));
					}
				}
				return getPage(page, criteria);
			}
		});
		return page;
	}

	@Override
	public Page<T> findPage(final Page<T> page, final Map<String, ?> filterMap) {

		return this.findPage(page, filterMap, null);
	}

	/**
	 * 描述：获取身份标识
	 * 
	 * @param
	 * @return Serializable
	 */
	private Serializable getIdentifier(final T pojo) {

		if (pojo instanceof BaseObject) {
			return ((BaseObject) pojo).getId();
		}
		// 如果未知实体的主键，则获取pojo的身份标识名称
		final String keyName = getSessionFactory().getClassMetadata(pojo.getClass())
				.getIdentifierPropertyName();
		if (keyName != null) {
			// 根据身份标识名称在pojo中获取相应的value
			try {
				return (Serializable) PropertyUtils.getProperty(pojo, keyName);
			} catch (final Exception e) {
				throw new ConfigException("通过实体类" + pojo.getClass().getSimpleName() + "的hibernate配置无法获取id，请检查配置。");
			}

		}
		return null;
	}

	/**
	 * 描述：根据page、criteria获取分页对象
	 * 
	 * @param
	 * @return Page<T>
	 */
	private Page<T> getPage(final Page<T> page, final Criteria criteria) {

		if (page.isAutoCount()) {
			page.setTotalCount(daoHelper.countCriteriaResult(criteria));
		}
		if (page.getTotalCount() > 0) {
			daoHelper.setPageParameter(criteria, page);
			page.setData(criteria.list());
		} else {
			page.setData(Collections.EMPTY_LIST);
		}
		return page;
	}
}
