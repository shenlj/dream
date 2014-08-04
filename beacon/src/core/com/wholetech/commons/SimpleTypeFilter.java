package com.wholetech.commons;

import java.util.Date;

import net.sf.json.util.PropertyFilter;

public class SimpleTypeFilter implements PropertyFilter {

	@Override
	public boolean apply(final Object source, final String name, final Object value) {

		return value != null &&
				!(value instanceof Boolean) && !(value instanceof Number) &&
				!(value instanceof String) && !(value instanceof Date);
	}
}
