package com.raja.smr.config.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

@Configuration
public class DataSourceConfig {

	@Value("${spring.datasource.username}")
	private String username;
	
	@Value("${spring.datasource.password}")
	private String password;
	
	@Value("${spring.datasource.driver-class-name}")
	private String driverClass;
	
	@Value("${spring.datasource.url}")
	private String url;
	
	@Value("${spring.datasource.druid.max-active}")
	private Integer maxActive;
	
	@Value("${spring.datasource.druid.max-wait-millis}")
	private long maxWait;
	
	@Value("${spring.datasource.druid.initial-size}")
	private int initialSize;
	
	@Value("${spring.datasource.druid.test-while-idle}")
	private boolean testWhileIdle;
	
	@Value("${spring.datasource.druid.filters}")
	private String filters;
	
	@Value("${spring.datasource.druid.test-on-return}")
	private boolean testOnReturn;
	
	@Value("${spring.datasource.druid.test-on-borrow}")
	private boolean testOnBorrow;
	
	@Bean
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setInitialSize(initialSize);
		dataSource.setMaxActive(maxActive);
		dataSource.setMaxWait(maxWait);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);
		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return dataSource;
	}
	
	@Bean
	public ServletRegistrationBean statViewServlet() {
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		
		// 设置控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "root");
		servletRegistrationBean.addInitParameter("loginPassword", "root");
		
		// 是否可以重置数据
		servletRegistrationBean.addInitParameter("resetEnble", "false");
		
		return servletRegistrationBean;
	}
	
	@Bean
	public FilterRegistrationBean statFilter() {
		// 创建过滤器
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new WebStatFilter());
		// 设置过滤器路径
		filterRegistrationBean.addUrlPatterns("/*");
		// 忽略过滤的形式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		
		return filterRegistrationBean;
	}
	
}
