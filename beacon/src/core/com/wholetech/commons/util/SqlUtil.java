package com.wholetech.commons.util;

import java.beans.PropertyDescriptor;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.struts2.ServletActionContext;

import com.wholetech.commons.BaseObject;
import com.wholetech.commons.BaseStandardEntity;
import com.wholetech.commons.ssh.extend.SpringContextHolder;

public class SqlUtil {

  private static ConfigurationHelper configurationHelper = new HibernateConfigurationHelper();
  private static final String SYSTEM_USER = "system";

  public static void batchSave(Collection instances, Connection conn) throws Exception {

    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      for (Object o : instances) {
        String sql = getInsertSql((BaseObject) o);
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

  public static void batchUpdate(Collection instances, String[] str, Connection conn) throws Exception {

    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      for (Object o : instances) {
        String sql = getUpdateSql((BaseObject) o, str);
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

  private static String getInsertSql(BaseObject entity) {

    try {
      setCreateProperties(entity);
      PropertyUtilsBean pub = new PropertyUtilsBean();
      PropertyDescriptor[] descriptors = pub.getPropertyDescriptors(entity);
      String sTable = configurationHelper.getTableName(entity.getClass());
      List listName = new LinkedList();
      List listValue = new LinkedList();
      for (PropertyDescriptor element : descriptors) {
        String name = element.getName();
        if (name != null) {
          String columnName = configurationHelper.getColumnName(entity.getClass(), name);
          if (columnName != null) {
            listName.add(columnName);
            Object value = pub.getSimpleProperty(entity, name);
            listValue.add(value);
          }
        }
      }
      String sql = insert(sTable, listName.toArray(), listValue.toArray());
      return sql;
    } catch (Exception e) {
      return null;
    }
  }

  private static String getUpdateSql(BaseObject entity, String[] str) {

    try {
      setCreateProperties(entity);
      PropertyUtilsBean pub = new PropertyUtilsBean();
      PropertyDescriptor[] descriptors = pub.getPropertyDescriptors(entity);
      String sTable = configurationHelper.getTableName(entity.getClass());
      List pks = configurationHelper.getPkColumnName(entity.getClass());
      List updateName = Arrays.asList(str);
      List listName = new LinkedList();
      List listValue = new LinkedList();
      List conName = new LinkedList();
      List conValue = new LinkedList();
      for (PropertyDescriptor element : descriptors) {
        String name = element.getName();
        if (name != null) {
          String columnName = configurationHelper.getColumnName(entity.getClass(), name);
          if (columnName != null) {
            if (updateName.contains(name)) {
              listName.add(columnName);
              Object value = pub.getSimpleProperty(entity, name);
              listValue.add(value);
            } else {
              if (pks.contains(columnName)) {
                conName.add(columnName);
                Object value = pub.getSimpleProperty(entity, name);
                conValue.add(value);
              }
            }
          }
        }
      }
      String sql = update(sTable, listName.toArray(), listValue.toArray(), conName.toArray(), conValue.toArray());
      return sql;
    } catch (Exception e) {
      return null;
    }
  }

  private static void setCreateProperties(BaseObject entity) {

    SessionInfoHolder sessionInfoHolder = (SessionInfoHolder) SpringContextHolder.getBean("sessionInfoHolder");
    HttpServletRequest request = null;
    if (ServletActionContext.getContext() != null) {
      request = ServletActionContext.getRequest();
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

  public static String insert(String sTable, Object strRowName[], Object[] strRowValue) {

    int iLength = strRowName.length;// 要插入列的个数
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

  public static String update(String sTable, Object strRowName[], Object[] strRowValue, Object conName[],
      Object[] conValue) {

    int iLength = strRowName.length;// 要更新列的个数
    if (iLength == 0) {
      return null;
    }
    StringBuffer sql = new StringBuffer();
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
