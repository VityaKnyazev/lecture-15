package by.itacademy.javaenterprise.knyazev.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:database.properties")
@ComponentScan(basePackages = {"by.itacademy.javaenterprise.knyazev.entities", "by.itacademy.javaenterprise.knyazev.services", "by.itacademy.javaenterprise.knyazev.dto", "by.itacademy.javaenterprise.knyazev.dao"})
public class AppConfig {
	@Autowired
	Environment env;
	
	@Bean
	HikariConfig hikariConfig() {
//		HikariConfig hikariConfig = new HikariConfig("src/main/resources/database.properties");
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName(env.getProperty("driverClassName"));
		hikariConfig.setJdbcUrl(env.getProperty("jdbcUrl"));
		hikariConfig.setUsername(env.getProperty("dataSource.user"));
		hikariConfig.setPassword(env.getProperty("dataSource.password"));
		hikariConfig.setTransactionIsolation(env.getProperty("dataSource.transactionIsolation"));
		hikariConfig.setMaximumPoolSize(Integer.valueOf(env.getProperty("dataSource.maxPoolSize")));
		return hikariConfig;
	}
	
	@Bean
	DataSource hikariDataSource() {
		DataSource hikariDataSource = new HikariDataSource(hikariConfig());
		return hikariDataSource;
	}
	
	@Bean
	JdbcTemplate jdbcCategoriesDAO() {
		return new JdbcTemplate(hikariDataSource());		
	}
	
	@Bean
	JdbcTemplate jdbcProducersDAO() {
		return new JdbcTemplate(hikariDataSource());		
	}
	
	@Bean
	NamedParameterJdbcTemplate jdbcGoodsDAO() {
		return new NamedParameterJdbcTemplate(hikariDataSource());
	}
	
	
	@Bean
	Logger categoriesDAOLogger() {
		return LoggerFactory.getLogger("CategoriesDAO");
	}
	
	@Bean
	Logger producersDAOLogger() {
		return LoggerFactory.getLogger("ProducersDAO");
	}
	
	@Bean
	Logger goodsDAOLogger() {
		return LoggerFactory.getLogger("GoodsDAO");
	}
	
	@Bean
	Logger productServiceLogger() {
		return LoggerFactory.getLogger("ProductService");
	}
	
	@Bean
	Logger appLogger() {
		return LoggerFactory.getLogger("App");
	}
}
