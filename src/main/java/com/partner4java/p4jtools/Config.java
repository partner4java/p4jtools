package com.partner4java.p4jtools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 通用配置
 * 
 * @author 王昌龙
 * 
 */
public class Config {
	private static Map<String, Object> values = new HashMap<String, Object>();
	static {
		Properties p = new Properties();
		File pFile = null;
		FileInputStream pInStream = null;
		try {
			pFile = new File(Config.class.getClassLoader()
					.getResource("config.properties").getPath());
			pInStream = new FileInputStream(pFile);
			p.load(pInStream);
			Enumeration<String> enu = (Enumeration<String>) p.propertyNames();
			while (enu.hasMoreElements()) {
				String key = enu.nextElement();
				values.put(key, p.get(key));
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println(Config.class.getClassLoader()
					.getResource("").getPath()
					+ "config.properties不存在");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pInStream != null)
					pInStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取int类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static int intValue(String key) {
		return Integer.valueOf(values.get(key).toString());
	}
	
	/**
	 * 获取String类型的值
	 * 
	 * @param key
	 * @return
	 */
	public static String value(String key) {
		return values.get(key).toString();
	}
}
