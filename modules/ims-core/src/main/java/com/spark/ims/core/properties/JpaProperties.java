package com.spark.ims.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 数据源JPA配置信息
 *
 * Created by liyuan on 2018/4/26.
 */
@ConfigurationProperties(prefix = "spring.jpa")
public class JpaProperties {

    private String dialect;
    private String showSql;
    private String formatSql;
    private String hibernateNamingStrategy;
    private String entityPackage;
    private String hibernateEjbInterceptor;
    /** 批量操作记录数, 默认值: 100 */
    private String hibernateJdbcBatchSize = "100";

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public String getShowSql() {
        return showSql;
    }

    public void setShowSql(String showSql) {
        this.showSql = showSql;
    }
    
    public String getFormatSql() {
		return formatSql;
	}

	public void setFormatSql(String formatSql) {
		this.formatSql = formatSql;
	}

	public String getHibernateNamingStrategy() {
        return hibernateNamingStrategy;
    }

    public void setHibernateNamingStrategy(String hibernateNamingStrategy) {
        this.hibernateNamingStrategy = hibernateNamingStrategy;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getHibernateEjbInterceptor() {
        return hibernateEjbInterceptor;
    }

    public void setHibernateEjbInterceptor(String hibernateEjbInterceptor) {
        this.hibernateEjbInterceptor = hibernateEjbInterceptor;
    }

    public String getHibernateJdbcBatchSize() {
        return hibernateJdbcBatchSize;
    }

    public void setHibernateJdbcBatchSize(String hibernateJdbcBatchSize) {
        this.hibernateJdbcBatchSize = hibernateJdbcBatchSize;
    }
}
