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
		"zcla71.baudoze.tarefa"
	},
	entityManagerFactoryRef = "userEntityManagerFactory",
	transactionManagerRef = "userTransactionManager"
)
public class UserDatabaseConfig {
	@Bean
	public DataSource userDataSource() throws IOException {
		Path dbPath = Path.of("data", "users", "teste.sqlite");
		Files.createDirectories(dbPath.getParent());

		SQLiteConfig sqlite = new SQLiteConfig();
		sqlite.enforceForeignKeys(true);
		sqlite.setBusyTimeout(10_000);

		SQLiteDataSource ds = new SQLiteDataSource(sqlite);
		ds.setUrl("jdbc:sqlite:" + dbPath);

		return ds;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
			EntityManagerFactoryBuilder builder) throws IOException {
		return builder
				.dataSource(userDataSource())
				.packages(
					"zcla71.baudoze.tarefa"
				)
				.persistenceUnit("user")
				.build();
	}

	@Bean
	public PlatformTransactionManager userTransactionManager(
			@Qualifier("userEntityManagerFactory")
			EntityManagerFactory emf) {
		return new JpaTransactionManager(Objects.requireNonNull(emf));
	}
}
