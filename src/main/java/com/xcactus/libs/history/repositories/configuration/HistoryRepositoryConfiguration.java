/**
 * 
 */
package com.xcactus.libs.history.repositories.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import liquibase.integration.spring.SpringLiquibase;

/**
 * @author maciej.sowa
 *
 */
@Configuration
//@ComponentScan("com.xcactus.libs.history.services")
@EnableJpaAuditing
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "historyEntityManagerFactory",
	transactionManagerRef = "historyTransactionManager",
	basePackages = { "com.xcactus.libs.history.repositories" })
@EntityScan("com.xcactus.libs.history.jpa")
public class HistoryRepositoryConfiguration {
	
	@Bean(name = "historyDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.history")
	public DataSource historyDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "historyEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean historyEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("historyDataSource") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.xcactus.libs.history.jpa").persistenceUnit("history").build();
	}

	@Bean(name = "historyTransactionManager")
	public PlatformTransactionManager historyTransactionManager(
			@Qualifier("historyEntityManagerFactory") EntityManagerFactory historyEntityManagerFactory) {
		return new JpaTransactionManager(historyEntityManagerFactory);
	}
	
	@Bean(name = "historyLiquibase")
	public SpringLiquibase historyLiquibase() {
	    SpringLiquibase liquibase = new SpringLiquibase();
	    liquibase.setChangeLog("classpath:/liquibase/changelog-history-repository.xml");
	    liquibase.setDataSource(historyDataSource());
	    return liquibase;
	}
}
