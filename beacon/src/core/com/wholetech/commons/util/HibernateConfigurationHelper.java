package com.wholetech.commons.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

public class HibernateConfigurationHelper implements ConfigurationHelper {

	private static Configuration hibernateConf = new Configuration();

	private PersistentClass getPersistentClass(final Class clazz) {

		synchronized (HibernateConfigurationHelper.class) {
			PersistentClass pc = HibernateConfigurationHelper.hibernateConf.getClassMapping(clazz.getName());
			if (pc == null) {
				HibernateConfigurationHelper.hibernateConf = HibernateConfigurationHelper.hibernateConf.addClass(clazz);
				pc = HibernateConfigurationHelper.hibernateConf.getClassMapping(clazz.getName());
			}
			return pc;
		}
	}

	@Override
	public String getTableName(final Class clazz) {

		return getPersistentClass(clazz).getTable().getName();
	}

	@Override
	public List getPkColumnName(final Class clazz) {

		final List list = new ArrayList();
		for (final Object column : getPersistentClass(clazz).getTable().getPrimaryKey().getColumns()) {
			list.add(((Column) column).getName());
		}
		return list;
	}

	@Override
	public String getColumnName(final Class clazz, final String propertyName) {

		String columnName = null;
		try {
			final Iterator<Column> it = getPersistentClass(clazz).getProperty(
					propertyName).getColumnIterator();
			while (it.hasNext()) {
				columnName = it.next().getName();
			}
			return columnName;
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public String getAnyColumnName(final Class clazz, final String propertyName) {

		String columnName = null;
		try {
			final Iterator<Column> it = getPersistentClass(clazz).getProperty(
					propertyName).getColumnIterator();
			while (it.hasNext()) {
				columnName = it.next().getName();
				break;
			}
			return columnName;
		} catch (final Exception e) {
			return null;
		}
	}

	@Override
	public Map getAnyType(final Class clazz, final String propertyName) {

		final Property property = getPersistentClass(clazz).getProperty(propertyName);
		return ((org.hibernate.mapping.Any) property.getValue())
				.getMetaValues();
	}

	@Override
	public boolean isAnyType(final Class clazz, final String propertyName) {

		final Property property = getPersistentClass(clazz).getProperty(propertyName);
		if (property.getValue() instanceof org.hibernate.mapping.Any) {
			return true;
		}
		return false;
	}
}
