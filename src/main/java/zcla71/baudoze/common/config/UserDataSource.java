package zcla71.baudoze.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class UserDataSource extends AbstractDataSource {
	@Override
	public Connection getConnection() throws SQLException {
		Long userId = UserContext.getUserId();

		// O "_bootstrap" é um banco "dummy", só pra não quebrar o sistema quando ele chega aqui na inicialização
		Path dbPath = Path.of("data", "users", (userId == null ? "_bootstrap" : "bau." + userId) + ".sqlite");
		try {
			Files.createDirectories(dbPath.getParent());
		} catch (IOException e) {
			// Não tem muito o que se possa fazer...
			throw new RuntimeException(e);
		}

		SQLiteConfig sqlite = new SQLiteConfig();
		sqlite.enforceForeignKeys(true);
		sqlite.setBusyTimeout(10_000);

		SQLiteDataSource ds = new SQLiteDataSource(sqlite);
		ds.setUrl("jdbc:sqlite:" + dbPath);

		if (!Files.exists(dbPath)) {
			try (Connection ignored = ds.getConnection()) {
				// força a criação do arquivo
			}
			// Inicializa o banco
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema-user.sql"));
			populator.setSeparator(";");
			populator.execute(ds);
		}

		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}
}
