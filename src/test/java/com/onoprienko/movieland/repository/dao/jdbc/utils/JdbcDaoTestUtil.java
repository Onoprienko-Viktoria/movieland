package com.onoprienko.movieland.repository.dao.jdbc.utils;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class JdbcDaoTestUtil {
    private final static String CREATE_SCRIPT_PATH = "/create-tables-before.sql";
    private final static String DELETE_SCRIPT_PATH = "/delete-tables-after.sql";

    private final static String TEST_DB_URL = "jdbc:postgresql://localhost:5433/test_movieland";
    private final static String TEST_DB_USER = "user";
    private final static String TEST_DB_PASS = "pass";


    public static void createTables() throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readContent(CREATE_SCRIPT_PATH))) {
            preparedStatement.execute();
        }
    }


    public static void dropTables() throws IOException, SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(readContent(DELETE_SCRIPT_PATH))) {
            preparedStatement.execute();
        }
    }

    private static String readContent(String path) throws IOException {
        File file = new File(path);
        try (InputStream inputStream = new FileInputStream(file)) {
            byte[] text = new byte[(int) file.length()];
            inputStream.read(text);
            return new String(text);
        }
    }


    public static DataSource getDatasource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(TEST_DB_URL);
        hikariDataSource.setUsername(TEST_DB_USER);
        hikariDataSource.setPassword(TEST_DB_PASS);
        return hikariDataSource;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(TEST_DB_URL,
                TEST_DB_USER,
                TEST_DB_PASS);
    }
}
