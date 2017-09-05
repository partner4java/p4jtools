package com.partner4java.p4jtools.str;

import java.security.MessageDigest;

public class MD5Util {

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @return
	 */
	public static String encoder(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
