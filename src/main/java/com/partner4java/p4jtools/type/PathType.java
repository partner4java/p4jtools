package com.partner4java.p4jtools.type;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 文件判断
 * 
 * @author 王昌龙
 * 
 */
public class PathType {
	private static Set<String> suffixs = new HashSet<String>();

	static {
		suffixs.add("js");
		suffixs.add("css");
		suffixs.add("gif");
		suffixs.add("png");
		suffixs.add("jpg");
		suffixs.add("ico");
		suffixs.add("bmp");
		suffixs.add("ttf");
		suffixs.add("eot");
		suffixs.add("svg");
		suffixs.add("woff");
		suffixs.add("otf");
	}

	/**
	 * 是否是微信浏览器请求<br/>
	 * Header的user-agent里面包含micromessenger
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isWeixin(HttpServletRequest request) {
		String ua = request.getHeader("user-agent");
		if (StringUtils.isNotEmpty(ua)) {
			ua = ua.toLowerCase();
			if (ua.contains("micromessenger")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否由寺库app请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isSecoo(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null && (userAgent.contains("AndroidApp") || userAgent.contains("Secoo-iPhone"))) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是app中请求
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isWMWP(HttpServletRequest request) {
		if (isUserClient(request) || isAgentClient(request)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是用户端
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isUserClient(HttpServletRequest request) {
		if (isUserIos(request) || isUserAndroid(request)) {
			return true;
		}

		String deviceId = request.getHeader("deviceid");
		if (StringUtils.isEmpty(deviceId)) {
			deviceId = request.getParameter("deviceid");
		}

		if (StringUtils.isNotEmpty(deviceId)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否是经纪人端访问
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAgentClient(HttpServletRequest request) {
		if (isAgentIos(request)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否为ios用户端访问
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isUserIos(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null && userAgent.contains("auction-ios")) {
			return true;
		}

		String deviceId = request.getHeader("deviceid");
		if (StringUtils.isEmpty(deviceId)) {
			deviceId = request.getParameter("deviceid");
		}

		if (StringUtils.isNotEmpty(deviceId)) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否为安卓用户端访问
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isUserAndroid(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null && userAgent.contains("auction-android")) {
			return true;
		}

		return false;
	}

	/**
	 * 判断是否为ios经纪人端访问
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isAgentIos(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (userAgent != null && userAgent.contains("pm-agent-ios")) {
			return true;
		}

		return false;
	}

	/**
	 * 是否为静态请求
	 * 
	 * @param servletPath
	 *            请求地址（或者文件名称）
	 * @return
	 */
	public static boolean isStaticFiles(String servletPath) {
		try {
			if (servletPath.contains(".") && !servletPath.contains("?")) {
				String[] paths = servletPath.split("\\.");
				if (paths != null && paths.length > 1) {
					String suffix = paths[paths.length - 1];
					if (suffixs.contains(suffix.toLowerCase())) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取后缀名
	 * 
	 * @param servletPath
	 * @return
	 */
	public static String getSuffix(String servletPath) {
		try {
			if (servletPath.contains(".")) {
				String[] paths = servletPath.split("\\.");
				if (paths != null && paths.length > 1) {
					return paths[paths.length - 1];
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
