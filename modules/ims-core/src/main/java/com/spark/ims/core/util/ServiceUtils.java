package com.spark.ims.core.util;

import com.spark.ims.common.domain.BaseRelationModel;
import com.spark.ims.common.util.ClassUtils;
import com.spark.ims.common.util.SpringUtils;
import com.spark.ims.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.spark.ims.service.IBaseService;

import java.io.Serializable;

public class ServiceUtils<T extends BaseRelationModel, ID extends Serializable> {
	
	public static final Logger logger = LoggerFactory
			.getLogger(ServiceUtils.class);

	/**
	 * 获取目标模型对应的service
	 *
	 * @param targetType
	 *            目标模型类型
	 * @return IBaseService
	 */
	public static IBaseService getTargetService(String targetType) {
		String targetServiceName = ClassUtils
				.getLowerCamelAndSingularize(targetType) + "Service";
		IBaseService targetService = SpringUtils.getBean(targetServiceName);
		if (targetService == null) {
			logger.error("can't find principalService : " + targetServiceName);
			throw new SystemException("can't find principalService : "
					+ targetServiceName);
		}
		return targetService;
	}
}
