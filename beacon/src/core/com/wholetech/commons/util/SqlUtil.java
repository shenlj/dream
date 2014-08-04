package com.wholetech.commons.util;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.wholetech.commons.BaseObject;
import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.ssh.extend.SpringContextHolder;

public class SqlUtil {

	private static ConfigurationHelper configurationHelper = new HibernateConfigurationHelper();

	public static void batchSave(final Collection instances, final Connection conn) throws Exception {

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			for (final Object o : instances) {
				final String sql = SqlUtil.getInsertSql((BaseObject) o);
				if (sql != null && !sql.equals("")) {
					stmt.addBatch(sql);
				}
			}
			stmt.executeBatch();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	public static void batchUpdate(final Collection instances, final String[] str, final Connection conn)
			throws Exception {

		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			for (final Object o : instances) {
				final String sql = SqlUtil.getUpdateSql((BaseObject) o, str);
				if (sql != null && !sql.equals("")) {
					stmt.addBatch(sql);
				}
			}
			stmt.executeBatch();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	private static String getInsertSql(final BaseObject entity) {

		try {
			SqlUtil.setCreateProperties(entity);
			final PropertyUtilsBean pub = new PropertyUtilsBean();
			final PropertyDescriptor[] descriptors = pub.getPropertyDescriptors(entity);
			final String sTable = SqlUtil.configurationHelper.getTableName(entity.getClass());
			final List listName = new LinkedList();
			final List listValue = new LinkedList();
			for (final PropertyDescriptor element : descriptors) {
				final String name = element.getName();
				if (name != null) {
					final String columnName = SqlUtil.configurationHelper.getColumnName(entity.getClass(), name);
					if (columnName != null) {
						listName.add(columnName);
						final Object value = pub.getSimpleProperty(entity, name);
						listValue.add(value);
					}
				}
			}
			final String sql = SqlUtil.insert(sTable, listName.toArray(), listValue.toArray());
			return sql;
		} catch (final Exception e) {
			return null;
		}
	}

	private static String getUpdateSql(final BaseObject entity, final String[] str) {

		try {
			SqlUtil.setCreateProperties(entity);
			final PropertyUtilsBean pub = new PropertyUtilsBean();
			final PropertyDescriptor[] descriptors = pub.getPropertyDescriptors(entity);
			final String sTable = SqlUtil.configurationHelper.getTableName(entity.getClass());
			final List pks = SqlUtil.configurationHelper.getPkColumnName(entity.getClass());
			final List updateName = Arrays.asList(str);
			final List listName = new LinkedList();
			final List listValue = new LinkedList();
			final List conName = new LinkedList();
			final List conValue = new LinkedList();
			for (final PropertyDescriptor element : descriptors) {
				final String name = element.getName();
				if (name != null) {
					final String columnName = SqlUtil.configurationHelper.getColumnName(entity.getClass(), name);
					if (columnName != null) {
						if (updateName.contains(name)) {
							listName.add(columnName);
							final Object value = pub.getSimpleProperty(entity, name);
							listValue.add(value);
						} else {
							if (pks.contains(columnName)) {
								conName.add(columnName);
								final Object value = pub.getSimpleProperty(entity, name);
								conValue.add(value);
							}
						}
					}
				}
			}
			final String sql = SqlUtil.update(sTable, listName.toArray(), listValue.toArray(), conName.toArray(),
					conValue.toArray());
			return sql;
		} catch (final Exception e) {
			return null;
		}
	}

	private static void setCreateProperties(final BaseObject entity) {

		SpringContextHolder.getBean("sessionInfoHolder");
		if (ActionContext.getContext() != null) {
			ServletActionContext.getRequest();
		}
		if (!(entity instanceof BaseStandardEntity)) {
			return;
		}

		// if (request != null) {
		// if (((BaseStandardEntity) entity).getId() == null) {
		// ((BaseStandardEntity) entity).setId(UUIDHexGenerator.getInstance().generate());
		// }
		// ((BaseStandardEntity) entity).setCreaterId(sessionInfoHolder.getUserId(request));
		// ((BaseStandardEntity) entity).setCreaterName(sessionInfoHolder.getLoginName(request));
		// ((BaseStandardEntity) entity).setUpdaterId(sessionInfoHolder.getUserId(request));
		// ((BaseStandardEntity) entity).setUpdaterName(sessionInfoHolder.getLoginName(request));
		// ((BaseStandardEntity) entity).setCreaterOrgId(sessionInfoHolder.getOrgCode(request));
		// ((BaseStandardEntity) entity).setCreaterOrgName(sessionInfoHolder.getOrgName(request));
		// ((BaseStandardEntity) entity).setCreateTime(new Date());// 不取数据库时间，提高效率
		// ((BaseStandardEntity) entity).setUpdateTime(new Date());// 不取数据库时间，提高效率
		// ((BaseStandardEntity) entity).setDelFlag("A");
		// } else {
		// if (((BaseStandardEntity) entity).getId() == null) {
		// ((BaseStandardEntity) entity).setId(UUIDHexGenerator.getInstance().generate());
		// }
		// ((BaseStandardEntity) entity).setCreaterId(SYSTEM_USER);
		// ((BaseStandardEntity) entity).setCreaterName(SYSTEM_USER);
		// ((BaseStandardEntity) entity).setUpdaterId(SYSTEM_USER);
		// ((BaseStandardEntity) entity).setUpdaterName(SYSTEM_USER);
		// ((BaseStandardEntity) entity).setCreaterOrgId(SYSTEM_USER);
		// ((BaseStandardEntity) entity).setCreaterOrgName(SYSTEM_USER);
		// ((BaseStandardEntity) entity).setCreateTime(new Date());
		// ((BaseStandardEntity) entity).setUpdateTime(new Date());
		// ((BaseStandardEntity) entity).setDelFlag("A");
		//
		// }
	}

	public static String insert(final String sTable, final Object strRowName[], final Object[] strRowValue) {

		final int iLength = strRowName.length;// 要插入列的个数
		if (iLength == 0) {
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into ").append(sTable).append(" ( ");
		for (int i = 0; i < iLength - 1; i++) {
			sql.append(" ").append(strRowName[i]).append(",");
		}// end of for...
		sql = sql.append(strRowName[iLength - 1]).append(") values (");
		for (int i = 0; i <= iLength - 1; i++) {
			if (strRowValue[i] == null) {
				sql.append(" null");
			} else if (strRowValue[i] instanceof Number) {
				sql.append(strRowValue[i]);
			} else if (strRowValue[i] instanceof Date) {
				sql.append(" to_date('").append(DateUtil.format((Date) strRowValue[i], DateUtil.getDateTimePattern()))
						.append("','yyyy-MM-dd HH24:mi:ss')");
			} else if (strRowValue[i] != null && strRowValue[i].toString().equals("")) {
				sql.append(" null");
			} else {
				sql.append(" '").append(strRowValue[i]).append("'");
			}
			if (i < iLength - 1) {
				sql.append(",");
			} else {
				sql.append(")");
			}
		}
		return sql.toString();
	}

	public static String update(final String sTable, final Object strRowName[], final Object[] strRowValue,
			final Object conName[],
			final Object[] conValue) {

		final int iLength = strRowName.length;// 要更新列的个数
		if (iLength == 0) {
			return null;
		}
		final StringBuffer sql = new StringBuffer();
		sql.append(" update ").append(sTable).append(" set ");
		for (int i = 0; i <= iLength - 1; i++) {
			if (strRowValue[i] == null) {
				sql.append(strRowName[i]).append(" =").append(" null");
			} else if (strRowValue[i] instanceof Number) {
				sql.append(strRowName[i]).append(" =").append(strRowValue[i]);
			} else if (strRowValue[i] instanceof Date) {
				sql.append(strRowName[i]).append(" =").append(" to_date('").append(
						DateUtil.format((Date) strRowValue[i], DateUtil.getDateTimePattern()))
						.append("','yyyy-MM-dd HH24:mi:ss')");
			} else if (strRowValue[i] != null && strRowValue[i].toString().equals("")) {
				sql.append(strRowName[i]).append(" =").append(" null");
			} else {
				sql.append(strRowName[i]).append(" =").append(" '").append(strRowValue[i]).append("'");
			}
			if (i < iLength - 1) {
				sql.append(",");
			}
		}
		for (int i = 0; i <= conName.length - 1; i++) {
			sql.append(" where ");
			if (conValue[i] == null) {
				sql.append(conName[i]).append(" =").append(" null");
			} else if (conValue[i] instanceof Number) {
				sql.append(conName[i]).append(" =").append(conValue[i]);
			} else if (conValue[i] instanceof Date) {
				sql.append(conName[i]).append(" =").append(" to_date('").append(
						DateUtil.format((Date) conValue[i], DateUtil.getDateTimePattern()))
						.append("','yyyy-MM-dd HH24:mi:ss')");
			} else if (conValue[i] != null && conValue[i].toString().equals("")) {
				sql.append(conName[i]).append(" =").append(" null");
			} else {
				sql.append(conName[i]).append(" =").append(" '").append(conValue[i]).append("'");
			}
			if (i < iLength - 1) {
				sql.append(" and ");
			}
		}
		return sql.toString();
	}
}
