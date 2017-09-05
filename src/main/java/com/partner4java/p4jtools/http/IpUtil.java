package com.partner4java.p4jtools.http;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * simple introduction ip格式转换工具类
 * <p>
 * detailed comment
 * 
 * @author 王昌龙 2012-4-25
 * @see
 * @since 1.0
 */
public final class IpUtil {

	// ip格式 0~255.0~255.0~255.1~255
	private static final String REGEX_IP = "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|[1-9])";
	// ip区间 (格式为 ip-ip，例如：211.151.74.1-211.151.74.100)
	private static final String REGEX_INTERVAL_IP = REGEX_IP + "-" + REGEX_IP;

	/**
	 * 字符型IP转换成long型
	 * 
	 * @param ip
	 * @return
	 */
	public static Long ip2long(String ip) {
		long result = 0;
		if (isIpFormat(ip)) {
			String[] section = ip.split("\\.");
			if (section.length > 2) {
				for (int i = 0; i < section.length; i++) {
					result += Long.parseLong(section[i]) << ((section.length
							- i - 1) * 8);
				}
			}
		}
		return result;
	}

	/**
	 * 将long型IP转换成字符型
	 * 
	 * @param value
	 * @return
	 */
	public static String long2ip(long value) {
		if (value < 1)
			return "";
		StringBuffer ip = new StringBuffer();
		ip.append(String.valueOf((value >>> 24))).append("."); // 直接右移24位
		ip.append(String.valueOf((value & 0x00FFFFFF) >>> 16)).append("."); // 将高8位置0，然后右移16位
		ip.append(String.valueOf((value & 0x0000FFFF) >>> 8)).append("."); // 将高16位置0，然后右移8位
		ip.append(String.valueOf((value & 0x000000FF))); // 将高24位置0
		return ip.toString();
	}

	/**
	 * 根据传入的ip区间，
	 * 
	 * @param intervalIp
	 * @return 返回的数组有两个元素，第一个是较小的，第二的是较大的
	 */
	public static long[] getIntervalIpArr(String intervalIp) throws Exception {
		if (!isIntervalIpFormat(intervalIp))
			throw new RuntimeException("interval ip format error !");

		String[] ips = intervalIp.split("-");
		long ip1 = ip2long(ips[0]);
		long ip2 = ip2long(ips[1]);

		return new long[] { Math.min(ip1, ip2), Math.max(ip1, ip2) };
	}

	public static boolean isIpFormat(String ip) {
		return ip != null && ip.length() > 0 && ip.matches(REGEX_IP);
	}

	public static boolean isIntervalIpFormat(String intervalIp) {
		return intervalIp != null && intervalIp.length() > 0
				&& intervalIp.matches(REGEX_INTERVAL_IP);
	}

	/**
	 * 获取ip：内部判断了是否为反射ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteAddrIp(HttpServletRequest request) {
		String ipFromNginx = getHeader(request, "X-Real-IP");
		return StringUtils.isEmpty(ipFromNginx) ? request.getRemoteAddr()
				: ipFromNginx;
	}

	private static String getHeader(HttpServletRequest request, String headName) {
		String value = request.getHeader(headName);
		return !StringUtils.isBlank(value)
				&& !"unknown".equalsIgnoreCase(value) ? value : "";
	}
}
