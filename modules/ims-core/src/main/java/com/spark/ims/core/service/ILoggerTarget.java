package com.spark.ims.core.service;


import com.spark.ims.core.model.LoggerModel;

public interface ILoggerTarget {
	/**
	 * 记录错误信息
	 * @param model  信息对象
	 */
	void error(LoggerModel model);
	/**
	 * 记录业务访问信息
	 * @param model 信息对象
	 */
	void info(LoggerModel model);

	/**
	 * 获取输出类型
	 */
	String getType();
}
