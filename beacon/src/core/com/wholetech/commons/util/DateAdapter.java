package com.wholetech.commons.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public String marshal(final Date v) throws Exception {

		return this.dateFormat.format(v);
	}

	@Override
	public Date unmarshal(final String v) throws Exception {

		return this.dateFormat.parse(v);
	}

}
