package com.spark.ims.core.service.impl;

import com.spark.ims.common.util.DateUtils;
import com.spark.ims.core.model.LoggerModel;
import com.spark.ims.core.observer.TargetFactory;
import com.spark.ims.core.service.ILoggerTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 输出到控制台
 */
public class LoggerConsole implements ILoggerTarget {
    private static final Logger logger = LoggerFactory.getLogger(TargetFactory.CONSOLE);

    @Override
    public void error(LoggerModel model) {
        MDC.put("user", model.getUserName());
        MDC.put("date", DateUtils.getCurrentDay());
        logger.error(model.getMessage());
    }

    @Override
    public void info(LoggerModel model) {
        MDC.put("user", model.getUserName());
        MDC.put("date", DateUtils.getNowTime());
        logger.info(model.getMessage());
    }

    @Override
    public String getType() {
        return TargetFactory.CONSOLE;
    }
}
