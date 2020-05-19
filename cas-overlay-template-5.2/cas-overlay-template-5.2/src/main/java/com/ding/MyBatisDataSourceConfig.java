package com.ding;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;
/**
 * 還需要在下面这个问题配置  不然spring不会管理这个文件
 * O:\cas\cas-overlay-template-5.2\cas-overlay-template-5.2\src\main\resource\META-INF\spring.factories
 */
@Configuration
public class MyBatisDataSourceConfig {
    //配置数据源
    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource dataSource= new DriverManagerDataSource("jdbc:mysql://127.0.0.1:3306/blog","root","123456");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        return dataSource;
    }

    //配置 mybatis自动扫描 Mapper
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.ding");
        return mapperScannerConfigurer;
    }
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setMapperLocations();
        factoryBean.setDataSource(dataSource());
        ResourcePatternResolver resourcePatternResolver=new PathMatchingResourcePatternResolver();
        //配置扫描对应路径的xml
        resourcePatternResolver.getResources("classpath*:mapper/*.xml");
        factoryBean.setMapperLocations(resourcePatternResolver.getResources("classpath*:mapper/*.xml"));
        return factoryBean.getObject();
    }
}
