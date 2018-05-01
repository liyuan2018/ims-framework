package com.spark.ims.core.annotation;

import javax.validation.ConstraintValidatorContext;

/**
 * Created by liyuan on 2018/4/26.
 * 校验异常信息包装工具类
 */
public class MessageWrappingUtils {

    /**
     * 包装错误信息
     *
     * @param fieldName 字段名
     * @param message   消息
     * @param context   错误信息上下文
     */
    public static void addMessageToContext(String fieldName, String message, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addPropertyNode(fieldName).addConstraintViolation();
    }

}
