package com.wholetech.commons.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.wholetech.commons.query.Page;

/**
 * 
 * @param <T>
 */
public interface BaseDao<T> extends CommonDao {

	/**
	 * 根据主键获取对象。
	 * 
	 * @param id
	 *            主键
	 * @return 当前泛型的对象; 如果没找到返回null。
	 */
	public T get(Serializable id);

	/**
	 * 根据主键返回克隆对象。
	 * 
	 * @param id
	 *            主键
	 * @return clone对象; 如果没找到返回null;
	 */
	public T getClone(Serializable id);

	/**
	 * 返回当前泛型全部的实体。
	 * 
	 * @return 当前泛型全部的实体; 如果没找到返回长度为0的List。
	 */
	public List<T> getAll();

	/**
	 * 新建实体。
	 * 
	 * @param pojo
	 *            要被创建的对象
	 * @return 创建实体后实体的主键值。
	 */
	public Serializable create(T pojo);

	/**
	 * 更新一个实体。
	 * 
	 * @param pojo
	 *            待更新实体
	 */
	public void update(T pojo);

	/**
	 * 创建或更新一个实体。
	 * 
	 * @param o
	 *            待创建或更新对象。
	 * @return 保存或修改的实体的主键值。
	 */
	public Serializable saveOrUpdate(T o);

	/**
	 * 删除对象。
	 * 
	 * @param pojo
	 *            待删除的持久化对象。
	 */
	public void remove(T pojo);

	/**
	 * 批量更新数据。
	 * 
	 * @param instances
	 *            待更新实体集合
	 */
	public void batchUpdate(Collection<T> instances);

	/**
	 * 批量更新一批数据。
	 * 更新前后，实体的状态(游离、持久化)不变。
	 * 
	 * @param instances
	 *            待更新实体集合。
	 */
	public void batchMerge(Collection<T> instances);

	/**
	 * 保存或者更新一批数据。
	 * 如果id为空，则save，如果id不为空则update。
	 * 
	 * @param instances
	 *            待保存实体集合。
	 */
	public void batchSaveOrUpdate(Collection<T> instances);

	/**
	 * 批量保存一批数据。
	 * 
	 * @param instances
	 *            待新增的实体集合。
	 */
	public void batchSave(Collection<T> instances);

	/**
	 * 批量删除一批持久化对象
	 * 
	 * @param instances
	 *            待删除的实体集合。
	 */
	public void batchRemove(Collection<T> instances);

	/**
	 * 根据属性名和属性值查询一个实体。
	 * 
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 符合条件的唯一泛型对象; 如果没找到则返回<tt>null</tt>;
	 */
	public T findUnique(String name, Object value);

	/**
	 * 根据属性名和属性值查询符合条件的一批对象。
	 * 查询条件是name=value，可以根据参数查询出多条记录。
	 * 
	 * @param name
	 *            属性名称
	 * @param value
	 *            属性值
	 * @return 符合条件的对象列表，如果没找到则返回<tt>空list</tt>。
	 */
	public List<T> findList(String name, Object value);

	/**
	 * 通过filter中设置的查询条件来查询一批实体。设置条件的规则是全部采用相等。
	 * 
	 * @param filter
	 *            Map，查询条件，由属性到值的映射。
	 * @return 符合条件的对象列表，如果没找到则返回<tt>空list</tt>。
	 */
	public List<T> findList(Map<String, Object> filter);

	/**
	 * 通过filters中设置的查询条件来查询一批实体。
	 * <p>
	 * 其中可以设置第二个参数，来自己决定如何设置查询条件。 eg.
	 * 
	 * <pre>
	 * dao.findBy(filterMap, new FilterSetter() {
	 * 	public void setUpCriteria(Criteria criteria, Map filterMap) {
	 * 		for (Map.Entry&lt;String, ?&gt; entry : filterMap.entrySet()) {
	 * 			String key = entry.getKey();
	 * 			Object value = entry.getValue();
	 * 
	 * 			if (&quot;prapa&quot;.equals(key)) {
	 * 				criteria.add(Restrictions.like(key, value));
	 * 			} else {
	 * 				criteria.add(Restrictions.eq(key, value));
	 * 			}
	 * 		}
	 * 	}
	 * });
	 * </pre>
	 * 
	 * @param filterMap
	 *            ，查询条件，由属性到值的映射。
	 * @param filterSetter
	 *            设置查询条件的接口。
	 * @return 符合条件的对象列表，如果没找到则返回<tt>空list</tt>。
	 */
	public List<T> findList(Map<String, ?> filterMap, FilterSetter filterSetter);


	/**
	 * 根据查询条件分页查询。
	 * filterSetter的用法参见{@link #findBy(Map, FilterSetter...)}
	 * 
	 * @param page
	 *            分页信息，其中也封装了排序信息。
	 * @param filters
	 *            Map，查询条件，由属性到值的映射。
	 * @param filterSetter
	 *            设置查询条件的接口。
	 */
	public Page<T> findPage(Page<T> page, Map<String, ?> filterMap, FilterSetter filterSetter);

	/**
	 * 根据查询条件分页查询。
	 * filters Map中的参数都是按照相等的规则来查询。
	 * 
	 * @param page
	 *            分页信息，其中也封装了排序信息。
	 * @param filters
	 *            Map，查询条件，由属性到值的映射。
	 */
	public Page<T> findPage(Page<T> page, Map<String, ?> filterMap);
}