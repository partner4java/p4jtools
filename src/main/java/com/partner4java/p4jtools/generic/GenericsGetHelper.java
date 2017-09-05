package com.partner4java.p4jtools.generic;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

import org.springframework.core.GenericTypeResolver;

/**
 * 获取泛型类型辅助工具类
 * 
 * @author partner4java
 * 
 */
public class GenericsGetHelper {

	/**
	 * 获取父类传入泛型类型<br/>
	 * 只限获取单个类型
	 * 
	 * @param clazz
	 *            需要查询的class（子class）
	 * @see GenericTypeResolver
	 * @return 父类传入的泛型类型
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperGenericsClass(Class clazz) {
		// 主要工作由GenericTypeResolver完成，GenericTypeResolver并提供了cache（缓存），提高性能
		Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(clazz);
		if (typeVariableMap != null) {
			Type rawType = typeVariableMap.values().iterator().next();
			if (rawType instanceof Class)
				return (Class) rawType;
		}
		return null;
	}

	/**
	 * 获取父类传入泛型类型<br/>
	 * 
	 * @param clazz
	 *            需要查询的class（子class）
	 * @param name
	 *            获取指定名称的泛型参数(如：T)
	 * 
	 * @see GenericTypeResolver
	 * @return 父类传入的泛型类型
	 */
	@SuppressWarnings("rawtypes")
	public static Class getSuperGenericsClass(Class clazz, TypeVariable name) {
		// 主要工作由GenericTypeResolver完成，GenericTypeResolver并提供了cache（缓存），提高性能
		Map<TypeVariable, Type> typeVariableMap = GenericTypeResolver.getTypeVariableMap(clazz);
		if (typeVariableMap != null) {
			Type rawType = typeVariableMap.get(name);
			if (rawType instanceof Class)
				return (Class) rawType;
		}
		return null;
	}

}
