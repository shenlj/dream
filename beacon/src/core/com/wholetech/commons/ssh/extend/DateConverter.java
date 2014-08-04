package com.wholetech.commons.ssh.extend;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.Converter;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateConverter
		implements Converter
{

	private static final Logger logger = LoggerFactory.getLogger(DateConverter.class);
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static Map<Pattern, SimpleDateFormat> candidates = new LinkedHashMap();

	static
	{
		DateConverter.candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$"),
				new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		DateConverter.candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}$"),
				new SimpleDateFormat("yyyy-MM-dd hh:mm"));
		DateConverter.candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}$"),
				new SimpleDateFormat("yyyy-MM-dd hh"));
		DateConverter.candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"),
				new SimpleDateFormat("yyyy-MM-dd"));
		DateConverter.candidates.put(Pattern.compile("^\\d{4}-\\d{2}$"),
				new SimpleDateFormat("yyyy-MM"));
		DateConverter.candidates.put(Pattern.compile("^\\d{4}$"),
				new SimpleDateFormat("yyyy"));
	}

	public DateConverter(final String formatPattern) {

		if (StringUtils.isNotBlank(formatPattern)) {
			format = new SimpleDateFormat(formatPattern);
		}
	}

	@Override
	public Object convert(final Class arg, final Object value) {

		try {

			// if (!(StringUtils.isNotBlank(dateStr))) break label113;
			// for (Iterator localIterator = candidates.entrySet().iterator(); localIterator.hasNext(); ) { Map.Entry
			// entry = (Map.Entry)localIterator.next();
			// if (!(((Pattern)entry.getKey()).matcher(dateStr).matches())) break label77;
			// label77: return ((SimpleDateFormat)entry.getValue()).parse(dateStr);
			// }

			DateConverter.logger
					.error("无法解析日期字符串[{}]，目前支持的日期格式有[yyyy-MM-dd hh:mm:ss][yyyy-MM-dd hh:mm][yyyy-MM-dd hh][yyyy-MM-dd][yyyy-MM][yyyy]");
		} catch (final Exception e) {
			DateConverter.logger.error("解析日期字符串[{}]时出错", value, e);
		}
		label113: return null;
	}

	public static void main(final String[] args) {

		final DateConverter dc = new DateConverter("yyyy-MM-dd");
		final Object obj = dc.convert(Date.class, "2001");
		System.out.println(obj);
	}
}
