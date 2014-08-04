package com.wholetech.commons.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wholetech.commons.query.Page;

public interface CommonDao {

	/**
	 * 根据主键查询指定类型的对象。
	 *
	 * @param pojoClass
	 *            要返回对象的class
	 * @param id
	 *            主键id
	 * @return 符合条件的唯一对象;如果没找到，返回null.
	 */
	public Object get(Class<?> pojoClass, Serializable id);

	/**
	 * 根据HQL查询一个对象。
	 * 如果这个条件能查出多条记录，则只返回第一条记录。
	 *
	 * @param hqlOrKey
	 *            查询所用hql或者所要查询的hql在配置文件中的键值
	 * @return 符合条件的一个对象；如果未找到则返回null;找到多个时，返回其中一个。
	 */
	public Object findOneByHql(String hqlOrKey, Object... values);

	/**
	 * 根据SQL查询一个对象。
	 * 如果这个条件能查出多条记录，则只返回第一条记录。
	 *
	 * @param sqlOrKey
	 *            查询所用sql或者所要查询的sql在配置文件中的键值
	 * @return 符合条件的一个对象；如果未找到则返回null;找到多个时，返回其中一个。
	 */
	public Object findOneBySql(String sqlOrKey, Object... values);

	/**
	 * 根据HQL查询一组对象。
	 * 返回list中的类型是Object[]
	 * 这一块的注释/实例再丰富吧。
	 *
	 * @param hqlOrKey
	 *            查询所用hql或者所要查询的hql在配置文件中的键值
	 * @param values
	 *            hql中参数对应的参数值
	 * @return 符合条件的对象list; 如果没找到，则返回<tt>空list</tt>。
	 */
	public List<?> findListByHql(String hqlOrKey, Object... values);

	/**
	 * 根据SQL查询一组对象。
	 * 最常见返回的类型是根据select后面的字段组成的Object[]数组对象。
	 *
	 * @param sqlOrKey
	 *            查询所用sql或者所要查询的sql在配置文件中的键值
	 * @param values
	 *            sql中参数对应的参数值
	 * @return 符合条件的对象list; 如果没找到，则返回<tt>空list</tt>。
	 */
	public List<?> findListBySql(String sqlOrKey, Object... values);

	/**
	 * 使用hql语句进行分页查询。
	 * hql语句中的参数使用占位符'?'。比如hql="from User user where user.name=?"。
	 * <p>
	 * 默认情况下，dao实现中会将hql修改成select count(*)...的形式，来查询总条数，但是修改规则对于一些复杂hql不适合， 所以默认查询的总条数就会与实际不符合。
	 * 所以可以在调用该接口之前，自己写函数获取总条数，然后赋予page.totalCount,然后将page.autoCount置为false。
	 *
	 * @param page
	 *            分页
	 * @param hqlOrKey
	 *            查询所用hql或者所要查询的hql在配置文件中的键值
	 * @param values
	 *            根据hql中需要参数个数而定的参数，可以为null。
	 * @return 包含查询结果的page对象
	 */
	public Page<?> findPageByHql(Page<?> page, String hqlOrKey, Object... values);

	/**
	 * 使用sql语句进行分页查询。
	 * sql语句中的参数占位符'?'。比如sql="select * from User user where user.name=?"。
	 * <p>
	 * 默认情况下，dao实现中会将sql修改成select count(*)...的形式，来查询总条数，但是修改规则对于一些复杂sql不适合， 默认查询的总条数就会与实际不符合。
	 * 所以可以在调用该接口之前，自己写函数获取总条数，然后赋予page.totalCount,然后将page.autoCount置为false。
	 *
	 * @param page
	 *            分页对象。
	 * @param sqlOrKey
	 *            查询所用sql或者所要查询的sql在配置文件中的键值
	 * @param values
	 *            根据sql中需要参数个数而定的参数，可以为null。
	 * @return 包含查询结果的page对象
	 */
	public Page<?> findPageBySql(Page<?> page, String sqlOrKey, Object... values);

	/**
	 * 直接执行一条Hql语句。往往是insert、update、delete语句。
	 *
	 * @param hqlOrKey
	 *            查询所用hql或者所要查询的hql在配置文件中的键值
	 * @param values
	 *            根据hql中需要的参数个数，传入的参数值。
	 * @return hql语句执行影响到的数据库记录条数。
	 */
	public int executeHql(final String hqlOrKey, final Object... values);

	/**
	 * 直接执行一条Sql语句。往往是insert、update、delete语句。
	 *
	 * @param sqlOrKey
	 *            查询所用sql或者所要查询的sql在配置文件中的键值
	 * @param values
	 *            根据sql中需要的参数个数，传入的参数值。
	 * @return sql语句执行影响到的数据库记录条数。
	 */
	public int executeSql(final String sqlOrKey, final Object... values);

	/**
	 * 使用命名查询技术实现查询，
	 * 这种需要在*.hbm.xml文件中定义&lt;query&gt;元素来制定命名查询。<br>
	 * eg *.hbm.xml
	 *
	 * <pre>
	 * &lt;query name="Daily.getDailyByIdAndDate"&gt;from Daily where Id = ?&lt;/query&gt;
	 *
	 * 调用程序
	 * findListBySqlName("Daily.getDailyByIdAndDate",new Object[]{"1111111111"});
	 * </pre>
	 *
	 * @param queryName
	 *            在*.hbm.xml文件中定义的命名查询
	 * @param values
	 *            参数值数组
	 * @return 符合条件的对象list; 如果没找到，则返回<tt>空list</tt>。
	 */
	public List<?> findListByNamedQuery(String queryName, Object... values);

	/**
	 * 使用count语句查询总条数，为page设置总条数。
	 *
	 * @param page
	 *            分页对象。
	 * @param sqlOrKey
	 *            count语句或者count语句的配置key；
	 * @param values
	 *            查询参数。
	 * @return
	 */
	public boolean setTotalByCountSql(Page page, final String sqlOrKey, final Object... values);

	/**
	 * 格式化配置的sql，把sql中模式化的内容使用参数替换。
	 * 比如：
	 * sql_find_student_in_age = "select * from student where age in ({0})"；
	 * 使用String sql = sqlGetter.getSql(sql_find_student_in_age, "21,22");
	 * 将得到sql = select * from student where age in (21, 22)
	 * <p>
	 * 注：第二个参数是数组，其中的顺序 要和配置字符串中的{index}对应起来。 arguments[0] 替换{0}, arguments[1] 替换{1}...
	 *
	 * @param key
	 *            sql配置key
	 * @param arguments
	 *            替换参数。
	 * @return
	 */
	public String formatSql(String sqlKey, String[] arguments);

	/**
	 * 获取数据库时间
	 */
	public Date getSystemDate();

	/**
	 * 刷新缓存，将session中缓存的内容
	 */
	public void flush();
}
