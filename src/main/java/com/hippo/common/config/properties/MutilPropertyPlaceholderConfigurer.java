/**
 * 
 */
package com.hippo.common.config.properties;

import com.hippo.common.bean.ErrorCode;
import com.hippo.common.bean.SystemException;
import com.hippo.common.util.security.DESUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Properties;


/**
 * 
 * 实现了spring的外部属性文件placeholder。同时实现了configer接口。为框架中的其他类，可以通过configer获取配置文件中的内容。
 * 
 */
public class MutilPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

	private static final Logger log = LoggerFactory.getLogger(MutilPropertyPlaceholderConfigurer.class);

	private static final String[] encryptNames = { "usernameXXX", "pwdXXX" };

	private static boolean isEncryProperty(String propertyName) {
		for (String name : encryptNames) {
			if (propertyName.endsWith(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		if (isEncryProperty(propertyName)) {
			String value = DESUtils.decode64BASE(propertyValue);
			return value;
		} else {
			return propertyValue;
		}
	}

	@Override
	protected void loadProperties(Properties arg0) throws IOException {
		super.loadProperties(arg0);
		Configuration.setProp(arg0);
		if (StringUtils.isEmpty(Configuration.getProperty("project.code")))
			throw new SystemException(ErrorCode.INTERNAL_SERVER_ERROR,
					"Please set your project code in property file before system comes on line!");
		log.info("========== load properties file ==========");
	}
	
	
	
	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	private String beanName;
	@Override
	public void setBeanName(String name) {
		this.beanName = name;
	}

	private BeanFactory beanFactory;
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setIgnoreUnresolvablePlaceholders(boolean ignoreUnresolvablePlaceholders) {
		super.setIgnoreUnresolvablePlaceholders(true);
	}

}
