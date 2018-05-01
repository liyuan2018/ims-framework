package com.spark.ims.core.config;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by liyuan on 2018/4/26.
 * 为log4j提供一个数据库连接
 */
public class ConnectionFactory {
	private static DataSource dataSource = null;
	
	public static Connection getDatabaseConnection() throws SQLException {
		return dataSource.getConnection();
	}
	
	/**
	 * 在logg读取getDatabaseConnectionzhiq
	 * @param ds datasource
	 */
	public static void setDataSource(DataSource ds) {
		dataSource = ds;
	}
}