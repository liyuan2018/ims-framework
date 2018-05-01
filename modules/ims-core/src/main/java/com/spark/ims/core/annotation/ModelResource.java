package com.spark.ims.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liyuan on 2018/4/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelResource {

    /** identity */
    String identity() default "";
    /** 英文名称 */
    String name() default "";
    /** 描述 */
    String description() default "";

}
