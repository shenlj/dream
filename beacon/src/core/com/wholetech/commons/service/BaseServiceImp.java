package com.wholetech.commons.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wholetech.commons.BaseObject;
import com.wholetech.commons.dao.BaseDao;
import com.wholetech.commons.exception.EntityNotExistException;
import com.wholetech.commons.util.GenericsUtil;

/**
 * Service基类
 */
abstract public class BaseServiceImp<T> implements BaseService<T> {

	/** 日志管理器 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected Class<T> entityClass = GenericsUtil.getGenericClass(getClass());

	/**
	 * 获取一个具体的DAO对象，由子类实现
	 */
	abstract protected BaseDao<T> getBaseDao();

	/*
	 * 获得一个对象，该对象是一个受管理的Hibernate对象
	 */
	public T get(Serializable id) {
		return getBaseDao().get(id);
	}

	/*
	 * 获得某一个对象，该对象是一个受管理的Hibernate对象
	 */
	public Object get(Class clazz, Serializable id) {
		logger.info("加载实体类型 {},主键{}", clazz.getName(), id);
		return getBaseDao().get(clazz, id);
	}

	/*
	 * 获得全部实体对象
	 */
	public List<T> getAll() {
		return getBaseDao().getAll();
	}

	/*
	 * 获得一个被克隆对象的副本
	 */
	public T getClone(Serializable id) {
		return getBaseDao().getClone(id);
	}

	/*
	 * 执行对象删除
	 */
	public void remove(T o) {
		logger.info("删除对象{}", o);
		if (preCheckOrHandleForDelete(o)) {
			getBaseDao().remove(o);
		}
	}

	/*
	 * 标记删除对象
	 */
	public void markDelete(T o) {
		logger.info("标记删除对象 {}", o);
		if (o instanceof BaseObject) {
			//((BaseObject) o).setDelFlag(Constants.PERSISTENCE_OPERATION_DELETE);
		}
		this.update(o);
	}
	
	/*
	 * 标记删除对象
	 */
	public void deleteLogically(T o) {
		logger.info("标记删除对象 {}", o);
		if (o instanceof BaseObject) {
			//((BaseObject) o).setDelFlag(Constants.PERSISTENCE_OPERATION_DELETE);
		}
		this.update(o);
	}

	/*
	 * 执行对象创建
	 */
	public Serializable create(T o) {
		logger.info("创建对象{}", o);
		if (preCheckForCreateOrUpdate(o)) {
			return getBaseDao().create(o);
		} else {
			return null;
		}
	}

	/*
	 * 执行对象克隆
	 * 
	 * @param id 被Clone对象的主键
	 */
	public Serializable clone(Serializable id, T o) {
		if (preCheckForCreateOrUpdate(o)) {
			afterHandleForClone(this.get(id), o);
			return getBaseDao().create(o);
		} else {
			return null;
		}
	}

	/*
	 * 执行对象更新
	 */
	public void update(T o) {
		logger.info("更新对象{}", o);
		if (preCheckForCreateOrUpdate(o)) {
			getBaseDao().update(o);
		}
	}

	/*
	 * 批量更新实体
	 */
	public void batchUpdate(Collection<T> detachedInstances) {
		logger.info("批量更新实体{}", detachedInstances);
		getBaseDao().batchUpdate(detachedInstances);
	}

	/*
	 * 批量更新实体
	 * <br/> 跟update的区别在于生成sql时，只更新那些跟数据库中有差别的字段。
	 */
	public void batchMerge(Collection<T> detachedInstances) {
		logger.info("批量更新实体", detachedInstances);
		getBaseDao().batchMerge(detachedInstances);
	}

	/*
	 * 批量保存实体，新增或者修改。
	 */
	public void batchSaveOrUpdate(Collection<T> instances) {
		logger.info("批量保存或更新实体", instances);
		getBaseDao().batchSaveOrUpdate(instances);
	}

	/*
	 * 批量保存实体
	 */
	public void batchSave(Collection<T> transientInstances) {
		logger.info("批量保存实体", transientInstances);
		getBaseDao().batchSave(transientInstances);
	}

	/*
	 * 批量删除实体
	 */
	public void batchRemove(Collection<T> persistentInstances) {
		logger.info("批量删除实体", persistentInstances);
		getBaseDao().batchRemove(persistentInstances);
	}

	public void batchRemove(String[] ids) {
		if (logger.isInfoEnabled()) {
			logger.info("构造hql批量删除，参数为{}。", ids.toString());
		}
		StringBuffer hql = new StringBuffer("delete from ");
		hql.append(entityClass.getName()).append(" where id in('").append(
				ids[0]);
		logger.debug("构造删除hql：{}", hql);
		for (int i = 1; i < ids.length; i++) {
			hql.append("','").append(ids[i]);
		}
		hql.append("')");

		this.getBaseDao().executeHql(hql.toString());
		logger.info("批量删除结束。");
	}

	public void batchRemoveSafely(String[] ids) {
		if (logger.isInfoEnabled()) {
			logger.info("使用逐个加载，逐个删除的方式进行批量删除，参数为{}。", ids.toString());
		}
		for (String id : ids) {
			T t = this.getBaseDao().get(id);
			if (t == null) {
				throw new EntityNotExistException(entityClass, id);
			}
			this.getBaseDao().remove(t);
		}
		logger.info("安全批量删除结束。");
	}

	/*
	 * 无参数形式，SQL执行无分页查询
	 */
	public List<?> findListByHql(String hqlOrKey, Object... values) {
		logger.info("使用{}进行hql查询。", hqlOrKey);
		List<?> rslt = getBaseDao().findListByHql(hqlOrKey, values);
		logger.info("查询出{}条数据。", rslt.size());
		return rslt;
	}

	/*
	 * 无分页查询
	 */
	public List<?> findListBySql(String sqlOrKey, Object... values) {
		logger.info("使用{}进行sql查询。", sqlOrKey);
		List<?> rslt = getBaseDao().findListBySql(sqlOrKey, values);
		logger.info("查询出{}条数据。", rslt.size());
		return rslt;
	}

	/*
	 * 执行新建或更新之前的条件检查，诸如唯一性约束条件之类的检查。由子类视具体情况进行覆写
	 * 
	 * @param o 待检查对象
	 */
	protected boolean preCheckForCreateOrUpdate(T o) {
		return true;
	}

	/**
	 * 执行删除之前的条件检查，诸如对象外键关联约束之类的检查。由子类视具体情况进行覆写
	 * 
	 * @param o
	 *            待检查对象
	 * @return 检查是否通过
	 */
	protected boolean preCheckOrHandleForDelete(T o) {
		return true;
	}

	/**
	 * 执行Clone以后关联Collection对象的处理，一般是取原始对象的Collection属性进行循环追加到一个新的Collection类型对象
	 * ，然后赋给新Clone的对象
	 * 由子类视具体情况进行覆写
	 * 
	 * @param orgObj
	 *            被Clone的原始对象
	 * @param newObj
	 *            新建的Clone对象
	 */
	protected void afterHandleForClone(T orgObj, T newObj) {

	}

	/*
	 * 检测字段值是否已存在，excludeSelf标识是否排除自己,一般编辑对象检测时需要排除自己
	 * 
	 * @return true=检测值可用,false=检测值违反唯一约束
	 */
	public boolean checkUnique(String name, String value, String id) {
		logger.debug("通过[{}={}]来检查唯一性。", name, value);

		T object = null;
		try {
			object = getBaseDao().findUnique(name, value);
		} catch (EntityNotExistException e) {
			logger.info(e.getMessage());
			return false;
		}

		if (object == null) {
			return true;
		} else {
			if (StringUtils.isNotEmpty(id)) {
				BaseObject be = (BaseObject) object;
				if (id.equals(be.getId() + "")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * 
	 * 描述：验证entityForm是否满足唯一性校验
	 * 
	 * @param
	 * @return boolean
	 */
	public boolean checkUnique(String[] name, Object[] value, String id) {
		if (logger.isInfoEnabled()) {
			StringBuilder sb = new StringBuilder("通过[");
			for (int i = 0; i < name.length; i++) {
				sb.append(name[i]).append('=').append(value[i]);
			}
			sb.append("]来检查唯一性。");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < name.length; i++) {
			map.put(name[i], value[i]);
		}
		List<T> list = getBaseDao().findList(map);
		if (list.size() == 0) {
			return true;
		} else if (list.size() > 1) {
			return false;
		} else {
			if (StringUtils.isNotEmpty(id)) {
				BaseObject be = (BaseObject) list.get(0);
				if (id.equals(be.getId() + "")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * Discription:TODO[获取数据库时间]
	 * 
	 * @return
	 * @author zhangll
	 * @update [日期YYYY-MM-DD] [更改人姓名][变更描述]
	 */
	public Date getSystemDate() {

		return this.getBaseDao().getSystemDate();
	}
}