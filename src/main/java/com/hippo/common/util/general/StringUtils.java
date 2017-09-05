package com.hippo.common.util.general;

import java.util.List;

public class StringUtils {

	public static boolean isEmpty(String value) {
		int strLen;
		if (value == null || (strLen = value.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(value.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isEmpty(String... values) {
		for (String value : values)
			if (isEmpty(value))
				return true;
		return false;
	}

	public static boolean isAllEmpty(String... values) {
		for (String value : values)
			if (isEmpty(value))
				return false;
		return true;
	}

	public static boolean isAllEmpty(List<String> values) {
		for (String value : values)
			if (isEmpty(value))
				return false;
		return true;
	}

	public static String phase(String value, String defaultValue) {
		if (isEmpty(value))
			return defaultValue;

		return value;
	}

	public static Integer phase(String value, Integer defaultValue) {
		if (isEmpty(value))
			return defaultValue;

		return Integer.parseInt(value);
	}

	public static Long phase(String value, Long defaultValue) {
		if (isEmpty(value))
			return defaultValue;

		return Long.parseLong(value);
	}

	public static Double phase(String value, Double defaultValue) {
		if (isEmpty(value))
			return defaultValue;

		return Double.parseDouble(value);
	}

	public static int getLength(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}
}
