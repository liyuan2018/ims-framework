/**
 * 文件名：StringUtil.java
 *
 * 版本信息：v1.0
 * 日期：2012-10-10
 * Copyright  Corporation 2012
 * 版权所有 E-vada
 *
 */
package com.spark.ims.common.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liyuan on 2018/4/27.
 */
public class StringUtils {

	/**
	 * @param obj
	 * @return
	 */
	public static String asString(Object obj) {
		return obj != null ? obj.toString() : "";
	}

	/**
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		return (obj == null || obj.toString().length() == 0);
	}

	/**
	 *
	 * @param list
	 * @param joinStr
	 * @return
	 */
	public static String join(String[] list, String joinStr) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; list != null && i < list.length; i++) {
			if ((i + 1) == list.length) {
				s.append(list[i]);
			} else {
				s.append(list[i]).append(joinStr);
			}
		}
		return s.toString();
	}

	/**
	 * firstCharLowerCase
	 *
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharLowerCase(String s) {
		if (s == null || "".equals(s))
			return ("");
		return s.substring(0, 1).toLowerCase() + s.substring(1);
	}

	/**
	 * firstCharUpperCase
	 *
	 * @param s
	 *            String
	 * @return String
	 */
	public static String firstCharUpperCase(String s) {
		if (s == null || "".equals(s))
			return ("");
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	/**
	 *
	 * @param src
	 * @return
	 */
	public static String toBeanPatternStr(String src) {
		String dist = src.toLowerCase();
		Pattern pattern = Pattern.compile("_([a-z0-9])");
		Matcher matcher = pattern.matcher(dist);
		while (matcher.find()) {
			dist = dist.replaceFirst(matcher.group(0), matcher.group(1)
					.toUpperCase());
		}
		return dist;
	}

	/**
	 *
	 * @param src
	 * @return
	 */
	public static String toJSLineSeparateStr(String src) {
		if (src == null) {
			return "";
		}
		String dist = src;
		dist = dist.replaceAll("\r\n", "\\\\n");
		dist = dist.replaceAll("\r", "\\\\n");
		dist = dist.replaceAll("\n", "\\\\n");
		return dist;
	}

	/**
	 *
	 * @return
	 */
	public static String getBytesString(String input, String code) {
		try {
			byte[] b = input.getBytes(code);
			return Arrays.toString(b);
		} catch (UnsupportedEncodingException e) {
			return String.valueOf(code.hashCode());
		}
	}

	/**
	 *
	 * @param clob
	 * @return
	 */
	public static String getStringFromClob(java.sql.Clob clob) {
		String result = "";
		try {
			if (clob == null) {
				return null;
			}
			Reader reader = clob.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			String line = br.readLine();
			StringBuffer sb = new StringBuffer(1024);
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
		} catch (Exception ex) {

		}
		return result;
	}

	public static String formatParamMsg(String message, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			message = message.replace("{" + i + "}", args[i].toString());
		}
		return message;
	}

    /**
     * 去掉前缀
     *
     * @param toTrim  待处理的字符串
     * @param trimStr 前缀
     * @return 去掉前缀的字符串
     */
    public static String trimPrefix(String toTrim, String trimStr) {
        while (toTrim.startsWith(trimStr)) {
            toTrim = toTrim.substring(trimStr.length());
        }
        return toTrim;
    }

    /**
     * 去掉后缀
     *
     * @param toTrim  代理的字符串
     * @param trimStr 后缀
     * @return 去掉后缀的字符串
     */
    public static String trimSufffix(String toTrim, String trimStr) {
        while (toTrim.endsWith(trimStr)) {
            toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
        }
        return toTrim;
    }


	public static String capitalize(String str) {
		int strLen;
		return str != null && (strLen = str.length()) != 0?(new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString():str;
	}

	/** @deprecated */
	public static String capitalise(String str) {
		return capitalize(str);
	}

	public static String uncapitalize(String str) {
		int strLen;
		return str != null && (strLen = str.length()) != 0?(new StringBuffer(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString():str;
	}

	/** @deprecated */
	public static String uncapitalise(String str) {
		return uncapitalize(str);
	}


	private static final String getMethodName = "get";
	/**
	 * 获取属性的get方法名
	 *
	 * @param dateFieldName 属性名称
	 * @return 获取属性的get方法名
	 */
	public static String getFieldMethodName(String dateFieldName) {
		return getMethodName + firstLetterToUpper(dateFieldName);
	}

	/**
	 * 转换首字母大写
	 *
	 * @param dateFieldName 字段名称
	 * @return 首字母大写后字串
	 */
	public static String firstLetterToUpper(String dateFieldName) {
		char[] cs = dateFieldName.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}
}
