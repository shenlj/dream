package com.wholetech.commons.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.hql.ast.QueryTranslatorImpl;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wholetech.commons.query.Page;
import com.wholetech.commons.util.BeanUtil;
import com.wholetech.commons.util.CriterionUtil;

public class HibernateDAOHelper {

  protected Logger logger = LoggerFactory.getLogger(HibernateDAOHelper.class);
  protected static CriterionUtil criterionUtil = new CriterionUtil();

  public int countCriteriaResult(Criteria c) {

    CriteriaImpl impl = (CriteriaImpl) c;

    Projection projection = impl.getProjection();
    ResultTransformer transformer = impl.getResultTransformer();

    List orderEntries = null;
    try {
      orderEntries = (List) BeanUtil.getDeclaredProperty(impl, "orderEntries");
      BeanUtil.setDeclaredProperty(impl, "orderEntries", new ArrayList());
    } catch (Exception e) {
      this.logger.error("不可能抛出的异常:{}", e.getMessage());
    }

    int totalCount = ((Integer) c.setProjection(Projections.rowCount()).uniqueResult()).intValue();

    c.setProjection(projection);

    if (projection == null) {
      c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
    }

    if (transformer != null) {
      c.setResultTransformer(transformer);
    }
    try {
      BeanUtil.setDeclaredProperty(impl, "orderEntries", orderEntries);
    } catch (Exception e) {
      this.logger.error("不可能抛出的异常:{}", e.getMessage());
    }

    return totalCount;
  }

  public Criteria setPageParameter(Criteria c, Page<?> page) {

    c.setFirstResult(page.getIdisplayStart());
    c.setMaxResults(page.getIdisplayLength());
    return c;
  }

  public int countResult(Query query, Object[] values) {

    try {
      return Integer.parseInt(String.valueOf(getQuery(query, values).uniqueResult()));
    } catch (Exception e) {
      throw new RuntimeException("Query can't be auto count, queryString is:" + query.getQueryString(), e);
    }
  }

  public String buildCountQueryString(String sqlOrHql) {

    String prefix = "";

    int fromIndex = StringUtils.indexOfIgnoreCase(sqlOrHql, "from");
    int orderIndex = StringUtils.lastIndexOfIgnoreCase(sqlOrHql, "order by");
    if (StringUtils.isNotEmpty(sqlOrHql) && sqlOrHql.toUpperCase().trim().startsWith("SELECT")) {
      prefix = sqlOrHql.substring(StringUtils.indexOfIgnoreCase(sqlOrHql, "select") + 6, fromIndex);
    }

    if (orderIndex == -1) {
      if (StringUtils.indexOfIgnoreCase(prefix, "distinct") != -1) {
        return "SELECT COUNT(" + prefix + ") " + StringUtils.substring(sqlOrHql, fromIndex);
      }

      return "SELECT COUNT(*) as CT " + StringUtils.substring(sqlOrHql, fromIndex);
    }
    if (StringUtils.indexOfIgnoreCase(prefix, "distinct") != -1) {
      return "SELECT COUNT(" + prefix + ") " + StringUtils.substring(sqlOrHql, fromIndex, orderIndex);
    }

    return "SELECT COUNT(*) as CT " + StringUtils.substring(sqlOrHql, fromIndex, orderIndex);
  }

  protected String getCountSql(String originalHql, SessionFactory sessionFactory) {

    QueryTranslatorImpl queryTranslator = new QueryTranslatorImpl(originalHql, originalHql,
        Collections.EMPTY_MAP, (SessionFactoryImplementor) sessionFactory);

    queryTranslator.compile(Collections.EMPTY_MAP, false);

    return "select count(*) from (" + queryTranslator.getSQLString() + ") tmp_count_t";
  }

  public Query getQuery(Query query, Object[] values) {

    if (values != null && values.length > 0) {
      for (int i = 0; i < values.length; ++i) {
        query.setParameter(i, values[i]);
      }
    }

    return query;
  }

  private boolean isComplexProperty(String prop) {

    return prop.indexOf(".") != -1;
  }

  public static void checkWriteOperationAllowed(HibernateTemplate template, Session session)
  
      throws InvalidDataAccessApiUsageException {

    if (template.isCheckWriteOperations() && template.getFlushMode() != 2 &&
        session.getFlushMode().lessThan(FlushMode.COMMIT)) {
      throw new InvalidDataAccessApiUsageException(
          "Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
    }
  }

}
