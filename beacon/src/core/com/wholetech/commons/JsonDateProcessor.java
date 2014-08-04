package com.wholetech.commons;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonDateProcessor implements JsonValueProcessor {

	private final DateFormat format;

	public JsonDateProcessor(final String format) {

		this.format = new SimpleDateFormat(format);
	}

	@Override
	public Object processArrayValue(final Object value, final JsonConfig jsonConfig) {

		return "";
	}

	@Override
	public Object processObjectValue(final String key, final Object value, final JsonConfig jsonConfig) {

		if (value == null) {
			return null;
		}

		return format.format((Date) value);
	}
}
