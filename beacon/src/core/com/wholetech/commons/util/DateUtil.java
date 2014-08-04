package com.wholetech.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.wholetech.commons.Constants;

/**
 * 日期Util类。
 * 转换日期时，如果不指定日期格式，则使用默认的日期格式。<br>
 * 默认的日期格式可以在文件messages_zh_CN.properties中通过属性date.default_format来指定。不指定的情况下默认使用
 * yyyy-MM-dd作为日期格式，日期时间格式默认使用yyyy-MM-dd HH:mm:ss
 */
public class DateUtil extends org.apache.commons.lang.time.DateUtils {

	/** 格式yyyy-MM-dd */
	public static final String FMT_DATE_YYYY_MM_DD = "yyyy-MM-dd";

	/** 格式yyyyMMdd */
	public static final String FMT_DATE_YYYYMMDD = "yyyyMMdd";

	/** 格式yyMMdd */
	public static final String FMT_DATE_YYMMDD = "yyMMdd";

	/** 格式yyyy */
	public static final String FMT_DATE_YYYY = "yyyy";

	/** 格式yyMM */
	public static final String FMT_DATE_YYMM = "yyMM";

	/** 格式yyyyMM */
	public static final String FMT_DATE_YYYYMM = "yyyyMM";

	/** 格式yyyyMMddHHmmss */
	public static final String FMT_DATE_YYYYMMDDHHmmss = "yyyyMMddHHmmss";

	/** 格式yyMMddHHmm */
	public static final String FMT_DATE_YYMMDDHHmm = "yyMMddHHmm";

	/** 格式HHmm */
	public static final String FMT_DATE_HHmm = "HHmm";

	private static String defaultDatePattern = "yyyy-MM-dd";

	static {
		// 尝试试从messages_zh_Cn.properties中获取defaultDatePattner.
		try {
			final Locale locale = LocaleContextHolder.getLocale();
			DateUtil.defaultDatePattern = ResourceBundle.getBundle(
					Constants.MESSAGE_BUNDLE_KEY, locale).getString("date.default_format");
		} catch (final MissingResourceException mse) {
			// do nothing
		}
	}

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {

		return DateUtil.defaultDatePattern;
	}

	public static String getDateTimePattern() {

		return DateUtil.getDatePattern() + " HH:mm:ss";
	}

	/**
	 * 返回预设Format的当前应用服务器运行的日期字符串。
	 */
	public static String getToday() {

		final Date today = new Date();
		return DateUtil.format(today);
	}

	/**
	 * 返回Format的当前应用服务器运行的日期时间字符串。
	 */
	public static String getTodayTime() {

		final Date today = new Date();
		return DateUtil.format(today, DateUtil.getDateTimePattern());
	}

	/**
	 * 使用默认Format格式化Date成字符串。
	 */
	public static String format(final Date date) {

		return date == null ? "" : DateUtil.format(date, DateUtil.getDatePattern());
	}

	/**
	 * 使用参数Format格式化Date成字符串。如果参数date为null，则返回空字符串""。
	 *
	 * @param date
	 *            待转换日期。
	 * @param pattern
	 *            指定的转换格式。
	 */
	public static String format(final Date date, final String pattern) {

		return date == null ? "" : new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 使用预设格式将字符串解析为Date。
	 */
	public static Date parse(final String strDate) throws ParseException {

		return StringUtils.isBlank(strDate) ? null : DateUtil.parse(strDate,
				DateUtil.getDatePattern());
	}

	/**
	 * 使用指定格式Format将字符串解析为Date。如果参数strDate为null，则返回null。
	 *
	 * @param strDate
	 *            待解析日期字符串。
	 * @param pattern
	 *            指定的转换格式。
	 */
	public static Date parse(final String strDate, final String pattern)
			throws ParseException {

		return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(
				pattern).parse(strDate);
	}

	/**
	 * 在日期上增加数个整月。
	 *
	 * @param date
	 *            待操作日期。
	 * @param n
	 *            要增加的月数。
	 */
	public static Date addMonth(final Date date, final int n) {

		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	private static Log log = LogFactory.getLog(DateUtil.class);
	private static String timePattern = "HH:mm";

	// ~ Methods
	// ================================================================

	/**
	 * 将一个日期转换成默认的日期格式。
	 *
	 * @param aDate
	 *            待转换日期。
	 * @return 格式化后的日期。
	 */
	public static final String getDate(final Date aDate) {

		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(DateUtil.getDatePattern());
			returnValue = df.format(aDate);
		}

		return returnValue;
	}

	/**
	 * 将一个字符串按照指定的格式转换成日期。
	 *
	 * @param aMask
	 *            格式。
	 * @param strDate
	 *            转换的日期字符串。
	 * @return 转换后的日期对象。
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(final String aMask, final String strDate)
			throws ParseException {

		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);

		if (DateUtil.log.isDebugEnabled()) {
			DateUtil.log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
		}

		try {
			date = df.parse(strDate);
		} catch (final ParseException pe) {
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}

	/**
	 * 使用默认的时间格式(HH:mm)转换字符串。
	 *
	 * @param theTime
	 *            待转换的日期对象。
	 * @return 格式化后的字符串。
	 */
	public static String getTimeNow(final Date theTime) {

		return DateUtil.getDateTime(DateUtil.timePattern, theTime);
	}

	/**
	 * 将指定日期按照指定格式转换成字符串。
	 *
	 * @param aMask
	 *            指定的格式。
	 * @param aDate
	 *            待转换日期对象。
	 * @return 格式化的日期字符串。
	 */
	public static final String getDateTime(final String aMask, final Date aDate) {

		SimpleDateFormat df = null;
		String returnValue = "";

		if (aDate == null) {
			DateUtil.log.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * 按照默认的日期格式将指定日期对象转换成字符串。
	 *
	 * @param aDate
	 *            待转换的日期。
	 * @return 转换后的字符串。
	 */
	public static final String convertDateToString(final Date aDate) {

		return DateUtil.getDateTime(DateUtil.getDatePattern(), aDate);
	}

	/**
	 * 使用DateUtil默认的格式来转换日期字符串为日期对象。
	 *
	 * @param strDate
	 *            日期字符串表示。
	 * @return 转换后的日期对象。
	 * @throws ParseException
	 */
	public static Date convertStringToDate(final String strDate)
			throws ParseException {

		Date aDate = null;

		try {
			if (DateUtil.log.isDebugEnabled()) {
				DateUtil.log.debug("converting date with pattern: " + DateUtil.getDatePattern());
			}

			aDate = DateUtil.convertStringToDate(DateUtil.getDatePattern(), strDate);
		} catch (final ParseException pe) {
			DateUtil.log.error("Could not convert '" + strDate
					+ "' to a date, throwing exception", pe);
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return aDate;
	}

	/**
	 * 日期格式化方法。
	 *
	 * @param date
	 *            要格式化的日期。
	 * @param nFmt
	 *            格式化样式，使用<code>DateUtil.FMT_DATE_YYYY_MM_DD</code>等来指定。
	 * @see DateUtil#FMT_DATE_YYYY_MM_DD
	 * @return
	 */
	public static String formatDate(final Date date, final String nFmt) {

		final SimpleDateFormat fmtDate = new SimpleDateFormat(nFmt);
		return fmtDate.format(date);
	}

	/**
	 * 获取当前应用服务器日期时间的字符串表示，格式由{@link DateUtil#FMT_DATE_YYYYMMDDHHmmss}来指定。
	 */
	public static String getCurrentDateTime() {

		return DateUtil.formatDate(new Date(), DateUtil.FMT_DATE_YYYYMMDDHHmmss);
	}

	/**
	 * 获取当前应用服务器时间的字符串表示，格式由{@link DateUtil#FMT_DATE_HHmm}来指定。
	 */
	public static String getCurrentTime() {

		return DateUtil.formatDate(new Date(), DateUtil.FMT_DATE_HHmm);
	}

	/**
	 * 获取当前应用服务器日期的字符串表示，格式由{@link DateUtil#FMT_DATE_YYYYMMDD}来指定。
	 */
	public static String getCurrentDate() {

		return DateUtil.formatDate(new Date(), DateUtil.FMT_DATE_YYYYMMDD);
	}

	/**
	 * 获取当前应用服务器日期的字符串表示，格式由{@link DateUtil#FMT_DATE_YYYYMM}来指定。
	 */
	public static String getCurrentYYYYMM() {

		return DateUtil.formatDate(new Date(), DateUtil.FMT_DATE_YYYYMM);
	}

	/**
	 * 获取当前应用服务器日期的字符串表示，格式由{@link DateUtil#FMT_DATE_YYYY}来指定。
	 */
	public static String getCurrentYYYY() {

		return DateUtil.formatDate(new Date(), DateUtil.FMT_DATE_YYYY);
	}

	/**
	 * 将一个日期时间的字符串表示从inFormat格式转换为outFormat格式。
	 *
	 * @param dStr
	 *            日期时间的字符串。
	 * @param inFormat
	 *            原始格式。
	 * @param outFormat
	 *            转换后的格式。
	 * @return
	 */
	public static String convert(final String dStr, final String inFormat, final String outFormat) {

		final SimpleDateFormat sdf = new SimpleDateFormat(inFormat);
		Date d = null;
		try {
			d = sdf.parse(dStr);
		} catch (final ParseException pe) {
			System.out.println(pe.getMessage());
		}

		return DateUtil.dateToString(d, outFormat);

	}

	/**
	 * 按给出的格式将输入的日期转换为字符串。
	 *
	 * @param currdate
	 *            输入的date对象。
	 * @param strFormat
	 *            约定的格式。
	 * @return 按输入时间及约定格式返回的字符串
	 */
	public static final String dateToString(final java.util.Date currdate,
			final String strFormat) {

		String returnDate = "";
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
			if (currdate == null) {
				return returnDate;
			} else {
				returnDate = sdf.format(currdate);
			}
		} catch (final NullPointerException e) {
		}
		return returnDate;
	}

	/**
	 * 判读一个字符串是否是日期格式。
	 * <p>
	 * 判断的规则是：
	 * <li>如果长度为4，判断是否是yyyy格式。</li>
	 * <li>如果长度为6，判断是否是yyyyMM格式。</li>
	 * <li>如果长度为8，判断是否是yyyyMMdd格式。</li>
	 * <li>如果长度为10，判断是否是yyyyMMddHH格式。</li>
	 * <li>如果长度为12，判断是否是yyyyMMddHHmm格式。</li>
	 *
	 * @param currdate
	 *            输入的时间
	 * @param strFormat
	 *            约字的格式
	 * @return 按输入时间及约定格式返回的字符串
	 */
	public static final boolean checkIsDate(final String strDate) {

		Date returnDate = null;
		try {
			if (strDate.length() == 4) {
				returnDate = DateUtil.parse(strDate, "yyyy");
			} else if (strDate.length() == 6) {
				returnDate = DateUtil.parse(strDate, "yyyyMM");
			} else if (strDate.length() == 8) {
				returnDate = DateUtil.parse(strDate, "yyyyMMdd");
			} else if (strDate.length() == 10) {
				returnDate = DateUtil.parse(strDate, "yyyyMMddHH");
			} else if (strDate.length() == 12) {
				returnDate = DateUtil.parse(strDate, "yyyyMMddHHmm");
			}
		} catch (final Exception e) {
			return false;
		}
		if (returnDate != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取某一个日期在一年中是第几周的日期。
	 * 需要传递两个参数，第一个参数是要计算的日期，第二个参数指定周几作为一周的开始。
	 * 可以参见Calendar.SATURDAY等常量。
	 *
	 * @return
	 */
	public static int getWeekInYear(final Date date, final int startOfWeek) {

		final Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		cld.setFirstDayOfWeek(startOfWeek);

		return cld.get(Calendar.WEEK_OF_YEAR);
	}

}
