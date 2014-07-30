package com.wholetech.commons.dao;

import java.util.Map;

import org.hibernate.Criteria;

public interface FilterSetter {
	
	/**
	 * 根据过滤条件，设置对应的criteria。
	 * 可以自己决定采用什么样的过滤方法，大于、等于、小于等。
	 * 
	 * 在开发过程中可能经常使用内部匿名类的方式使用。
	 * 
	 * @param criteria   被设置的Hibernate criteria.
	 * @param filterMap  过滤条件键值对。
	 * @see {@link BaseDaoImp}
	 * @see {@link CommonDaoImp}
	 */
	public void setUpCriteria(Criteria criteria, Map<String, ?> filterMap);
	
}
