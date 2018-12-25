package com.meta.dbmeta.util;

/**
 * 字符串工具
 * 
 * @ClassName: StringUtil
 * @author gaoy
 * @date 2018年12月4日 下午2:00:09
 */
public class StringUtil {

	// 去除空格
	public static String trim(String str) {
		if (null != str) {
			str = str.trim();
		}
		return str;
	}
}
