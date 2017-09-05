package com.partner4java.p4jtools.enums;

/**
 * 请求方式类型
 * 
 * @author partner4java
 * 
 */
public enum HttpMethod {
	ALL, GET, DELETE, POST, PUT, HEAD, OPTIONS, TRACE;

	/**
	 * 根据文字返回泛型类型
	 * 
	 * @param methodName
	 * @return
	 */
	public static HttpMethod getHttpMethod(String methodName) {
		if (methodName != null && methodName.length() > 0) {
			methodName = methodName.trim().toUpperCase();
			if (ALL.toString().equals(methodName)) {
				return ALL;
			} else if (GET.toString().equals(methodName)) {
				return GET;
			} else if (DELETE.toString().equals(methodName)) {
				return DELETE;
			} else if (POST.toString().equals(methodName)) {
				return POST;
			} else if (PUT.toString().equals(methodName)) {
				return PUT;
			} else if (HEAD.toString().equals(methodName)) {
				return HEAD;
			} else if (OPTIONS.toString().equals(methodName)) {
				return OPTIONS;
			} else if (TRACE.toString().equals(methodName)) {
				return TRACE;
			}
		}
		return null;
	}
}
