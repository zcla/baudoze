package zcla71.baudoze.common.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

@Configuration
public class DatabaseConfig {
    @Bean
    public DataSource dataSource() throws IOException {
        Path dbPath = Path.of("data", "bau.sqlite");
        Files.createDirectories(dbPath.getParent());

        SQLiteConfig sqlite = new SQLiteConfig();
        sqlite.enforceForeignKeys(true);
        sqlite.setBusyTimeout(10_000);

        SQLiteDataSource ds = new SQLiteDataSource(sqlite);
        ds.setUrl("jdbc:sqlite:" + dbPath);

        return ds;
    }
}
