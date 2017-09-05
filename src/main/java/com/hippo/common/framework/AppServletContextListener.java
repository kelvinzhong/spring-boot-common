/**
 * 
 */
package com.hippo.common.framework;

import com.hippo.common.config.properties.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * 
 * web容器关闭的时候，释放资源
 * 
 */
public class AppServletContextListener implements ServletContextListener {

	private static final Logger log = LoggerFactory.getLogger(AppServletContextListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext()); // 获取spring上下文！
		Map<String, ExecutorService> executorMap = context.getBeansOfType(ExecutorService.class);
		if (null != executorMap) {
			log.warn("++++++++++++++++++++++++      web 容器开始关闭，释放相关资源          ++++++++++++++++++++++++++++++++++");
			for (Entry<String, ExecutorService> en : executorMap.entrySet()) {
				en.getValue().shutdown();
				try {
					en.getValue().awaitTermination(Configuration.getValue("termination.time.out", 20),
							TimeUnit.SECONDS);
				} catch (InterruptedException e) {
					log.error("occur error on wait executorservice shutdown", e);
				}
			}
			log.warn("++++++++++++++++++++++++   释放线程池成功       ++++++++++++++++++++++++++++++++++");
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		log.debug(" context start to inid {}", arg0.getServletContext().getContextPath());
		System.setProperty("dubbo.application.logger","slf4j");
	}

}
