package com.agilsistemas.replication_example.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Essa config se aplica para um bd remoto, que nem sempre pode estar
 * disponível. Por isso, definimos hibernate.hbm2ddl.auto como none, para que a
 * app ainda suba, mesmo sem a conexão.
 * 
 * @author YuriFernandes150
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.agilsistemas.replication_example.repository.remote", entityManagerFactoryRef = "remoteDbEntityManager", transactionManagerRef = "remoteDbTransactionManager")
public class RemoteDBConfig {

    /*
     * As configs aqui são parecidas com as da classe do banco local, mas note que
     * não utilizamos @Primary em nenhum dos Beans.
     * 
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean remoteDbEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(remoteDataSource());
        em.setPackagesToScan("com.agilsistemas.replication_example.model");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;

    }

    @Bean
    public DataSource remoteDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:49153/dbflex");
        dataSource.setUsername("postgres");
        dataSource.setPassword("031212");

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager remoteDbTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(remoteDbEntityManager().getObject());
        return transactionManager;
    }

}
