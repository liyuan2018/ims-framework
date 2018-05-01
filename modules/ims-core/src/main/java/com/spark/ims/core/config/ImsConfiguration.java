package com.spark.ims.core.config;

import com.spark.ims.common.util.SpringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.impl.Log4jContextFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 此类的作用
 * 1、将spring dataSource注入到log4j2中.
 * 2、将web的的默认log4j配置和当前的log4j配置合并
 */
@Component
public class ImsConfiguration {

	public ImsConfiguration() {
		try {
			//设置dataSource
			DataSource dataSource = SpringUtils.getBean("dataSource");
			ConnectionFactory.setDataSource(dataSource);
			//读取当前包下的log4j配置
			URL log4jConfigFile = this.getClass().getClassLoader().getResource("log4j2-dev.xml");
			ConfigurationSource configSource = new ConfigurationSource(log4jConfigFile.openStream(), log4jConfigFile);
			Log4jContextFactory factory = new Log4jContextFactory();
			LoggerContext ct = factory.getContext(ImsConfiguration.class.getName(), this.getClass().getClassLoader(), null, true, configSource);
			Map<String, LoggerConfig> loggers = ct.getConfiguration().getLoggers();

			//获取全局的log4j配置,参数为false表示非当前的。
			final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			Configuration config = ctx.getConfiguration();

			//将当前的配置增加到全局的配置中
			Set<Entry<String, LoggerConfig>> set = loggers.entrySet();
			for (Entry<String, LoggerConfig> entry : set) {
				config.addLogger(entry.getKey(), entry.getValue());
			}
			//更新全局的log4j配置
			ctx.updateLoggers();
		} catch (Exception e) {
			//为了不影响应用，直接吃掉异常。
			e.printStackTrace();
		}
	}
}
 