package com.wholetech.commons.ssh.extend;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
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
    candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$"), 
      new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
    candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}$"), 
      new SimpleDateFormat("yyyy-MM-dd hh:mm"));
    candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}$"), 
      new SimpleDateFormat("yyyy-MM-dd hh"));
    candidates.put(Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$"), 
      new SimpleDateFormat("yyyy-MM-dd"));
    candidates.put(Pattern.compile("^\\d{4}-\\d{2}$"), 
      new SimpleDateFormat("yyyy-MM"));
    candidates.put(Pattern.compile("^\\d{4}$"), 
      new SimpleDateFormat("yyyy"));
  }

  public DateConverter(String formatPattern) {
    if (StringUtils.isNotBlank(formatPattern))
      this.format = new SimpleDateFormat(formatPattern);
  }

  public Object convert(Class arg, Object value) {
    String dateStr;
    try {
      dateStr = (String)value;

//      if (!(StringUtils.isNotBlank(dateStr))) break label113;
//      for (Iterator localIterator = candidates.entrySet().iterator(); localIterator.hasNext(); ) { Map.Entry entry = (Map.Entry)localIterator.next();
//        if (!(((Pattern)entry.getKey()).matcher(dateStr).matches())) break label77;
//        label77: return ((SimpleDateFormat)entry.getValue()).parse(dateStr);
//      }

      logger.error("无法解析日期字符串[{}]，目前支持的日期格式有[yyyy-MM-dd hh:mm:ss][yyyy-MM-dd hh:mm][yyyy-MM-dd hh][yyyy-MM-dd][yyyy-MM][yyyy]");
    }
    catch (Exception e) {
      logger.error("解析日期字符串[{}]时出错", value, e);
    }
    label113: return null;
  }

  public static void main(String[] args) {
    DateConverter dc = new DateConverter("yyyy-MM-dd");
    Object obj = dc.convert(Date.class, "2001");
    System.out.println(obj);
  }
}