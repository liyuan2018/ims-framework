package com.spark.ims.core.aop.pointcut;

import org.aspectj.lang.annotation.Pointcut;

/**
 * AOP切面点定义
 * Created by liyuan on 2018/4/26.
 */
public class PointCutDefine {

    public static final String MYBATIS_MAPPER_EXECUTION = "execution(* com.spark..mapper..find*(..))  ||  execution(* com.spark..mybatis..find*(..))";
    /**
     * mybatis-mapper查询分页
     */
    @Pointcut(MYBATIS_MAPPER_EXECUTION)
    public void aroundMybatisPaginationQuery() {
    }
}
