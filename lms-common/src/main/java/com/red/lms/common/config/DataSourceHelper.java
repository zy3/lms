package com.red.lms.common.config;

import com.alibaba.druid.pool.DruidDataSource;

public class DataSourceHelper {

    public static void setCommonProperties(DruidDataSource dataSource) throws Exception {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setInitialSize(2);
        dataSource.setMinIdle(2);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(30000);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        dataSource.setValidationQuery("SELECT 'x'");
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(3600);
        dataSource.setLogAbandoned(true);
        dataSource.setFilters("stat");
        dataSource.setConnectionProperties("druid.stat.showSqlMills=100");
    }
}
