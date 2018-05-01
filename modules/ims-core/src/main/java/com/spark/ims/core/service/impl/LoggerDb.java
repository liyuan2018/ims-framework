package com.spark.ims.core.service.impl;

import com.spark.ims.common.util.DateUtils;
import com.spark.ims.common.util.UUIDUtils;
import com.spark.ims.core.model.LoggerModel;
import com.spark.ims.core.observer.TargetFactory;
import com.spark.ims.core.service.ILoggerTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 输出到数据库
 */
public class LoggerDb implements ILoggerTarget {
    private static final Logger logger = LoggerFactory.getLogger(TargetFactory.DB);

    @Override
    public void error(LoggerModel model) {
        MDC.put("id", UUIDUtils.generate());
        MDC.put("principalId", model.getPrincipalId());
        MDC.put("principalType", model.getPrincipalType());
        MDC.put("userId", model.getUserId());
        MDC.put("createTime", DateUtils.getNowTime());
        MDC.put("ip", model.getIp());
        MDC.put("usedTime", String.valueOf(model.getUsedTime()));
        MDC.put("event", model.getEvt().toString());
        MDC.put("type", model.getType());
        MDC.put("operateObject", model.getOperateObject());
        logger.error(model.getMessage());
    }

    @Override
    public void info(LoggerModel model) {
        MDC.put("id", UUIDUtils.generate());
        MDC.put("principalId", model.getPrincipalId());
        MDC.put("principalType", model.getPrincipalType());
        MDC.put("userId", model.getUserId());
        MDC.put("createTime", DateUtils.getNowTime());
        MDC.put("ip", model.getIp());
        MDC.put("usedTime", String.valueOf(model.getUsedTime()));
        MDC.put("event", model.getEvt().toString());
        MDC.put("type", model.getType());
        MDC.put("operateObject", model.getOperateObject());
        logger.info(model.getMessage());
    }

    @Override
    public String getType() {
        return TargetFactory.DB;
    }

}