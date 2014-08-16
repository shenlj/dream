package com.wholetech.commons;

import java.util.Date;

import net.sf.json.util.PropertyFilter;

public class SimpleTypeFilter implements PropertyFilter {

  public boolean apply(Object source, String name, Object value) {

    return value != null &&
        !(value instanceof Boolean) && !(value instanceof Number) &&
        !(value instanceof String) && !(value instanceof Date);
  }
}
