package zcla71.baudoze.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(
    basePackages = {
        "zcla71.baudoze.auth_user",
        "zcla71.baudoze.biblia"
    },
    entityManagerFactoryRef = "globalEntityManagerFactory",
    transactionManagerRef = "globalTransactionManager"
)
public class GlobalDatabaseConfig {
    @Bean
    @Primary
    public DataSource globalDataSource() throws IOException {
        Path dbPath = Path.of("data", "global.sqlite");
        Files.createDirectories(dbPath.getParent());

        SQLiteConfig sqlite = new SQLiteConfig();
        sqlite.enforceForeignKeys(true);
        sqlite.setBusyTimeout(10_000);

        SQLiteDataSource ds = new SQLiteDataSource(sqlite);
        ds.setUrl("jdbc:sqlite:" + dbPath);

        return ds;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean globalEntityManagerFactory(
            EntityManagerFactoryBuilder builder) throws IOException {
        return builder
                .dataSource(globalDataSource())
                .packages(
                    "zcla71.baudoze.auth_user",
                    "zcla71.baudoze.biblia"
                )
                .persistenceUnit("global")
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager globalTransactionManager(
            @Qualifier("globalEntityManagerFactory")
            EntityManagerFactory emf) {
        return new JpaTransactionManager(Objects.requireNonNull(emf));
    }
}
