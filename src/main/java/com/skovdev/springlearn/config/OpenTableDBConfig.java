package com.skovdev.springlearn.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class OpenTableDBConfig {

    @Bean
    DataSource configurate() throws IOException, SQLException {
        EmbeddedPostgres embeddedPostgres;
        embeddedPostgres = EmbeddedPostgres.builder()
                .setPort(5433).start();

//        try (Connection conn = embeddedPostgres.getPostgresDatabase().getConnection()) {
//            Statement statement = conn.createStatement();
//            statement.execute("CREATE DATABASE SpringLearnInMemDB IF NOT EXISTS ");
//        }

        return embeddedPostgres.getPostgresDatabase();
    }
}
