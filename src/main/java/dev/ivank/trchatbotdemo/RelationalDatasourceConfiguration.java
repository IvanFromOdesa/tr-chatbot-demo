package dev.ivank.trchatbotdemo;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource({"classpath:persistence-db.properties"})
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "dev.ivank.trchatbotdemo.security.auth",
                "dev.ivank.trchatbotdemo.report",
                "dev.ivank.trchatbotdemo.chat",
                "dev.ivank.trchatbotdemo.kb"
        },
        entityManagerFactoryRef = "relationalEntityManagerFactory",
        transactionManagerRef = "relationalTransactionManager"
)
public class RelationalDatasourceConfiguration {
    @Primary
    @Bean(name = "relationalDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.relational")
    public DataSource relationalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "relationalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean relationalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("relationalDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages(
                        "dev.ivank.trchatbotdemo.security.auth",
                        "dev.ivank.trchatbotdemo.report",
                        "dev.ivank.trchatbotdemo.chat",
                        "dev.ivank.trchatbotdemo.kb"
                )
                .persistenceUnit("relational")
                .build();
    }

    @Primary
    @Bean(name = "relationalTransactionManager")
    public PlatformTransactionManager relationalTransactionManager(
            @Qualifier("relationalEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
