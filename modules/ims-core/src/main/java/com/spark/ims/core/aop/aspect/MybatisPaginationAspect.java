package com.spark.ims.core.aop.aspect;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * mybatis-mapper切面实现分页
 * 对结果集特殊转换成Jpa-page对象
 *  Created by liyuan on 2018/4/26.
 */
@Aspect
@Component
public class MybatisPaginationAspect {


    public static Logger logger = LoggerFactory.getLogger(MybatisPaginationAspect
            .class);

    /**
     * 使用mybatis的PageHelper插件，在mapper的所有find方法拦截
     */
    @Around(value = "com.spark.ims.core.aop.pointcut.PointCutDefine.aroundMybatisPaginationQuery()")
    public Object aroundMybatisQuery(ProceedingJoinPoint pjp) throws Throwable{
        Object[] args = pjp.getArgs();
        Pageable pageable = null;
        for (Object object : args) {
            if(object instanceof Pageable&&object!=null) {
                logger.debug("mybatis拦截查询分页处理开始");
                pageable = (Pageable) object;
                PageHelper.startPage(pageable.getPageNumber() + 1, pageable.getPageSize());
            }
        }
        Object retVal = pjp.proceed();
        if(retVal instanceof Page ){
            Page page = (Page)retVal;
            if (page.getContent().size()==1 && page.getContent().get(0) instanceof  PageInfo){
                PageInfo pageInfo =(PageInfo)(page.getContent().get(0));
                Page result = new PageImpl(pageInfo.getList(),pageable,pageInfo.getTotal());
                logger.debug("mybatis拦截查询分页处理结束");
                return result;
            }
            else
                return retVal;
        }
        else
            return retVal ;
    }


}
