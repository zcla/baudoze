package zcla71.baudoze.common.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class UserDatabaseInitializer {
    private final DataSource userDataSource;

    public UserDatabaseInitializer(
            @Qualifier("userDataSource")
            DataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

	@PostConstruct
	void init() throws SQLException, IOException {
		try (Connection c = userDataSource.getConnection()) {
			ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema-user.sql"));
			populator.setSeparator(";");
			populator.execute(Objects.requireNonNull(userDataSource));
		}
	}
}
