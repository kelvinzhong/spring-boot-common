package com.hippo.common.config.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * properties文件 配置中心
 * 
 */
public class Configuration {
	private static final Logger log = LoggerFactory.getLogger(Configuration.class);

	/** 本地property文件配置收集 **/
	public static final Properties prop = new Properties();

	/** 分布式配置缓存 **/
	public static final Map<String, String> map = new HashMap<String, String>();

	static void setProp(Map<? extends String, ? extends String> m) {
		prop.putAll(m);
	}

	static void setProp(Properties p) {
		for (Object o : p.keySet()) {
			prop.put(o, p.get(o));
		}
	}

	void load(InputStream io) throws IOException {
		prop.load(io);
	}

	static void setProp(String name, String value) {
		prop.put(name, value);
	}

	public static void set(String key, String value) {
		if (map != null) {
			log.info(">>>>>>>>>> xxl-conf: 设置配置�: [{}:{}]", new Object[] { key, value });
			map.put(key, value);
		}
	}

	public static void update(String key, String value) {
		if (map != null) {
			if (map.get(key) != null) {
				log.info(">>>>>>>>>> xxl-conf: 更新配置: [{}:{}]", new Object[] { key, value });
				map.put(key, value);
			}
		}
	}

	public static String remove(String key) {
		if (map != null) {
			log.info(">>>>>>>>>> xxl-conf: 删除配置 ", key);
			return map.remove(key);
		}
		return null;
	}

	/***
	 * 获取默认property配置文件的配置
	 * 
	 * @param name
	 * @return
	 */
	public static String getProperty(String name) {
		return (String) prop.get(name);
	}

	public static Object getProperty(String name, Object defaultValue) {
		if (null == prop.get(name))
			return defaultValue;
		return prop.get(name);
	}

	public static int getProperty(String name, int defaultValue) {
		if (null == prop.get(name))
			return defaultValue;
		return Integer.parseInt((String) prop.get(name));
	}

	public static String getProperty(String name, String defaultValue) {
		if (null == prop.get(name))
			return defaultValue;
		return (String) prop.get(name);

	}

	public static long getProperty(String name, long defaultValue) {
		if (null == prop.get(name))
			return defaultValue;
		return (long) prop.get(name);

	}

	public static boolean getProperty(String name, boolean defaultValue) {
		if (null == prop.get(name))
			return defaultValue;
		return Boolean.parseBoolean(prop.get(name).toString());
		
	}

	private static boolean loadFromZookeeper(String name) {
		String zkData = ConfZkClient.getPathDataByKey(name);
		if (zkData != null) {
			set(name, zkData);
			return true;
		}

		return false;
	}

	public static String getValue(String name) {
		if (null == map.get(name)) {// && !loadFromZookeeper(name)
			log.warn(" SYS_CODE 中不存在： {} 配置项", name);
			return null;
		}
		return map.get(name);
	}

	public static String getValue(String name, String defaultVale) {
		if (null == map.get(name)) {// && !loadFromZookeeper(name)
			log.warn(" SYS_CODE 中不存在： {} 配置项", name);
			return defaultVale;
		}
		return map.get(name);
	}

	public static int getValue(String name, int defaultVale) {
		if (null == map.get(name)) {// && !loadFromZookeeper(name)
			log.warn(" SYS_CODE 中不存在： {} 配置项", name);
			return defaultVale;
		}
		return Integer.parseInt(map.get(name));
	}

	public static long getValue(String name, long defaultVale) {
		if (null == map.get(name)) {// && !loadFromZookeeper(name)
			log.warn(" SYS_CODE 中不存在： {} 配置项", name);
			return defaultVale;
		}
		return Long.parseLong(map.get(name));
	}

	public static boolean getValue(String name, boolean defaultVale) {
		if (null == map.get(name)) {// && !loadFromZookeeper(name)
			log.warn(" SYS_CODE 中不存在： {} 配置项", name);
			return defaultVale;
		}
		return Boolean.parseBoolean(map.get(name));
	}

}
