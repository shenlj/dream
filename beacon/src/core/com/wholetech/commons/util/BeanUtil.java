package com.wholetech.commons.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;


/**
 * 扩展org.apache.commons.beanutils.BeanUtils, 提供一些反射方面缺失的封装.
 */
public class BeanUtil extends org.apache.commons.beanutils.BeanUtils {
	protected static final Log logger = LogFactory.getLog(BeanUtil.class);

	private BeanUtil() {
	}

	
	
	/**
	 * 实现同BeanUtils的copyProperties一样的功能，只是在orig中null值属性不被复制到desc中
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	static public void copyPropertiesWithoutNull (Object orig, Object desc) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(orig.getClass());
		
		// 将orig中非空的属性拷贝给desc
		for (int i = 0; i < pds.length; i++) {
			if (PropertyUtils.isReadable(orig, pds[i].getName()) 
					&& PropertyUtils.isWriteable(orig, pds[i].getName())) {
				
				Object value = PropertyUtils.getProperty(orig, pds[i].getName());
				if (value != null) {
					PropertyUtils.setProperty(desc, pds[i].getName(), value);
				}
			}
		}
	}

	/**
	 * 暴力获取指定对象中指定属性名的private/protected变量。
	 * 
	 * @param object 源对象。
	 * @param propertyName 属性名称。
	 */
	static public Object getDeclaredProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		return getDeclaredProperty(object, field);
	}
	
	/**
	 * 暴力获取指定对象中指定属性名的private/protected变量。
	 * 
	 * @param object 源对象。
	 * @param field 属性的<code>java.lang.reflect.Field</code>表示。
	 */
	static public Object getDeclaredProperty(Object object, Field field)
			throws IllegalAccessException {
		Assert.notNull(object);
		Assert.notNull(field);
		
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		Object result = field.get(object);
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置当前对象中的private/protected变量。
	 * 
	 * @param object 目标对象。
	 * @param propertyName 属性名称。
	 * @param newValue 设置的属性值。
	 */
	static public void setDeclaredProperty(Object object, String propertyName,
			Object newValue) throws IllegalAccessException,
			NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = object.getClass().getDeclaredField(propertyName);
		setDeclaredProperty(object, field, newValue);
	}

	/**
	 * 暴力设置当前对象中的private/protected变量。
	 * 
	 * @param object 目标对象。
	 * @param field 属性的<code>java.lang.reflect.Field</code>表示。
	 * @param newValue 设置的属性值。
	 */
	static public void setDeclaredProperty(Object object, Field field,
			Object newValue) throws IllegalAccessException {
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		field.set(object, newValue);
		field.setAccessible(accessible);
	}

	/**
	 * 暴力调用目标对象的private/protected函数。
	 * 
	 * @param object 目标对象。
	 * @param methodName 方法名称。
	 * @param params 被调用方法的参数，如果有多个参数这里要写多个。
	 * 
	 * @return 调用目标对象函数后的返回值。
	 */
	static public Object invokePrivateMethod(Object object, String methodName,
			Object... params) throws NoSuchMethodException,
			IllegalAccessException, InvocationTargetException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			types[i] = params[i].getClass();
		}
		Method method = object.getClass().getDeclaredMethod(methodName, types);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = method.invoke(object, params);
		method.setAccessible(accessible);
		return result;
	}

	/**
	 * 按Filed的类型取得Field列表。
	 * 
	 * @param object 源对象。
	 * @param type 参照类型。
	 * 
	 * @return Field列表。
	 */
	static public List<Field> getFieldsByType(Object object, Class type) {
		ArrayList<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 获得类中指定属性的getter名称。
	 * 
	 * @param type 源类。
	 * @param fieldName 属性名称。
	 */
	public static String getAccessorName(Class type, String fieldName) {
		Assert.hasText(fieldName, "FieldName required");
		Assert.notNull(type, "Type required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.capitalize(fieldName);
		} else {
			return "get" + StringUtils.capitalize(fieldName);
		}
	}

	/**
	 * 获得类中指定属性的访问函数。
	 * 
	 * @param type 源类。
	 * @param fieldName 属性名称。
	 * 
	 * @param 类中指定属性的访问函数。
	 */
	public static Method getAccessor(Class type, String fieldName) {
		try {
			return type.getMethod(getAccessorName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	/**
	 * 获取对象中指定属性的private/protected变量值。
	 * 
	 * @param object 源对象。
	 * @param propertyName 属性名称。
	 * 
	 * @return 指定属性的属性值。
	 */
	static public Object getPrivateProperty(Object object, String propertyName)
			throws IllegalAccessException, NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		return field.get(object);
	}

	/**
	 * 设置指定对象属性值。
	 * 
	 * @param object 目标对象。
	 * @param propertyName 属性名称。
	 * @param newValue 要设置的属性值。
	 */
	static public void setPrivateProperty(Object object, String propertyName,
			Object newValue) throws IllegalAccessException,
			NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = object.getClass().getDeclaredField(propertyName);
		field.setAccessible(true);
		field.set(object, newValue);
	}
	
	/**
	 * 级联初始化一个对象的属性。
	 * 
	 * @param bean 目标对象。
	 * @param property 级联属性，级联属性使用a.b.c表示。
	 * 
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 */
	public static void instantiateNullProperty (Object bean, String property) 
				  throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
		String[] props = StringUtils.split(property, '.');
		Object target = bean;
		
		for (int i = 0; i < props.length; i++) {
			Object propValue = PropertyUtils.getProperty(target, props[i]);
			
			if (propValue == null) {
				Class clazz = PropertyUtils.getPropertyType(target, props[i]);
				propValue = clazz.newInstance();
				PropertyUtils.setProperty(target, props[i], propValue);
			}
			
			target = propValue;
		}
	}
}
