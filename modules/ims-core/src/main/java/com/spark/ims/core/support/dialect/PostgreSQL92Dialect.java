package com.spark.ims.core.support.dialect;

import org.hibernate.dialect.PostgreSQL9Dialect;

import java.sql.Types;

/**
 * hibernate postgresql9.2方言
 * Created by liyuan on 2018/4/26.
 */
public class PostgreSQL92Dialect extends PostgreSQL9Dialect {

    /**
     * Constructs a PostgreSQL92Dialect
     */
    public PostgreSQL92Dialect() {
        super();
        this.registerColumnType( Types.JAVA_OBJECT, "json" );
    }
}
