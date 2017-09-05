package com.partner4java.p4jtools.str;

import org.apache.commons.lang.StringUtils;

/**
 * 样式字符串处理帮助类
 * 
 * @author 王昌龙
 *
 */
public class StyleHelper {

	/**
	 * 去除样式
	 * 
	 * @param content
	 * @return
	 */
	public static String removeStyle(String content) {
		if (StringUtils.isNotEmpty(content) && content.length() > 10) {
			if (content.contains("<img")) {
				String[] contents = content.split("style=\"");
				if (contents != null && contents.length > 0) {
					StringBuilder builder = new StringBuilder();
					// 去除图片样式
					for (int i = 1; i < contents.length + 1; i++) {
						if (i > 1) {
							String str = contents[i - 1];
							if (str.length() > 2) {
								builder.append(str.substring(str.indexOf("\"") + 1));
							} else {
								builder.append(str);
							}
						} else {
							builder.append(contents[i - 1]);
						}
					}
					return builder.toString().replace("<p>&nbsp;</p>", "");
				}
			}
		}

		
		return content;
	}
}
