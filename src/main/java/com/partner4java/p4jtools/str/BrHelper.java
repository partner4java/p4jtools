package com.partner4java.p4jtools.str;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * html中br标签相关处理
 * 
 * @author 王昌龙
 *
 */
public class BrHelper {

	/**
	 * /r/n替换成br
	 * 
	 * @return
	 */
	public static String toBr(String str) {
		Pattern pattern = Pattern.compile("(\r\n|\r|\n|\n\r)");
		if (StringUtils.isNotEmpty(str)) {
			Matcher matcher = pattern.matcher(str);
			return matcher.replaceAll("<br/>");
		}
		return null;
	}
}
