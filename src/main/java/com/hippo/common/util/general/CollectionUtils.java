package com.hippo.common.util.general;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CollectionUtils {

	public static boolean isEmpty(Map<?, ?> c) {
		return c == null ? true : c.isEmpty() ? true : false;
	}

	public static boolean isEmpty(Collection<?> c) {
		return c == null ? true : c.isEmpty() ? true : false;
	}

	public static boolean isEmpty(List<?> c) {
		return c == null ? true : c.isEmpty() ? true : false;
	}

	public static boolean isEmpty(Object[] c) {
		return c == null ? true : c.length == 0 ? true : false;
	}
}
