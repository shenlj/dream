package com.wholetech.commons.util;

import java.util.List;
import java.util.Map;

public interface ConfigurationHelper {

  public String getTableName(Class clazz);

  public List getPkColumnName(Class clazz);

  public String getColumnName(Class clazz, String propertyName);

  public boolean isAnyType(Class clazz, String propertyName);

  public Map getAnyType(Class clazz, String propertyName);

  public String getAnyColumnName(Class clazz, String propertyName);
}
