package com.wholetech.commons.util;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wholetech.commons.Constants;
import com.wholetech.commons.exception.ConfigException;

/**
 * 系统所有配置文件的读取工具类，配置文件的根默认配置在jeaw-config.xml中，也可以通过环境变量Constants.CONFIG_FILE设置。<br>
 * 提供了读取配置文件并转换为指定格式的相应方法，如getString、getInteger、getLong、getDouble、getStringArray
 * 、getList等。<br>
 * 配置文件也可以动态加载，直接调用init可以重新加载所有的配置，调用refreshConfigFile可以加载某个指定的配置。<br>
 * 为了判断配置文件是否正确加载，提供了getKeys方法获取某个配置文件中的key列表，containsKey判断是否有该key存在。<br>
 */
public class PropertyXmlMgr {

	/**
	 */
	public static final String CONFIG_FILE = Constants.CONFIG_FILE;

	/** 日志管理器。 */
	private static Logger logger = LoggerFactory.getLogger(PropertyXmlMgr.class);

	/**
	 * 用来缓存文件配置对象。
	 */
	private static Map<String, Configuration> map = new HashMap<String, Configuration>();

	/**
	 * 用来缓存文件名和别名的对应关系。
	 */
	private static Map<String, String> fileMap = new HashMap<String, String>();

	/**
	 * web应用的基础路径也就是WEB-INF的路径。
	 */
	private static String basePath = null;

	private static boolean flag = false;

	/**
	 * 用来保存系统各配置文件路径。
	 */
	private static final String FILENAME = Constants.XML_FILENAME;

	private static final String classes = "classes/";

	/** 配置文件中配置项对应的数据类型。 */
	private static final int DATA_TYPE_BOOLEAN = 10;
	private static final int DATA_TYPE_BYTE = 20;
	private static final int DATA_TYPE_SHORT = 21;
	private static final int DATA_TYPE_INTEGER = 22;
	private static final int DATA_TYPE_BIGINTEGER = 23;
	private static final int DATA_TYPE_LONG = 24;
	private static final int DATA_TYPE_FLOAT = 30;
	private static final int DATA_TYPE_BIGDECIMAL = 31;
	private static final int DATA_TYPE_DOUBLE = 32;
	private static final int DATA_TYPE_STRING = 40;
	private static final int DATA_TYPE_KEYS = 90;

	static {
		// 初始化，装载所有配置文件。
		PropertyXmlMgr.init(PropertyXmlMgr.basePath);
	}

	/**
	 * 设置绝对路径，一般在jdk环境下运行时，而配置文件又不在相应的环境下，需要配置绝对路径。
	 *
	 * @param basePath
	 *            要设置的 basePath。
	 */
	public static void setBasePath(final String basePath) {

		PropertyXmlMgr.basePath = basePath;
	}

	/**
	 * 装载配置好的所有系统配置文件，只在第一次调用时执行
	 *
	 * @param path
	 *            web应用的基础路径也就是WEB-INF的路径。
	 */
	public static synchronized void init(final String path) {

		try {
			if (path != null) {
				PropertyXmlMgr.basePath = path;
			} else {
				// 取配置文件基础路径。
				if (PropertyXmlMgr.basePath == null) {
					File file = null;
					URL url = null;

					PropertyXmlMgr.basePath = System.getProperty(PropertyXmlMgr.CONFIG_FILE);
					if (PropertyXmlMgr.basePath != null) {
						PropertyXmlMgr.logger
								.info("系统参数配置文件{}的路径：{}", PropertyXmlMgr.FILENAME, PropertyXmlMgr.basePath);
					} else {
						// 定位classes目录。
						url = PropertyXmlMgr.class.getClass().getResource("PropertyXmlMgr.class");
						PropertyXmlMgr.logger.info("系统参数配置文件{}路径：{}", PropertyXmlMgr.FILENAME, url);

						if (url != null) {
							PropertyXmlMgr.basePath = url.getPath();

							// 定位/WEB-INF/classes/目录。
							if (PropertyXmlMgr.basePath.indexOf("WEB-INF") > 0) {
								PropertyXmlMgr.basePath = PropertyXmlMgr.basePath.substring(0,
										PropertyXmlMgr.basePath.length() - 8);
							} else {
								PropertyXmlMgr.basePath = null;
								url = null;
								PropertyXmlMgr.logger.info("系统参数配置文件{}不在web容器路径。", PropertyXmlMgr.FILENAME);
							}
						} else {
							PropertyXmlMgr.basePath = PropertyXmlMgr.getFilePath(url, PropertyXmlMgr.FILENAME);
						}
					}

					if (PropertyXmlMgr.basePath != null) {
						String filePath = PropertyXmlMgr.basePath + PropertyXmlMgr.FILENAME;
						file = new File(filePath);

						if (file.exists()) {
							// 在WEB-INF/目录下。
							PropertyXmlMgr.readConfigFile(filePath);
							PropertyXmlMgr.flag = true;
						} else {
							// 在WEB-INF/classes/目录下。
							filePath = PropertyXmlMgr.basePath + PropertyXmlMgr.classes + PropertyXmlMgr.FILENAME;
							if (new File(filePath).exists()) {
								PropertyXmlMgr.readConfigFile(filePath);
								PropertyXmlMgr.flag = true;
							} else {
								PropertyXmlMgr.logger.info("配置文件{}不存在", PropertyXmlMgr.FILENAME);
							}
						}
					} else {
						PropertyXmlMgr.logger.info("未找到配置文件{}", PropertyXmlMgr.FILENAME);
					}
				}
			}
		} catch (final Exception e) {
			PropertyXmlMgr.logger.error("在初始化系统配置{}时发生错误：", PropertyXmlMgr.FILENAME, e);
		}
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以String类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static String getString(final String alias, final String key) {

		return (String) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_STRING);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Boolean类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Boolean getBoolean(final String alias, final String key) {

		return (Boolean) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BOOLEAN);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Byte类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Byte getByte(final String alias, final String key) {

		return (Byte) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BYTE);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Short类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Short getShort(final String alias, final String key) {

		return (Short) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_SHORT);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Integer类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Integer getInteger(final String alias, final String key) {

		return (Integer) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_INTEGER);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以BigInteger类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static BigInteger getBigInteger(final String alias, final String key) {

		return (BigInteger) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BIGINTEGER);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Long类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Long getLong(final String alias, final String key) {

		return (Long) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_LONG);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Float类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Float getFloat(final String alias, final String key) {

		return (Float) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_FLOAT);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以BigDecimal类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static BigDecimal getBigDecimal(final String alias, final String key) {

		return (BigDecimal) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BIGDECIMAL);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Double类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Double getDouble(final String alias, final String key) {

		return (Double) PropertyXmlMgr.getObject(alias, key, PropertyXmlMgr.DATA_TYPE_DOUBLE);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Iterator类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static Iterator<String> getKeys(final String alias) {

		return (Iterator<String>) PropertyXmlMgr.getObject(alias, null, PropertyXmlMgr.DATA_TYPE_KEYS);
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key的配置参数值，并以Iterator类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static boolean containsKey(final String alias, final String key) {

		final Iterator<String> keys = PropertyXmlMgr.getKeys(alias);

		if (keys != null) {
			while (keys.hasNext()) {
				if (keys.next().equals(key)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key，分隔符为delimiter的配置参数值，并以String[]类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @param delimiter
	 *            分隔符。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static String[] getStringArray(final String alias, final String key, final String delimiter) {

		try {
			final Configuration configuration = PropertyXmlMgr.getConfiguration(alias);

			if (configuration != null) {
				final String str = configuration.getString(key);

				if (str != null && !"".equals(str.trim())) {
					final String[] strArr = str.split(delimiter);
					return strArr;
				}
			}

			return null;
		} catch (final Exception e) {
			PropertyXmlMgr.logger.error("获取参数配置alias=" + alias + ",key=" + key + ",delimiter=" + delimiter + "出错：", e);
			return null;
		}
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key，分隔符为delimiter的配置参数值，并以List<String>类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @param delimiter
	 *            分隔符。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	public static List<String> getList(final String alias, final String key, final String delimiter) {

		try {
			final String[] strArr = PropertyXmlMgr.getStringArray(alias, key, delimiter);

			if (strArr == null) {
				return null;
			}

			return Arrays.asList(strArr);
		} catch (final Exception e) {
			PropertyXmlMgr.logger.error("获取参数配置alias=" + alias + ",key=" + key + ",delimiter=" + delimiter + "出错：", e);
			return null;
		}
	}

	/**
	 * 刷新参数absolutePath指定配置文件的配置属性。
	 *
	 * @param absolutePath
	 *            文件绝对路径。
	 * @throws Exception
	 */
	public static void refreshConfigFile(final String absolutePath) throws Exception {

		String alias = null;
		final String lowerCaseAbsolutePath = absolutePath.toLowerCase();

		for (final Map.Entry<String, String> entry : PropertyXmlMgr.fileMap.entrySet()) {
			final String lowerCaseFileName = entry.getValue().toLowerCase();

			if (lowerCaseFileName.endsWith(lowerCaseAbsolutePath)) {
				alias = entry.getKey();
				break;
			}
		}

		if (alias == null) {
			throw new java.lang.IllegalArgumentException("文件{" + absolutePath + "}没有加入到配置管理中。");
		}

		PropertyXmlMgr.loadAndCacheConfiguration(alias, absolutePath);
	}

	/**
	 * 根据配置文件的相对路径，获取配置文件的绝对路径。
	 *
	 * @param url
	 *            绝对根路径对应的url。
	 * @param fileName
	 *            配置文件相对路径，相对classpath的路径，如conf/sysmgr.properties。
	 * @return 配置文件的绝对路径。
	 */
	private static String getFilePath(URL url, final String fileName) {

		String path = null;
		boolean flag = true;

		if (url == null) {
			// classes目录下。
			if (Thread.currentThread().getContextClassLoader() != null) {
				url = Thread.currentThread().getContextClassLoader().getResource(fileName);
				PropertyXmlMgr.logger.info("获取到上下文url为{}", url);

				// web应用根目录。
				if (url == null) {
					url = Thread.currentThread().getContextClassLoader().getResource("WEB-INF/");
					PropertyXmlMgr.logger.info("获取到上下文url为{}", url);

					if (url == null) {
						url = Thread.currentThread().getContextClassLoader().getResource("/");
						PropertyXmlMgr.logger.info("获取到上下文url为{}", url);
					}

					flag = false;
				}
			}
		}

		if (url == null) {
			url = PropertyXmlMgr.class.getClass().getResource("/" + fileName);
			PropertyXmlMgr.logger.info("获取到上下文url为{}", url);
		}

		if (url == null) {
			if (PropertyXmlMgr.class.getClass().getClassLoader() != null) {
				url = PropertyXmlMgr.class.getClass().getClassLoader().getResource(fileName);
				PropertyXmlMgr.logger.info("获取到上下文url为{}", url);
			}
		}

		if (url != null) {
			path = url.getPath();

			if (PropertyXmlMgr.basePath == null && flag) {
				path = path.substring(0, path.length() - (PropertyXmlMgr.classes.length() + fileName.length()));
			}

			if (path.indexOf("WEB-INF/classes/") > 0 && !flag) {
				path = path.substring(0, path.length() - 8);
			}

			if (path.indexOf("WEB-INF/classes") > 0 && !flag) {
				path = path.substring(0, path.length() - 7);
			}
		} else {
			PropertyXmlMgr.logger.error("获取到上下文url为{}", url);
		}

		PropertyXmlMgr.logger.info("获取到上下文对应的根路径basePath为{}", path);

		return path;
	}

	/**
	 * 读取并分析jeaw-config.xml配置文件，并装载其中包含的所有配置文件。
	 *
	 * @param filePath
	 *            jeaw-config.xml配置文件路径。
	 * @throws Exception
	 */
	private static void readConfigFile(final String filePath) throws Exception {

		// 加载配置文件。
		final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder builder = factory.newDocumentBuilder();
		final Document doc = builder.parse(filePath);
		final Node node = doc.getFirstChild();
		final NodeList nodeList = node.getChildNodes();

		// 分析配置文件，并将文件加载到Configuration中。
		for (int i = 0; i < nodeList.getLength(); i++) {
			final Node fileNode = nodeList.item(i);
			if (fileNode != null) {
				if (fileNode.getAttributes() != null && fileNode.getNodeName().equalsIgnoreCase("properties")) {
					final Node fileNameAttrNode = fileNode.getAttributes().getNamedItem("fileName");
					final String fileName = fileNameAttrNode.getTextContent();
					final String tempPath = PropertyXmlMgr.getFilePath(null, fileName);

					if (tempPath == null) {
						PropertyXmlMgr.logger.error("配置文件{}没有找到", fileName);
					} else {
						final String alias = fileNode.getTextContent();
						PropertyXmlMgr.fileMap.put(alias, tempPath);

						PropertyXmlMgr.logger.info("读取配置文件{}，加载后保存到别名{}中。", tempPath, alias);
						PropertyXmlMgr.loadAndCacheConfiguration(alias, tempPath);
					}
				}
			}
		}
	}

	/**
	 * 加载并缓存文件配置对象。
	 *
	 * @param alias
	 *            文件配置对象对应的别名。
	 * @param filePath
	 *            文件配置对象对应的文件路径。
	 * @throws Exception
	 */
	private static void loadAndCacheConfiguration(final String alias, final String filePath) throws Exception {

		Configuration configuration = null;

		final String lowerCaseFilePath = filePath.toLowerCase();
		if (lowerCaseFilePath.endsWith("properties")) {
			configuration = PropertyXmlMgr.loadPropertiesConfiguration(filePath);
		} else if (lowerCaseFilePath.endsWith("xml")) {
			configuration = PropertyXmlMgr.loadXMLConfiguration(filePath);
		}

		PropertyXmlMgr.map.put(alias, configuration);
	}

	/**
	 * 获取指定properties配置文件中的配置文件对象。
	 *
	 * @param filePath
	 *            文件路径。
	 * @return properties配置文件对象。
	 * @throws Exception
	 */
	private static synchronized Configuration loadPropertiesConfiguration(final String filePath) {

		try {
			final PropertiesConfiguration configuration = new PropertiesConfiguration();

			// 关闭获取列表的方法，获取列表自己通过分割字符串实现。
			configuration.setDelimiterParsingDisabled(true);
			configuration.load(filePath);

			return configuration;
		} catch (final ConfigurationException e) {
			PropertyXmlMgr.logger.error("在读取配置文件{}时出错，请检查配置文件中的配置项。", filePath);
			throw new ConfigException("在读取配置文件" + filePath + "时出错，请检查配置文件中的配置项。");
		}
	}

	/**
	 * 获取指定xml配置文件中的配置文件对象。
	 *
	 * @param filePath
	 *            文件路径。
	 * @return xml配置文件对象。
	 * @throws Exception
	 */
	private static synchronized XMLConfiguration loadXMLConfiguration(final String filePath) {

		try {
			final XMLConfiguration configuration = new XMLConfiguration(filePath);

			// 关闭获取列表的方法，获取列表自己通过分割字符串实现。
			configuration.setDelimiterParsingDisabled(true);
			configuration.load(filePath);

			return configuration;
		} catch (final ConfigurationException e) {
			throw new ConfigException("在读取配置文件" + filePath + "时出错，请检查配置文件中的配置项。");
		}
	}

	/**
	 * 获取configuration，如果以前没有加载，则重新从文件中读取，并加载到内存。否则，直接从内存中读取。
	 *
	 * @param alias
	 *            配置文件别名，可为jeaw-config.xml中直接配置的名称，也可为conf/framework.
	 *            properties中配置的别名。
	 * @return 配置文件对象。
	 * @throws Exception
	 */
	private static Configuration getConfiguration(final String alias) throws Exception {

		// 未加载，则重新加载。
		if (!PropertyXmlMgr.flag) {
			if (PropertyXmlMgr.basePath != null) {
				final String fileName = PropertyXmlMgr.basePath + "/" + PropertyXmlMgr.FILENAME;

				final File file = new File(fileName);
				if (file.exists()) {
					PropertyXmlMgr.logger.info("开始读取配置文件{}。", fileName);
					PropertyXmlMgr.readConfigFile(fileName);
					PropertyXmlMgr.flag = true;
				} else {
					PropertyXmlMgr.logger.error("开始读取配置文件，但配置文件{}不存在。", fileName);
				}
			}
		}

		// 从内存中获取配置文件对象。
		final Configuration configuration = PropertyXmlMgr.map.get(alias);

		// 如果未获取到，则重新加载。
		if (configuration == null) {
			PropertyXmlMgr.basePath = null;
			PropertyXmlMgr.init(null);
		}

		return configuration;
	}

	/**
	 * 读取配置文件别名为alias，配置项键值为key，数据类型为dataType的配置参数值，并以Object类型返回。
	 *
	 * @param alias
	 *            配置文件别名。
	 * @param key
	 *            配置项键值。
	 * @param dataType
	 *            数据类型。
	 * @return 配置参数的值，如果未取到，则返回null。
	 */
	private static Object getObject(final String alias, final String key, final int dataType) {

		try {
			final Configuration configuration = PropertyXmlMgr.getConfiguration(alias);

			if (configuration != null) {
				switch (dataType) {
					case PropertyXmlMgr.DATA_TYPE_STRING:
						return configuration.getString(key, null);
					case PropertyXmlMgr.DATA_TYPE_BOOLEAN:
						return configuration.getBoolean(key, null);
					case PropertyXmlMgr.DATA_TYPE_INTEGER:
						return configuration.getInteger(key, null);
					case PropertyXmlMgr.DATA_TYPE_LONG:
						return configuration.getLong(key, null);
					case PropertyXmlMgr.DATA_TYPE_FLOAT:
						return configuration.getFloat(key, null);
					case PropertyXmlMgr.DATA_TYPE_DOUBLE:
						return configuration.getDouble(key, null);
					case PropertyXmlMgr.DATA_TYPE_KEYS:
						return configuration.getKeys();
					case PropertyXmlMgr.DATA_TYPE_BYTE:
						return configuration.getByte(key, null);
					case PropertyXmlMgr.DATA_TYPE_SHORT:
						return configuration.getShort(key, null);
					case PropertyXmlMgr.DATA_TYPE_BIGINTEGER:
						return configuration.getBigInteger(key, null);
					case PropertyXmlMgr.DATA_TYPE_BIGDECIMAL:
						return configuration.getBigDecimal(key, null);
					default:
						return null;
				}
			}

			return null;
		} catch (final Exception e) {
			PropertyXmlMgr.logger.error("获取参数配置alias=" + alias + ",key=" + key + ",dataType=" + dataType + "出错：", e);
			return null;
		}
	}
}
