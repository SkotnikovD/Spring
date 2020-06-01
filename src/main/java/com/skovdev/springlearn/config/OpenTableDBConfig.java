package com.skovdev.springlearn.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
@Profile("local")
public class OpenTableDBConfig {

    @Bean
    DataSource configurate() throws IOException, SQLException {
        EmbeddedPostgres embeddedPostgres;
        embeddedPostgres = EmbeddedPostgres.builder()
                .setPort(5433)
                .setServerConfig("logging_collector", "on")
                .setServerConfig("log_statement", "all")
                .start();

//        try (Connection conn = embeddedPostgres.getPostgresDatabase().getConnection()) {
//            Statement statement = conn.createStatement();
//            statement.execute("CREATE DATABASE SpringLearnInMemDB IF NOT EXISTS ");
//        }

        return embeddedPostgres.getPostgresDatabase();
    }
}
