package com.wholetech.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 可用Properties文件配置的Contants基类.
 * 本类既保持了Contants的static和final(静态与不可修改)特性,又拥有了可用Properties文件配置的特征,
 * 主要是应用了Java语言中静态初始化代码的特性.
 * <p/>
 * 子类可如下编写
 *
 * <pre>
 * public class Constants extends ConfigurableContants {
 *  static {
 *    init(&quot;framework.properties&quot;);
 * }
 * &lt;p/&gt;
 * public final static String ERROR_BUNDLE_KEY = getProperty(&quot;constant.error_bundle_key&quot;, &quot;errors&quot;); }
 * </pre>
 */
public class ConfigurableContants {

	protected static final Log log = LogFactory.getLog(ConfigurableContants.class);
	protected final static Properties p = new Properties();

	/**
	 * 静态读入属性文件到Properties p变量中
	 */
	protected static void init(final String propertyFileName) {

		InputStream in = null;
		try {
			in = ConfigurableContants.class.getResourceAsStream(propertyFileName);
			if (in != null) {
				ConfigurableContants.p.load(in);
			}
		} catch (final IOException e) {
			ConfigurableContants.log.error("load " + propertyFileName + " into Contants error!");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (final IOException e) {
					ConfigurableContants.log.error("close " + propertyFileName + " error!");
				}
			}
		}
	}

	/**
	 * 封装了Properties类的getProperty函数,使p变量对子类透明.
	 *
	 * @param key
	 *            property key.
	 * @param defaultValue
	 *            当使用property key在properties中取不到值时的默认值.
	 */
	protected static String getProperty(final String key, final String defaultValue) {

		return ConfigurableContants.p.getProperty(key, defaultValue);
	}
}
