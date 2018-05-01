package com.spark.ims.core.annotation;

import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * Created by liyuan on 2018/4/26.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
@RestController
public @interface Rest{
    Class value() default Rest.class;
    RequestMethod[] method() default {};
    String[] params() default {};
    String[] headers() default {};
    String[] consumes() default {};
    String[] produces() default {};
}