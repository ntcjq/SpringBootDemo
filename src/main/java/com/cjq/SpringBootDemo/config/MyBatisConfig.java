package com.cjq.SpringBootDemo.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.cjq.SpringBootDemo.dao.multipleDataSource.DynamicDataSource;
import com.cjq.SpringBootDemo.dao.multipleDataSource.mybatis.CustomSqlSessionTemplate;

@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, sqlSessionTemplateRef = "sqlSessionTemplate")
public class MyBatisConfig extends AbstractDataSourceConfig {
 
    //mapper模式下的接口层
    static final String BASE_PACKAGE = "com.cjq.SpringBootDemo.mapper";
 
    //对接数据库的实体层
    static final String ALIASES_PACKAGE = "com.cjq.SpringBootDemo.domain";
 
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/*.xml";
 
 
    @Primary
    @Bean(name = "dataSourceDefault")
    public DataSource dataSourceOne(Environment env) {
        String dbPrefix = "spring.datasource.default.";
        String poolPrefix = "spring.datasource.druid.";
        return getDataSource(env,dbPrefix,poolPrefix,"ds_default");
    }
 
    @Bean(name = "dataSourceTwo")
    public DataSource dataSourceTwo(Environment env) {
    	String dbPrefix = "spring.datasource.two.";
        String poolPrefix = "spring.datasource.druid.";
        return getDataSource(env,dbPrefix,poolPrefix,"ds_two");
    }
 
 
 
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("dataSourceDefault")DataSource dataSourceDefault,@Qualifier("dataSourceTwo")DataSource dataSourceTwo) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("ds_default",dataSourceDefault);
        targetDataSources.put("ds_two",dataSourceTwo);
 
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(dataSourceDefault);
        return dataSource;
    }
 
    @Bean(name = "sqlSessionFactoryDefault")
    public SqlSessionFactory sqlSessionFactoryDefault(@Qualifier("dataSourceDefault") DataSource dataSource)
        throws Exception {
        return createSqlSessionFactory(dataSource);
    }
 
    @Bean(name = "sqlSessionFactoryTwo")
    public SqlSessionFactory sqlSessionFactoryTwo(@Qualifier("dataSourceTwo") DataSource dataSource)
        throws Exception {
        return createSqlSessionFactory(dataSource);
    }
 
 
 
 
    @Bean(name = "sqlSessionTemplate")
    public CustomSqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactoryDefault")SqlSessionFactory factoryDefault,@Qualifier("sqlSessionFactoryTwo")SqlSessionFactory factoryTwo) throws Exception {
        Map<Object,SqlSessionFactory> sqlSessionFactoryMap = new HashMap<>();
        sqlSessionFactoryMap.put("ds_default",factoryDefault);
        sqlSessionFactoryMap.put("ds_two",factoryTwo);
        CustomSqlSessionTemplate customSqlSessionTemplate = new CustomSqlSessionTemplate(factoryDefault);
        customSqlSessionTemplate.setTargetSqlSessionFactorys(sqlSessionFactoryMap);
        return customSqlSessionTemplate;
    }
 
    /**
     * 创建数据源
     * @param dataSource
     * @return
     */
    private SqlSessionFactory createSqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setVfs(SpringBootVFS.class);
        bean.setTypeAliasesPackage(ALIASES_PACKAGE);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return bean.getObject();
    }
}
