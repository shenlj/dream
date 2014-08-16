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

  private PersistentClass getPersistentClass(Class clazz) {

    synchronized (HibernateConfigurationHelper.class) {
      PersistentClass pc = hibernateConf.getClassMapping(clazz.getName());
      if (pc == null) {
        hibernateConf = hibernateConf.addClass(clazz);
        pc = hibernateConf.getClassMapping(clazz.getName());
      }
      return pc;
    }
  }

  public String getTableName(Class clazz) {

    return getPersistentClass(clazz).getTable().getName();
  }

  public List getPkColumnName(Class clazz) {

    List list = new ArrayList();
    for (Object column : getPersistentClass(clazz).getTable().getPrimaryKey().getColumns()) {
      list.add(((Column) column).getName());
    }
    return list;
  }

  public String getColumnName(Class clazz, String propertyName) {

    String columnName = null;
    try {
      Iterator<Column> it = getPersistentClass(clazz).getProperty(
          propertyName).getColumnIterator();
      while (it.hasNext()) {
        columnName = it.next().getName();
      }
      return columnName;
    } catch (Exception e) {
      return null;
    }
  }

  public String getAnyColumnName(Class clazz, String propertyName) {

    String columnName = null;
    try {
      Iterator<Column> it = getPersistentClass(clazz).getProperty(
          propertyName).getColumnIterator();
      while (it.hasNext()) {
        columnName = it.next().getName();
        break;
      }
      return columnName;
    } catch (Exception e) {
      return null;
    }
  }

  public Map getAnyType(Class clazz, String propertyName) {

    Property property = getPersistentClass(clazz).getProperty(propertyName);
    return ((org.hibernate.mapping.Any) property.getValue())
        .getMetaValues();
  }

  public boolean isAnyType(Class clazz, String propertyName) {

    Property property = getPersistentClass(clazz).getProperty(propertyName);
    if (property.getValue() instanceof org.hibernate.mapping.Any) {
      return true;
    }
    return false;
  }
}
