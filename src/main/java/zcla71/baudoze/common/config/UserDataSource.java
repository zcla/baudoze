package zcla71.baudoze.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.AbstractDataSource;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

public class UserDataSource extends AbstractDataSource {
	@Override
	public Connection getConnection() throws SQLException {
		Long userId = UserContext.getUserId();

		Path dbPath = Path.of("data", "users", userId + ".sqlite");
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

		return ds.getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}
}
