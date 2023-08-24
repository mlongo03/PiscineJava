package edu.Roma42.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;

public class EmbeddedDataSourceTest {

    private DataSource dataSource;

    @BeforeEach
    void init() {
        EmbeddedDatabase db = new EmbeddedDatabaseBuilder()
            .addScript("schema.sql")
            .addScript("data.sql")
            .build();
        dataSource = db;
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(dataSource.getConnection());
    }
}

