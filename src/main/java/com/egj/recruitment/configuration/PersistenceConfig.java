package com.egj.recruitment.configuration;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Erikson Matondang on 7/15/2017.
 */
@Configuration
@EnableTransactionManagement
@PropertySource({
        "classpath:config/db.properties"
})
@ComponentScan(
        basePackages = {
                "com.egj.recruitment.service.impl",
                "com.egj.recruitment.dao"
        }
)
public class PersistenceConfig implements ApplicationContextAware {
    private ApplicationContext applicationContext;
    private Environment environment;


    @Bean
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("db.driver"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.username"));
        dataSource.setPassword(environment.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory=new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(applicationContext.getResource(environment.getProperty("mybatis.sessionFactory.configLocation")));

        List<Resource> xmlMapperLocations=new ArrayList<Resource>();
        for(String xmlMapper:environment.getProperty("mybatis.xml.mapper.locations").split(",")){
            xmlMapperLocations.addAll(Arrays.asList(applicationContext.getResources(xmlMapper)));
        }
        sqlSessionFactory.setMapperLocations(xmlMapperLocations.toArray(new Resource[xmlMapperLocations.size()]));
        return  sqlSessionFactory;
    }

    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactoryBean sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory.getObject());
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setAnnotationClass(Repository.class);
        mapperScannerConfigurer.setBasePackage(environment.getProperty("mybatis.java.mapper.package"));
        return mapperScannerConfigurer;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
        this.environment=applicationContext.getEnvironment();
    }
}
