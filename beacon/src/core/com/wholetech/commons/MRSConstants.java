package com.wholetech.commons;

public class MRSConstants {

	public static final String MESSAGE_KEY = "_message_result";

	public static final String SYSAPP_INDEX = "mrs";
	/** 字符串如果表示多个并列信息时，使用的默认分隔符 */
	public static final String DEFAULT_STRING_SPLIT = ",";

	public static final String REPORTCODE_TYPE = "report";// 报表代码标识
	public static final String CHAPTERCODE_TYPE = "chapter";// 章节代码标识

	public static final Integer REPORTCODE_LEN = 8;// 报表代码长度
	public static final Integer CHAPTERCODE_LEN = 8;// 章节代码长度
	public static final Integer MATERIA_LEN = 8;// 素材代码长度
	public static final Integer INEEX_LEN = 8;// 指标代码长度

	public static final String REPORTCODE_PREFIX = "FR";// 报表代码前缀
	public static final String CHAPTERCODE_PREFIX = "CH";// 章节代码前缀
	public static final String MATERIA_PREFIX = "MA";// 素材代码前缀
	public static final String INDEX_PREFIX = "ID";// 指标代码前缀

	public static final String EMPTY_STR = "";// 空串

	public static final String ONE_STR = "1";// 1
	public static final String ZERO_STR = "0";// 0

	/** 报告状态 */
	public static final String SUBMIT_STATUS = "1";// 提交
	public static final String NOSUBMIT_STATUS = "0";// 未提交

	/** 任务类型 */
	public static final String TASK_TYPE_WRITE = "1";// 任务类型：报告编写
	public static final String TASK_TYPE_INDEXFILL = "2";// 任务类型：数据填报
	public static final String TASK_TYPE_MATERIACOLE = "3";// 任务类型：素材搜集

	public static final String TASK_TYPE_WRITE_MC = "报告编写";// 任务类型：报告编写
	public static final String TASK_TYPE_INDEXFILL_MC = "数据填报";// 任务类型：数据填报
	public static final String TASK_TYPE_MATERIACOLE_MC = "素材搜集";// 任务类型：素材搜集

	public static final String TASK_TYPE_TASKSH = "4";// 任务类型：审核任务

	/** 代码生成前缀 */
	public static final String TASK_WRITE = "WR";// 报告编写
	public static final String TASK_INDEXFILL = "SU";// 数据填报
	public static final String TASK_COLLECT = "CO";// 素材搜集

	public static final Integer TASK_TYPE_SEQLEN = 4;// 任务顺序号长度

	/** 任务提交状态 */
	public static final String SUBMIT_TYPE = "1";// 任务提交状态:提交
	public static final String NOSUBMIT_TYPE = "0";// 任务提交状态：未提交

	/** 任务执行状态 */
	public static final String TASKEXECUTE_NEW = "01";// 任务执行状态：新建
	public static final String TASKEXECUTE_SINGER = "02";// 任务执行状态：签收
	public static final String TASKEXECUTE_SUBMIT = "03";// 任务执行状态：提交
	public static final String TASKEXECUTE_OVER = "04";// 任务执行状态：完成
	public static final String TASKEXECUTE_NEW_MC = "新建";// 任务执行状态：新建
	public static final String TASKEXECUTE_SINGER_MC = "签收";// 任务执行状态：签收
	public static final String TASKEXECUTE_SUBMIT_MC = "提交";// 任务执行状态：提交
	public static final String TASKEXECUTE_OVER_MC = "完成";// 任务执行状态：完成

	public static final String TASK_SUFFIX_WR = "编写";// 任务名称后缀

	public static final String TASK_ISAUTOCREATE = "1";// 任务创建方式为自动
	public static final String TASK_NOAUTOCREATE = "2";// 任务创建方式为手工

}
