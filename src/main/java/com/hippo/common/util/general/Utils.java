package com.hippo.common.util.general;

import com.alibaba.fastjson.JSON;
import com.hippo.common.bean.SystemException;
import com.hippo.common.config.properties.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Utils {

	private static final Logger log = LoggerFactory.getLogger(Utils.class);

	public static final boolean versionCompair(String requirIOSVersion, int requirAndroidVersion, String thisVersion) {

		if (StringUtils.isEmpty(thisVersion)) // 网页端无此参数，默认为最高版本
			return true;

		try {
			int androidVersion = Integer.parseInt(thisVersion);

			if (androidVersion < requirAndroidVersion)
				return false;
			else
				return true;
		} catch (Exception e) {
			String[] iosVersion = requirIOSVersion.split("\\.");
			String[] userVersion = thisVersion.split("\\.");

			for (int i = 0; i < iosVersion.length; i++)
				if (Integer.parseInt(userVersion[i]) < Integer.parseInt(iosVersion[i]))
					return false;
				else if (Integer.parseInt(userVersion[i]) > Integer.parseInt(iosVersion[i]))
					return true;

			return true;
		}
	}

	public static final String getRequestApiPostfix(HttpServletRequest request) {
		return request.getServletPath().endsWith("/")
				? request.getServletPath().substring(1, request.getServletPath().length() - 1)
				: request.getServletPath().substring(1);
	}

	public static final String getRequestUniqueId(HttpServletRequest request) throws SystemException {

		Map<String, Object> paramMap = getRequestParamMap(request);
		String userId = paramMap.get("loginUserId").toString();
		if (StringUtils.isEmpty(userId))
			throw new SystemException();

		// if (userId.equals(Constants.NOT_LOGGED_IN)) {
		// String imei = paramMap.get("imei");
		// String deviceType = paramMap.get("deviceType");
		// if (StringUtils.isEmpty(imei) || StringUtils.isEmpty(deviceType))
		// throw new SystemException();
		//
		// return imei + deviceType;
		// }

		return userId;
	}

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	}

	public static Map<String, Object> objectSuperClassToMap(Object obj) throws Exception {
		if (obj == null)
			return null;

		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass().getSuperclass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") == 0) {
				continue;
			}
			Method getter = property.getReadMethod();
			Object value = getter != null ? getter.invoke(obj) : null;
			map.put(key, value);
		}

		return map;
	}

	public static String getResultCodeFormat(int code) {
		if (code < 10)
			return "00" + code;
		if (code < 100)
			return "0" + code;

		return Integer.toString(code);
	}

	public static String getProjectCode() {
		String projectCode = Configuration.getProperty("project.code", "00");
		return projectCode.length() < 2 ? "0" + projectCode : projectCode;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList<Class> getImplementsClassByInterface(Class clazz, String scanPath) {
		ArrayList<Class> list = new ArrayList<>();

		if (clazz.isInterface())
			try {
				ArrayList<Class> allClass = getAllImplementsClass(scanPath);

				for (Class c : allClass)
					if (clazz.isAssignableFrom(c) && !clazz.equals(c))
						list.add(c);

			} catch (Exception e) {
				log.warn(e.toString());
			}

		return list;
	}

	@SuppressWarnings("rawtypes")
	private static ArrayList<Class> getAllImplementsClass(String packagename) {
		ArrayList<Class> list = new ArrayList<>();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String path = packagename.replace('.', '/');
		try {
			ArrayList<File> fileList = new ArrayList<>();

			Enumeration<URL> enumeration = classLoader.getResources(path);
			while (enumeration.hasMoreElements()) {
				URL url = enumeration.nextElement();
				fileList.add(new File(url.getFile()));
			}
			for (int i = 0; i < fileList.size(); i++) {
				list.addAll(findClass(fileList.get(i), packagename));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	private static ArrayList<Class> findClass(File file, String packagename) {
		ArrayList<Class> list = new ArrayList<>();
		if (!file.exists()) {
			return list;
		}
		File[] files = file.listFiles();
		for (File file2 : files) {
			if (file2.isDirectory()) {
				assert !file2.getName().contains(".");// 添加断言用于判断
				ArrayList<Class> arrayList = findClass(file2, packagename + "." + file2.getName());
				list.addAll(arrayList);
			} else if (file2.getName().endsWith(".class")) {
				try {
					// 保存的类文件不需要后缀.class
					list.add(Class
							.forName(packagename + '.' + file2.getName().substring(0, file2.getName().length() - 6)));
				} catch (ClassNotFoundException e) {
					log.error(e.toString());
				}
			}
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object invokeMethodByClassName(Class clazz, String methodName, Object[] arguments, Class[] paramClass)
			throws Exception {
		Class c = Class.forName(clazz.getName());
		Object yourObj = c.newInstance();

		Method method = c.getMethod(methodName, paramClass);
		return method.invoke(yourObj, arguments);
	}

	public static boolean isSystemException(Exception ex) {
		if (ex != null && !StringUtils.isEmpty(ex.getMessage())
				&& ex.getMessage().contains(SystemException.class.getName()))
			return true;

		return false;
	}

	public static String getSystemExceptionMessage(Exception ex) {
		if (ex != null && !StringUtils.isEmpty(ex.getMessage())) {
			int start = ex.getMessage().indexOf(";msg:");
			int end = ex.getMessage().indexOf(";\n");
			if (start < 0 || end < 0)
				return null;
			return ex.getMessage().substring(start + 5, end);
		}
		return null;
	}

	public static String getSystemExceptionCode(Exception ex) {
		if (ex != null && !StringUtils.isEmpty(ex.getMessage())) {
			int start = ex.getMessage().indexOf("code:");
			int end = ex.getMessage().indexOf(";msg:");
			if (start < 0 || end < 0)
				return null;
			return ex.getMessage().substring(start + 5, end);
		}
		return null;
	}

	public static String getBodyString(final ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

	public static Map<String, Object> getRequestParamMap(ServletRequest request) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String paramName = (String) names.nextElement();

			String paramValue = request.getParameter(paramName);
			// 形成键值对应的map
			paramMap.put(paramName, paramValue);
		}

		if (CollectionUtils.isEmpty(paramMap)) {
			String requestJson = Utils.getBodyString(request);
			if (!StringUtils.isEmpty(requestJson))
				paramMap = JSON.parseObject(requestJson, Map.class);
		}

		return paramMap;
	}

	public static String tokenFromRequest(HttpServletRequest request) {
		final String value = request.getHeader("Authorization");

		if (value == null || !value.toLowerCase().startsWith("bearer")) {
			return null;
		}

		String[] parts = value.split(" ");

		if (parts.length < 2) {
			return null;
		}

		return parts[1].trim();
	}

	public static int stringLength(String value) {
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

	public static boolean isValidLocation(double[] location) {
		if (location == null)
			return false;
		if (location.length != 2)
			return false;
		if (location[0] < -180 || location[0] > 180)
			return false;
		if (location[1] < -90 || location[1] > 90)
			return false;

		return true;
	}
}
