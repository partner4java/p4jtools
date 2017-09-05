package com.partner4java.p4jtools.str;

import org.apache.commons.lang.StringUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字拼音工具类
 * 
 *
 */
public class PinyinUtil {

	/**
	 * 汉字转换为拼音
	 * 
	 * @param str
	 * @return
	 */
	public static String getPingYin(String str) {
		if (str != null && !str.isEmpty()) {
			char[] t1 = null;
			t1 = str.toCharArray();
			String[] t2 = new String[t1.length];
			HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();

			t3.setCaseType(HanyuPinyinCaseType.UPPERCASE);
			t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			t3.setVCharType(HanyuPinyinVCharType.WITH_V);
			String t4 = "";
			int t0 = t1.length;
			try {
				for (int i = 0; i < t0; i++) {
					// 判断是否为汉字字符
					if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
						t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
						t4 += t2[0];
					} else
						t4 += java.lang.Character.toString(t1[i]);
				}
				return t4;
			} catch (BadHanyuPinyinOutputFormatCombination e1) {
				e1.printStackTrace();
			}
			return t4;
		}

		return "";
	}

	/**
	 * 返回中文的首字母
	 * 
	 * @param str
	 * @return
	 */
	public static String getPinYinHeadChar(String str) {
		String convert = "";
		if (StringUtils.isNotBlank(str)) {
			for (int j = 0; j < str.length(); j++) {
				char word = str.charAt(j);
				String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
				if (pinyinArray != null) {
					convert += pinyinArray[0].charAt(0);
				} else {
					convert += word;
				}
			}
			if (StringUtils.isNotBlank(convert)) {
				convert = convert.toUpperCase();
			}
		}
		return convert;
	}
}
