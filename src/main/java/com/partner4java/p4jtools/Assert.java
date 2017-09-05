package com.partner4java.p4jtools;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 各种判断非空
 *
 */
public final class Assert {

	public static Boolean isNotBlank(Object arg) {
		return execute(arg);
	}

	@SuppressWarnings("rawtypes")
	private static Boolean execute(Object arg) {
		if (arg == null) {
			return Boolean.valueOf(false);
		}
		if (((arg instanceof String)) && (StringUtils.isBlank(arg.toString()))) {
			return Boolean.valueOf(false);
		}
		if (((arg instanceof Map)) && (((Map) arg).isEmpty())) {
			return Boolean.valueOf(false);
		}
		if (((arg instanceof List)) && (((List) arg).isEmpty())) {
			return Boolean.valueOf(false);
		}
		if (((arg instanceof Set)) && (((Set) arg).isEmpty())) {
			return Boolean.valueOf(false);
		}
		if (arg.getClass().isArray()) {
			Object[] arg1 = (Object[]) arg;
			return Boolean.valueOf(arg1.length > 0);
		}
		return Boolean.valueOf(true);
	}
}