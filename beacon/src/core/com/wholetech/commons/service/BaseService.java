package com.wholetech.commons.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Service层对外公共接口。
 * 
 * 这一层主要封装业务逻辑，其中的重点是负责管理事务。从编码理论上讲，这一层的代码量应该是最大的，因为负责业务逻辑，所以一个Service可能引用几个Dao来完成复杂的业务逻辑。
 * <br />
 * Service编码要注意的点是：
 * <li> 事务的配置是在spring的ApplicationContext.xml文件中，参见元素&lt;tx:advice&gt;，
 * 		针对事务主要有两类，一类是要求有事务，一类不关心有没有事务。 </li>
 * <li> 当一个Service嵌套调用同一个Service中的方法，嵌套调用是完全不受事务控制的；
 *      只有从其他类中调用service方法才受事务控制。 </li>
 * <li> 事务的回滚完全是由异常来控制的，默认情况下，一旦抛出RuntimeException异常，则事务就会回滚。
 *      对于checked异常，则需要配置在&lt;tx:method name="update*" rollback-for="MyException"/&gt;</li>
 * 
 * <p>
 * 对接口中出现的术语进行解释：
 * <li>空list size为0的list</li>
 * <li>空page page.data的size为0的page</li>
 * 
 * 
 * 
 * @param <T>
 */
public interface BaseService<T> {

	/**
	 * 根据主键获得一个对象。
	 * 
	 * @param id 主键值。
	 * @return id对应的对象；如果没有找到返回null。
	 */
	public T get(Serializable id);

	/**
	 * 根据主键获取一个指定类的对象。
	 * 
	 * @param clazz 指定类型
	 * @param id 主键值
	 * @return 主键值为id的类型为clazz的对象；如果没有找到返回null。
	 */
	public Object get(Class<?> clazz, Serializable id);

	/**
	 * 获取当前泛型所有的实体。
	 * 
	 * @return 当前泛型的所有对象；如果一个也没有则返回<tt>空list</tt>.
	 */
	public List<T> getAll();

	/**
	 * 根据主键id获取一个实体的副本。
	 * 
	 * 这里使用Hibernate技术来解释一下：副本不在Hibernate的session中管理，是一个游离对象。
	 * 
	 * @param id 主键值。
	 * @return 主键值为id的对象拷贝；如果没有找到返回null。
	 */
	public T getClone(Serializable id);

	/**
	 * 创建一个实体。
	 * 
	 * @param o 被创建的实体。
	 * 
	 * @return 创建实体的主键。
	 */
	public Serializable create(T o);

	/**
	 * 
	 * @param id
	 * @param o
	 * @return
	 */
	public Serializable clone(Serializable id, T o);

	/**
	 * 更新一个实体。
	 * 
	 * @param o 被更新的对象。
	 */
	public void update(T o);

	/**
	 * 删除一个对象。
	 * 
	 * @param o 待删除对象。
	 * @return 删除成功与否的标志。
	 */
	public void remove(T o);

	/**
	 * 标记删除一个对象。
	 * <p>
	 * 将一个对象的delFlag置为D。
	 * 
	 * @param o 待操作对象。
	 * @deprecated
	 */
	public void markDelete(T o);
	
	/**
	 * 逻辑删除一个对象。
	 * <p>
	 * 将一个对象的delFlag置为D。
	 * 
	 * @param o 待操作对象。
	 */
	public void deleteLogically(T o);

	/**
	 * 批量更新对象。
	 * 更新完成后，参数中的对象都变成持久化状态。
	 * 
	 * @param detachedInstances 要更新的一批对象。
	 */
	public void batchUpdate(Collection<T> detachedInstances);

	/**
	 * 批量更新一批对象。
	 * <p> 更新后，这些对象的状态不变，原来如果不是持久化状态，现在依然不是持久化状态。
	 * 
	 * @param detachedInstances 待更新状态。
	 */
	public void batchMerge(Collection<T> detachedInstances);

	/**
	 * 批量新增或者保存一批对象。
	 * 
	 * @param instances 待操作对象。
	 */
	public void batchSaveOrUpdate(Collection<T> instances);

	/**
	 * 批量新增一批对象。
	 * 
	 * @param transientInstances 待新增对象。
	 */
	public void batchSave(Collection<T> transientInstances);

	/**
	 * 批量删除一批对象。
	 * 
	 * @param persistentInstances 待删除对象。
	 */
	public void batchRemove(Collection<T> persistentInstances);
	
	/**
	 * 根据主键值批量删除一批对象。
	 * 
	 * @param ids 要删除对象的主键数组。
	 */
	public void batchRemove(String[] ids);
	
	/**
	 * 根据主键批量安全删除一批数组。
	 * 采用逐个加载，然后删除的方式。与session缓存保持同步。
	 * 
	 * @param ids 要删除对象的主键数组。
	 */
	public void batchRemoveSafely(String[] ids);

	/**
	 * 判断属性name等于value的对象是否唯一，id为当前对象的主键。
	 * 
	 * <p>
	 * 如果id不为空，则根据以下规则判断：<br>
	 * 如果通过name=value没有找到任何对象，则唯一性成立；
	 * 如果只找到一个，主键值等于id，则唯一性成立，否则不成立；
	 * 如果找到多个，则不成立。
	 * 
	 * <p>
	 * 如果id为空，则根据以下规则判断：<br>
	 * 如果通过name=value没有找到任何对象，则唯一性成立；否则唯一性不成立。
	 * 
	 * @param name 属性名称
	 * @param value 属性值
	 * @param id 当前已存在对象的id，可以为null.
	 * @return 唯一性标志，true表示唯一性成立，false表示唯一性不成立。
	 */
	public boolean checkUnique(String name, String value, String id);

	/**
	 * 通过hql语句查询实体。
	 * 根据hql语句的不同形式，返回的对象可能不同，注意日志中的提示。
	 * 
	 * @param hqlOrKey 查询所用hql或者所要查询的hql在配置文件中的键值
	 * 
	 * @return 根据hql查询出一组对象；如果一个对象没找到，则返回空list。
	 */
	public List<?> findListByHql(String hqlOrKey, Object... values);

	/**
	 * 通过sql语句查询实体。
	 * <p>
	 * 如果select多个字段，则返回的对象是这些字段值组成的数组对象；
	 * 如果select一个字段，则返回这一个字段对应的对象。
	 * 
	 * @param sqlOrKey 查询所用sql或者所要查询的sql在配置文件中的键值
	 * 
	 * @return 查询出的记录list；如果一个对象没找到，则返回空list。
	 */
	public List<?> findListBySql(String sqlOrKey, Object... objects);

	/**
	 * 判断属性name等于value的对象是否唯一，id为当前对象的主键。
	 * 
	 * <p>
	 * 如果id不为空，则根据以下规则判断：<br>
	 * 如果通过name=value没有找到任何对象，则唯一性成立；
	 * 如果只找到一个，主键值等于id，则唯一性成立，否则不成立；
	 * 如果找到多个，则不成立。
	 * 
	 * <p>
	 * 如果id为空，则根据以下规则判断：<br>
	 * 如果通过name=value没有找到任何对象，则唯一性成立；否则唯一性不成立。
	 * 
	 * @param name 属性名称
	 * @param value 属性值
	 * @param id 当前已存在对象的id，可以为null.
	 * @return 唯一性标志，true表示唯一性成立，false表示唯一性不成立。
	 */
	public boolean checkUnique(String[] name, Object[] value, String id);

	/**
	 * Discription:获得数据库的时间
	 * 
	 * @author zhangll
	 */
	public Date getSystemDate();
}