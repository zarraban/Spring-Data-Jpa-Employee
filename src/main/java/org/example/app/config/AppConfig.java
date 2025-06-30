package org.example.app.config;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@ComponentScan(value = "org.example.app")
@PropertySource("classpath:application.properties")
@EnableJpaRepositories(basePackages = "org.example.app.repository", 
        entityManagerFactoryRef = "entityManagerFactory")
@EnableTransactionManagement
public class AppConfig {

    private final Environment environment;

    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);

    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("dbDriver"), "dbDriver must not be null"));
        dataSource.setUrl(Objects.requireNonNull(environment.getProperty("dbUrl"), "dbUrl must not be null"));
        dataSource.setUsername(Objects.requireNonNull(environment.getProperty("dbUsername"), "dbUsername must not be null"));
        dataSource.setPassword(Objects.requireNonNull(environment.getProperty("dbPassword"), "dbPassword must not be null"));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("org.example.app.entity");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setJpaProperties(additionalProperties());
        localContainerEntityManagerFactoryBean.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class);
        // Ensure the factory bean is fully initialized
        localContainerEntityManagerFactoryBean.afterPropertiesSet();

        return localContainerEntityManagerFactoryBean;
    }


    Properties additionalProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.hbm2ddl.auto","validate");
        properties.put("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }


}
