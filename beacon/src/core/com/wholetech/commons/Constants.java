package com.wholetech.commons;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.wholetech.commons.util.ConfigurableContants;

/**
 * 提供工程基本需要的常量。
 * 
 * <p>
 * 常量是可配置的，通过文件 "{classpath}/conf/framework.properties"文件来配置。
 * 如果在配置文件中没有配置对应的常量值，则使用默认常量值。
 */
public class Constants extends ConfigurableContants {

	// 静态初始化读入framework.properties中的设置
	static {
		init("/conf/framework.properties");
	}

	/**
     * <li>默认编码格式。</li>
     * <li>配置键值：framework.generic.defaultEncoding</li>
     * <li>默认值：UTF-8</li>
	 */
	public static final String DEFAULT_ENCODING = getProperty("framework.generic.defaultEncoding", "UTF-8");
	
	/**
     * <li>作用：国际化消息的配置文件</li>
     * <li>配置键值：constant.message_bundle_key</li>
     * <li>默认值：i18n/messages</li>
	 */
	public final static String MESSAGE_BUNDLE_KEY = getProperty("constant.message_bundle_key", "i18n/messages");

	/**
     * <li>作用：错误消息的配置文件</li>
     * <li>配置键值：constant.error_bundle_key</li>
     * <li>默认值：i18n/errors</li>
	 */
	public final static String ERROR_BUNDLE_KEY = getProperty("constant.error_bundle_key", "i18n/errors");

	/**
     * <li>作用：页面list的默认分页大小</li>
     * <li>配置键值：constant.default_page_size</li>
     * <li>默认值：15</li>
	 */
	public final static int DEFAULT_PAGE_SIZE = Integer.parseInt(getProperty("constant.default_page_size", 
			                                                     String.valueOf(15)));

	/**
     * <li>作用：更新多条记录时，批量提交数据库的数量 </li>
     * <li>配置键值：constant.default_batch_size</li>
     * <li>默认值：30</li>
	 */
	public final static int DEFAULT_BATCH_SIZE = Integer.parseInt(getProperty("constant.default_batch_size", 
                                                                  String.valueOf(30)));

	
	/**
     * <li>作用：登录用户在Session的键值</li>
     * <li>配置键值：constant.user_in_session</li>
     * <li>默认值：loginUser</li>
	 */
	public final static String USER_IN_SESSION = getProperty("constant.user_in_session", "loginUser");

	/**
     * <li>作用：后台传递给后台的自然主键名称，往往作为复选框的名称</li>
     * <li>配置键值：constant.itemlist</li>
     * <li>默认值：itemlist</li>
	 */
	public final static String DEFAULT_ITEM_LIST = getProperty("constant.itemlist", "itemlist");
	
	
	
	/**
     * <li>作用：框架主键的名称</li>
     * <li>配置键值：constant.id</li>
     * <li>默认值：id</li>
	 */
	public final static String DEFAULT_ID_NAME = getProperty("constant.id", "id");
	
	/** 对应于Ext treepanel，传递节点id */
	public final static String DEFAULT_TREENODE_NAME = "node";

	/**
     * 当前部署系统的文件夹分隔符。
	 */
	public static final String FILE_SEPARATOR = System.getProperty("file.separator");

	/**
     * 当前部署系统的登录用户的临时文件夹，以文件分隔符结尾。
	 */
	public static final String USER_HOME = System.getProperty("user.home") + FILE_SEPARATOR;

	/**
     * <li>配置键值：xmlfileName</li>
	 */
	public static final String XML_FILENAME = getProperty("xmlfileName", "beacon-config.xml");

	/**
     * <li>配置键值：CONFIG_FILE</li>
	 */
	public static final String CONFIG_FILE = getProperty("CONFIG_FILE", "com.jeaw.config");
	
	/** 无效状态的字符串表达 */
	public static final String STATUS_INVALID = "0";

	/** 有效状态的字符串表达 */
	public static final String STATUS_VALID = "1";

	/** 有效/无效状态的转义映射，"无效"，"有效" */
	public static Map<String, String> STATUS_MAP = new HashMap<String, String>();
	static {
		STATUS_MAP.put(STATUS_VALID, "有效");
		STATUS_MAP.put(STATUS_INVALID, "无效");
	}

	/** 实体查询标志的字符串表达 */
	public static final String PERSISTENCE_OPERATION_QUERY = getProperty("entity.query", "Q");
	
	/** 实体新增标识的字符串表达 */
	public static final String PERSISTENCE_OPERATION_CREATE = getProperty("entity.add", "A");

	/** 实体更新标识的字符串表达 */ 
	public static final String PERSISTENCE_OPERATION_UPDATE = getProperty("entity.update", "U");

	/** 实体删除标识的字符串表达 */ 
	public static final String PERSISTENCE_OPERATION_DELETE = getProperty("entity.delete", "D");

	/** 实体操作状态的转义 */ 
	public static Map<String, String> OPERATION_MAP = new HashMap<String, String>();
	static {
		OPERATION_MAP.put(PERSISTENCE_OPERATION_QUERY, "查询");
		OPERATION_MAP.put(PERSISTENCE_OPERATION_CREATE, "创建");
		OPERATION_MAP.put(PERSISTENCE_OPERATION_UPDATE, "更新");
		OPERATION_MAP.put(PERSISTENCE_OPERATION_DELETE, "删除");
	}

	/** boolean中true的字符串表示 */
	public static final String BOOLEAN_TRUE = "1";
	
	/** boolean中false的字符串表示 */
	public static final String BOOLEAN_FALSE = "0";

	/** boolean的转义map */
	public static Map<String, String> BOOLEAN_MAP = new LinkedHashMap<String, String>();
	static {
		BOOLEAN_MAP.put(BOOLEAN_TRUE, "是");
		BOOLEAN_MAP.put(BOOLEAN_FALSE, "否");
	}

	/** 默认cache provider的配置键值，参见spring配置文件中对cache provider的配置 */
	public final static String CACHE_PROVIDER = "cache.provider_class";
	
	/**
     * <li>默认的初始化密码</li>
     * <li>配置键值：initial.password</li> 
     * <li>默认值：000000</li>
	 */
	public static final String INITIAL_PASSWORD = getProperty("initial.password", "000000");
	
	/**
     * <li>默认的日期时间格式化字符串</li>
     * <li>配置键值：default.datetime.format</li>
     * <li>默认值：yyyy-MM-dd HH:mm:ss</li>
	 */
	public static final String DEFAULT_DATETIME_FORMAT = getProperty("default.datetime.format", "yyyy-MM-dd HH:mm:ss");
	
	/**
     * <li>默认的日期格式化字符串</li>
     * <li>配置键值：default.date.format</li>
     * <li>默认值：yyyy-MM-dd</li>
	 */
	public static final String DEFAULT_DATE_FORMAT = getProperty("default.date.format", "yyyy-MM-dd");
	
	/**
     * <li>排序，升序的标志</li>
     * <li>配置键值：order.ascend</li>
     * <li>默认值：asc</li>
	 */
	public static final String ORDER_ASCEND = getProperty("order.ascend", "asc");
	
	/**
     * <li>排序，降序的标志</li>
     * <li>配置键值：order.descend</li>
     * <li>默认值：desc</li>
	 */
	public static final String ORDER_DESCEND = getProperty("order.descend", "desc");
	
	/*****************************************************************
	 * Added by zhouyingzhao, from 2009-6-1;
	 *****************************************************************/
	public static final String FIELDS_ARRAY = "fieldsArray";

	
	public static final String AuditID_Session = "AuditID_Session";
	
	public static final String User_Auth = "user_auth";
	
	/** action中将setResult的结果处理结果放置在request范围内的标示 */
	public static final String RESULT_INDEX = "result_index";
	
	/** action中将putInfoResult的结果处理结果放置在request范围内的标示，用Map保存 */
	public static final String RESULT_MAP_INDEX = "result_map_index";
	
	public static final String JSON_MSG = "msg";
	
	public static final String CUSTOM_JSONHELPER = "custom_jsonhelper";
	
	public static final String JSON_NOT_OUTPUT_SUCCESS = "json_not_out_success";
	
	
	
	
	
	/** 系统中树的虚拟根ID */
	public final static String VIRTUAL_TREE_ROOT = "ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ";
	
	/** 当前数据库类型 增加原因：审计中需要判断当前数据库是什么类型，如果为oracle则使用自定义的sequence，否则可能使用表的字段自增功能 */
	public final static String CURRENT_DATABASE_TYPE= getProperty("framework.env.db.current", "oracle");
	
	/** 插入历史表时默认的schema 不同的项目可以通过在framework.properties中配置framework.rdbms.historySchema属性来设定历史表的schema */
	public final static String DEFAULT_HISTORY_SCHEMA= getProperty("framework.rdbms.historySchema", null);
	/** 系统标识 增加原因：审计信息中记录系统标识 */
	
	/**
     * <li>是否物理删除附件的默认配置</li>
     * <li>配置键值：delete.physically</li>
     * <li>默认值：0，不删除</li>
     * <li>1 - 物理删除； 0 - 不删除</li>
	 */
	public final static boolean File_DELETE_PHYSICALLY = BOOLEAN_TRUE.equals(getProperty("delete.physically", "0"));
}