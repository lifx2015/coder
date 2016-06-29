package com.unsky.util;

/**
 * 字符串工具类
 * 
 * @project coder
 * @package com.unsky.util
 * @class UtilString
 * @author unsky
 * @date 2016-6-29 15:31:56
 * @version 1.0
 */
public class UtilString {
	/**
	 * 首字母转小写
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCaseFirstOne(String str) {
		if (Character.isLowerCase(str.charAt(0)))
			return str;
		else
			return new StringBuffer().append(str.toLowerCase().charAt(0)).append(str.substring(1)).toString();
	}

	/**
	 * 首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String toUpperCaseFirstOne(String str) {
		if (Character.isUpperCase(str.charAt(0)))
			return str;
		else
			return new StringBuffer().append(str.toUpperCase().charAt(0)).append(str.substring(1)).toString();
	}

	/**
	 * 将下划线转成驼峰 <i>transfer_date->transferDate</i>
	 * 
	 * @param str
	 * @return
	 */
	public static String transform(String str) {
		while (str.contains("_")) {
			int i = str.indexOf("_");
			if (i + 1 < str.length()) {
				char c = str.charAt(i + 1);
				String temp = (c + "").toUpperCase();
				str = str.replace("_" + c, temp);
			}
		}
		return str;
	}
}
