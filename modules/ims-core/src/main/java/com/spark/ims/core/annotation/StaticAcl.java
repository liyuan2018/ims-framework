package com.spark.ims.core.annotation;


import com.spark.ims.common.domain.StaticAclType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liyuan on 2018/4/26.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface StaticAcl {

    /**
     * 类型
     * @return
     */
    StaticAclType type() ;

}
