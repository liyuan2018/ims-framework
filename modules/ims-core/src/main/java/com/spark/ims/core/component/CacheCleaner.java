package com.spark.ims.core.component;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TransactionRequiredException;

/**
 * Jpa和Mybatis缓存清除
 * 1. 省得在Mybatis的Mapper中配置:flushCache="true" useCache="false"
 * 2. 省得一直调用repository.saveAndFlush, repository.flush()
 *
 * 用法:
 * 1. 注入service层:
 * <code>
 *     @Autowired
 *     private CacheCleaner cacheCleaner;
 * </code>
 * 2. 在jpa保存或修改后, 需要执行mybatis复杂查询的前面调用: cacheCleaner.flush()
 */
@Component
public class CacheCleaner {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SqlSession sqlSession;

    /**
     * Synchronize the persistence context to the
     * underlying database.
     * @throws TransactionRequiredException if there is
     *         no transaction
     * @throws PersistenceException if the flush fails
     */
    public void flush() {
        entityManager.flush();
        sqlSession.clearCache();
    }
}