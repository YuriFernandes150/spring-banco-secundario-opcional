package com.agilsistemas.replication_example.config;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Essa Config cuida do banco local. Indicados aqui estão propriedades e pacotes
 * que usarão essa conexão de bd.
 * Definimos os packages e os managers.
 * 
 * @author YuriFernandes150
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.agilsistemas.replication_example.repository.local", entityManagerFactoryRef = "localDbEntityManager", transactionManagerRef = "localDbTransactionManager")
public class LocalDBConfig {

    /**
     * Criamos o Entity manager primário, esse banco vai ser o principal utilizado
     * pela aplicação.
     * 
     * @return um objeto entityManager para o Spring.
     */
    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean localDbEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(localDataSource());
        em.setPackagesToScan("com.agilsistemas.replication_example.model");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;

    }

    /**
     * O datasource local, contendo as propriedades básicas da conexão.
     * @return Um objeto de datasource para o Spring usar.
     */
    @Primary
    @Bean
    public DataSource localDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5434/dbflex");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres");

        return dataSource;
    }

    /**
     * O gerenciador de transações para o banco local.
     * @return Um objeto TransactionManager para o Spring usar.
     */
    @Primary
    @Bean
    public PlatformTransactionManager localDbTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(localDbEntityManager().getObject());
        return transactionManager;
    }

}
