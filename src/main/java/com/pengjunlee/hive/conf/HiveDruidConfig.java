package com.pengjunlee.hive.conf;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "hive")
public class HiveDruidConfig {

	/*private String url;
	private String user;
	private String password;
	private String driverClassName;
	private int initialSize;
	private int minIdle;
	private int maxActive;
	private int maxWait;
	private int timeBetweenEvictionRunsMillis;
	private int minEvictableIdleTimeMillis;
	private String validationQuery;
	private boolean testWhileIdle;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private boolean poolPreparedStatements;
	private int maxPoolPreparedStatementPerConnectionSize;*/

	@Autowired
	private Environment env;

	@Bean(name = "hiveDruidDataSource")
	@Qualifier("hiveDruidDataSource")
	public DataSource dataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(env.getProperty("hive.url"));
		dataSource.setDriverClassName(env.getProperty("hive.driver-class-name"));
		dataSource.setUsername(env.getProperty("hive.user"));
		dataSource.setPassword(env.getProperty("hive.password"));

		dataSource.setInitialSize(Integer.valueOf(env.getProperty("hive.initialSize")));
		dataSource.setMinIdle(Integer.valueOf(env.getProperty("hive.minIdle")));
		dataSource.setMaxActive(Integer.valueOf(env.getProperty("hive.maxActive")));
		dataSource.setMaxWait(Integer.valueOf(env.getProperty("hive.maxWait")));
		dataSource.setTimeBetweenEvictionRunsMillis(Integer.valueOf(env.getProperty("hive.timeBetweenEvictionRunsMillis")));
		dataSource.setMinEvictableIdleTimeMillis(Integer.valueOf(env.getProperty("hive.minEvictableIdleTimeMillis")));
		dataSource.setValidationQuery(env.getProperty("hive.validationQuery"));
		dataSource.setTestWhileIdle(Boolean.valueOf(env.getProperty("hive.testWhileIdle")));
		dataSource.setTestOnBorrow(Boolean.valueOf(env.getProperty("hive.testOnBorrow")));
		dataSource.setTestOnReturn(Boolean.valueOf(env.getProperty("hive.testOnReturn")));
		dataSource.setPoolPreparedStatements(Boolean.valueOf(env.getProperty("hive.poolPreparedStatements")));
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(Integer.valueOf(env.getProperty("hive.maxPoolPreparedStatementPerConnectionSize")));
		return dataSource;
	}

    @Bean(name = "hiveDruidTemplate")
    public JdbcTemplate hiveDruidTemplate(@Qualifier("hiveDruidDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}