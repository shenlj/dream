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
 * 
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
		init(basePath);
	}

	/**
	 * 设置绝对路径，一般在jdk环境下运行时，而配置文件又不在相应的环境下，需要配置绝对路径。
	 * 
	 * @param basePath
	 *            要设置的 basePath。
	 */
	public static void setBasePath(String basePath) {
		PropertyXmlMgr.basePath = basePath;
	}

	/**
	 * 装载配置好的所有系统配置文件，只在第一次调用时执行
	 * 
	 * @param path
	 *            web应用的基础路径也就是WEB-INF的路径。
	 */
	public static synchronized void init(String path) {
		try {
			if (path != null) {
				basePath = path;
			} else {
				// 取配置文件基础路径。
				if (basePath == null) {
					File file = null;
					URL url = null;

					basePath = System.getProperty(CONFIG_FILE);
					if (basePath != null) {
						logger.info("系统参数配置文件{}的路径：{}", FILENAME, basePath);
					} else {
						// 定位classes目录。
						url = PropertyXmlMgr.class.getClass().getResource("PropertyXmlMgr.class");
						logger.info("系统参数配置文件{}路径：{}", FILENAME, url);

						if (url != null) {
							basePath = url.getPath();

							// 定位/WEB-INF/classes/目录。
							if (basePath.indexOf("WEB-INF") > 0) {
								basePath = basePath.substring(0, basePath.length() - 8);
							} else {
								basePath = null;
								url = null;
								logger.info("系统参数配置文件{}不在web容器路径。", FILENAME);
							}
						} else {
							basePath = getFilePath(url, FILENAME);
						}
					}

					if (basePath != null) {
						String filePath = basePath + FILENAME;
						file = new File(filePath);

						if (file.exists()) {
							// 在WEB-INF/目录下。
							readConfigFile(filePath);
							flag = true;
						} else {
							// 在WEB-INF/classes/目录下。
							filePath = basePath + classes + FILENAME;
							if (new File(filePath).exists()) {
								readConfigFile(filePath);
								flag = true;
							} else {
								logger.info("配置文件{}不存在", FILENAME);
							}
						}
					} else {
						logger.info("未找到配置文件{}", FILENAME);
					}
				}
			}
		} catch (Exception e) {
			logger.error("在初始化系统配置{}时发生错误：", FILENAME, e);
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
	public static String getString(String alias, String key) {
		return (String) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_STRING);
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
	public static Boolean getBoolean(String alias, String key) {
		return (Boolean) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BOOLEAN);
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
	public static Byte getByte(String alias, String key) {
		return (Byte) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BYTE);
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
	public static Short getShort(String alias, String key) {
		return (Short) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_SHORT);
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
	public static Integer getInteger(String alias, String key) {
		return (Integer) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_INTEGER);
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
	public static BigInteger getBigInteger(String alias, String key) {
		return (BigInteger) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BIGINTEGER);
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
	public static Long getLong(String alias, String key) {
		return (Long) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_LONG);
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
	public static Float getFloat(String alias, String key) {
		return (Float) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_FLOAT);
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
	public static BigDecimal getBigDecimal(String alias, String key) {
		return (BigDecimal) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_BIGDECIMAL);
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
	public static Double getDouble(String alias, String key) {
		return (Double) getObject(alias, key, PropertyXmlMgr.DATA_TYPE_DOUBLE);
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
	public static Iterator<String> getKeys(String alias) {
		return (Iterator<String>) getObject(alias, null, PropertyXmlMgr.DATA_TYPE_KEYS);
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
	public static boolean containsKey(String alias, String key) {
		Iterator<String> keys = getKeys(alias);

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
	public static String[] getStringArray(String alias, String key, String delimiter) {
		try {
			Configuration configuration = getConfiguration(alias);

			if (configuration != null) {
				String str = configuration.getString(key);

				if (str != null && !"".equals(str.trim())) {
					String[] strArr = str.split(delimiter);
					return strArr;
				}
			}

			return null;
		} catch (Exception e) {
			logger.error("获取参数配置alias=" + alias + ",key=" + key + ",delimiter=" + delimiter + "出错：", e);
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
	public static List<String> getList(String alias, String key, String delimiter) {
		try {
			String[] strArr = getStringArray(alias, key, delimiter);

			if (strArr == null) {
				return null;
			}

			return Arrays.asList(strArr);
		} catch (Exception e) {
			logger.error("获取参数配置alias=" + alias + ",key=" + key + ",delimiter=" + delimiter + "出错：", e);
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
	public static void refreshConfigFile(String absolutePath) throws Exception {
		String alias = null;
		String lowerCaseAbsolutePath = absolutePath.toLowerCase();

		for (Map.Entry<String, String> entry : fileMap.entrySet()) {
			String lowerCaseFileName = entry.getValue().toLowerCase();

			if (lowerCaseFileName.endsWith(lowerCaseAbsolutePath)) {
				alias = entry.getKey();
				break;
			}
		}

		if (alias == null) {
			throw new java.lang.IllegalArgumentException("文件{" + absolutePath + "}没有加入到配置管理中。");
		}

		loadAndCacheConfiguration(alias, absolutePath);
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
	private static String getFilePath(URL url, String fileName) {
		String path = null;
		boolean flag = true;

		if (url == null) {
			// classes目录下。
			if (Thread.currentThread().getContextClassLoader() != null) {
				url = Thread.currentThread().getContextClassLoader().getResource(fileName);
				logger.info("获取到上下文url为{}", url);

				// web应用根目录。
				if (url == null) {
					url = Thread.currentThread().getContextClassLoader().getResource("WEB-INF/");
					logger.info("获取到上下文url为{}", url);

					if (url == null) {
						url = Thread.currentThread().getContextClassLoader().getResource("/");
						logger.info("获取到上下文url为{}", url);
					}

					flag = false;
				}
			}
		}

		if (url == null) {
			url = PropertyXmlMgr.class.getClass().getResource("/" + fileName);
			logger.info("获取到上下文url为{}", url);
		}

		if (url == null) {
			if (PropertyXmlMgr.class.getClass().getClassLoader() != null) {
				url = PropertyXmlMgr.class.getClass().getClassLoader().getResource(fileName);
				logger.info("获取到上下文url为{}", url);
			}
		}

		if (url != null) {
			path = url.getPath();

			if (basePath == null && flag) {
				path = path.substring(0, path.length() - (classes.length() + fileName.length()));
			}

			if (path.indexOf("WEB-INF/classes/") > 0 && !flag) {
				path = path.substring(0, path.length() - 8);
			}

			if (path.indexOf("WEB-INF/classes") > 0 && !flag) {
				path = path.substring(0, path.length() - 7);
			}
		} else {
			logger.error("获取到上下文url为{}", url);
		}

		logger.info("获取到上下文对应的根路径basePath为{}", path);

		return path;
	}

	/**
	 * 读取并分析jeaw-config.xml配置文件，并装载其中包含的所有配置文件。
	 * 
	 * @param filePath
	 *            jeaw-config.xml配置文件路径。
	 * @throws Exception
	 */
	private static void readConfigFile(String filePath) throws Exception {
		// 加载配置文件。
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(filePath);
		Node node = doc.getFirstChild();
		NodeList nodeList = node.getChildNodes();

		// 分析配置文件，并将文件加载到Configuration中。
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node fileNode = (Node) nodeList.item(i);
			if (fileNode != null) {
				if (fileNode.getAttributes() != null && fileNode.getNodeName().equalsIgnoreCase("properties")) {
					Node fileNameAttrNode = fileNode.getAttributes().getNamedItem("fileName");
					String fileName = fileNameAttrNode.getTextContent();
					String tempPath = getFilePath(null, fileName);

					if (tempPath == null) {
						logger.error("配置文件{}没有找到", fileName);
					} else {
						String alias = fileNode.getTextContent();
						fileMap.put(alias, tempPath);

						logger.info("读取配置文件{}，加载后保存到别名{}中。", tempPath, alias);
						loadAndCacheConfiguration(alias, tempPath);
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
	private static void loadAndCacheConfiguration(String alias, String filePath) throws Exception {
		Configuration configuration = null;

		String lowerCaseFilePath = filePath.toLowerCase();
		if (lowerCaseFilePath.endsWith("properties")) {
			configuration = loadPropertiesConfiguration(filePath);
		} else if (lowerCaseFilePath.endsWith("xml")) {
			configuration = loadXMLConfiguration(filePath);
		}

		map.put(alias, configuration);
	}

	/**
	 * 获取指定properties配置文件中的配置文件对象。
	 * 
	 * @param filePath
	 *            文件路径。
	 * @return properties配置文件对象。
	 * @throws Exception
	 */
	private static synchronized Configuration loadPropertiesConfiguration(String filePath) {
		try {
			PropertiesConfiguration configuration = new PropertiesConfiguration();

			// 关闭获取列表的方法，获取列表自己通过分割字符串实现。
			configuration.setDelimiterParsingDisabled(true);
			configuration.load(filePath);

			return configuration;
		} catch (ConfigurationException e) {
			logger.error("在读取配置文件{}时出错，请检查配置文件中的配置项。", filePath);
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
	private static synchronized XMLConfiguration loadXMLConfiguration(String filePath) {
		try {
			XMLConfiguration configuration = new XMLConfiguration(filePath);

			// 关闭获取列表的方法，获取列表自己通过分割字符串实现。
			configuration.setDelimiterParsingDisabled(true);
			configuration.load(filePath);

			return configuration;
		} catch (ConfigurationException e) {
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
	private static Configuration getConfiguration(String alias) throws Exception {
		// 未加载，则重新加载。
		if (!flag) {
			if (basePath != null) {
				String fileName = basePath + "/" + FILENAME;

				File file = new File(fileName);
				if (file.exists()) {
					logger.info("开始读取配置文件{}。", fileName);
					readConfigFile(fileName);
					flag = true;
				} else {
					logger.error("开始读取配置文件，但配置文件{}不存在。", fileName);
				}
			}
		}

		// 从内存中获取配置文件对象。
		Configuration configuration = map.get(alias);

		// 如果未获取到，则重新加载。
		if (configuration == null) {
			basePath = null;
			init(null);
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
	private static Object getObject(String alias, String key, int dataType) {
		try {
			Configuration configuration = getConfiguration(alias);

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
		} catch (Exception e) {
			logger.error("获取参数配置alias=" + alias + ",key=" + key + ",dataType=" + dataType + "出错：", e);
			return null;
		}
	}
}