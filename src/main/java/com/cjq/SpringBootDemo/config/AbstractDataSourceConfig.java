package com.cjq.SpringBootDemo.config;

import java.util.Properties;

import javax.sql.DataSource;
import org.springframework.core.env.Environment;
import com.atomikos.jdbc.AtomikosDataSourceBean;

/**
 * 针对springboot的数据源配置
 */
public abstract class AbstractDataSourceConfig {
 
    protected DataSource getDataSource(Environment env,String dbPrefix,String poolPrefix,String dataSourceName){
        Properties prop = build(env,dbPrefix,poolPrefix);
        AtomikosDataSourceBean ds = new AtomikosDataSourceBean();
        ds.setXaDataSourceClassName("com.alibaba.druid.pool.xa.DruidXADataSource");
        ds.setUniqueResourceName(dataSourceName);
        ds.setXaProperties(prop);
        return ds;
    }
 
    /**
     * 主要针对druid数据库链接池
     * @param env
     * @param prefix
     * @return
     */
    protected Properties build(Environment env, String dbPrefix,String poolPrefix) {
        Properties prop = new Properties();
        prop.put("url", env.getProperty(dbPrefix + "url"));
        prop.put("username", env.getProperty(dbPrefix + "username"));
        prop.put("password", env.getProperty(dbPrefix + "password"));
        prop.put("driverClassName", env.getProperty(dbPrefix + "driverClassName", ""));
        prop.put("initialSize", env.getProperty(poolPrefix + "initialSize", Integer.class));
        prop.put("maxActive", env.getProperty(poolPrefix + "maxActive", Integer.class));
        prop.put("minIdle", env.getProperty(poolPrefix + "minIdle", Integer.class));
        prop.put("maxWait", env.getProperty(poolPrefix + "maxWait", Integer.class));
        prop.put("timeBetweenEvictionRunsMillis", env.getProperty(poolPrefix + "timeBetweenEvictionRunsMillis", Integer.class));
        prop.put("minEvictableIdleTimeMillis", env.getProperty(poolPrefix + "minEvictableIdleTimeMillis", Integer.class));
        prop.put("validationQuery", env.getProperty(poolPrefix + "validationQuery"));
        prop.put("testWhileIdle", env.getProperty(poolPrefix + "testWhileIdle", Boolean.class));
        prop.put("testOnBorrow", env.getProperty(poolPrefix + "testOnBorrow", Boolean.class));
        prop.put("testOnReturn", env.getProperty(poolPrefix + "testOnReturn", Boolean.class));
        prop.put("poolPreparedStatements", env.getProperty(poolPrefix + "poolPreparedStatements", Boolean.class));
        prop.put("maxPoolPreparedStatementPerConnectionSize",
                env.getProperty(poolPrefix + "maxPoolPreparedStatementPerConnectionSize", Integer.class));
        prop.put("filters", env.getProperty(poolPrefix + "filters"));
        return prop;
    }
}

