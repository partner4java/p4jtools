package com.partner4java.p4jtools.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

/**
 * 通过反射进行数据获取、操作相关辅助工具类<br/>
 * 
 * @author partner4java
 * 
 */
public class ReflectionDataHelper {
	/**
	 * 获取指定字段的值<br/>
	 * 如果是集合，封装成逗号分隔的数值
	 * 
	 * @param entity
	 *            实体对象
	 * @param field
	 *            字段反射类型
	 * @return the field's current value
	 */
	public static Object getFieldValue(Object entity, Field field) {
		ReflectionUtils.makeAccessible(field);
		Object value = ReflectionUtils.getField(field, entity);
		return value;
	}

	/**
	 * 获取指定字段的值
	 * 
	 * @param entity
	 *            实体对象
	 * @param fieldName
	 *            字段反射类型
	 * @return the field's current value
	 */
	public static Object getFieldValue(Object entity, String fieldName) {
		Object value = null;
		try {
			Field field = entity.getClass().getField(fieldName);
			ReflectionUtils.makeAccessible(field);
			value = ReflectionUtils.getField(field, entity);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 设置指定字段的值
	 * 
	 * @param entity
	 *            实体对象
	 * @param field
	 *            字段反射类型
	 * @param value
	 *            值
	 * @return 被设置的对象
	 */
	public static Object setFieldValue(Object entity, Field field, Object value) {
		if (value != null) {
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, entity, value);
		}
		return entity;
	}

	/**
	 * 设置指定字段的值
	 * 
	 * @param entity
	 *            实体对象
	 * @param fieldName
	 *            字段名称
	 * @param value
	 *            值
	 * @return 被设置的对象
	 */
	public static Object setFieldValue(Object entity, String fieldName,
			Object value) {
		try {
			Field field = entity.getClass().getField(fieldName);
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, entity, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}

	/**
	 * 设置指定字段的值
	 * 
	 * @param entity
	 *            实体对象
	 * @param methodName
	 *            方法名称
	 * @param value
	 *            值
	 * @return 被设置的对象
	 */
	public static Object invokeMethod(Object entity, String methodName,
			Object value) {
		try {
			Method method = entity.getClass().getMethod(methodName,
					boolean.class);
			ReflectionUtils.makeAccessible(method);
			method.invoke(entity, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entity;
	}
}
