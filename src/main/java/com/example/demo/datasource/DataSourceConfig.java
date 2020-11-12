package com.example.demo.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * mysql 数据库配置
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.dao", sqlSessionTemplateRef = "sqlSessionTemplate")
public class DataSourceConfig {

	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource setDataSource() {
		 return new DruidDataSource();
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager  setTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory setSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		//-----------------------------------------------------------
		//分页插件,插件无非是设置mybatis的拦截器
		PageHelper pageHelper = new PageHelper();
	    Properties properties = new Properties();
	    properties.setProperty("reasonable", "true");
	    properties.setProperty("supportMethodsArguments", "true");
	    properties.setProperty("returnPageInfo", "check");
	    properties.setProperty("params", "count=countSql");
	    pageHelper.setProperties(properties);
	    //添加插件 pageHelper版本要对，不然会报错
	    bean.setPlugins(new Interceptor[]{pageHelper});
	   //-----------------------------------------------------------
		bean.setDataSource(dataSource);
		bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*.xml"));
		return bean.getObject();
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
