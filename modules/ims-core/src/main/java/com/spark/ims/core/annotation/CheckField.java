package com.spark.ims.core.annotation;



import com.spark.ims.core.annotation.impl.CheckFieldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by liyuan on 2018/4/26.
 * 重复输入校验注解，两次输入不一致返回错误信息(例：密码)
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CheckFieldValidator.class)
@Documented
public @interface CheckField {
    /**
     * 字段名 *
     */
    String field() default "";

    /**
     * 校验字段名 *
     */
    String verifyField() default "";


    /**
     * 默认错误消息
     */
    String message() default "";

    /**
     * 分组
     */
    Class<?>[] groups() default {};

    /**
     * 负载
     */
    Class<? extends Payload>[] payload() default {};

}
