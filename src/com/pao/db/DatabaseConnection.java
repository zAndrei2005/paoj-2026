package com.pao.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Simple singleton DatabaseConnection. Loads configuration from resources/db.properties
 * and provides JDBC connections via DriverManager. Caller must close connections.
 */
public class DatabaseConnection {
    private final Properties props = new Properties();

    private DatabaseConnection() {
        loadProperties();
        try {
            String driver = props.getProperty("jdbc.driverClassName");
            if (driver != null && !driver.isEmpty()) {
                Class.forName(driver);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver class not found: " + e.getMessage());
        }
    }

    private void loadProperties() {
        try (InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("db.properties")) {
            if (is == null) {
                System.err.println("db.properties not found on classpath");
                return;
            }
            props.load(is);
        } catch (IOException e) {
            System.err.println("Failed to load db.properties: " + e.getMessage());
        }
    }

    private static class Holder {
        private static final DatabaseConnection INSTANCE = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return Holder.INSTANCE;
    }

    public Connection getConnection() throws SQLException {
        String url = firstNonBlank("jdbc.url", "db.url");
        String user = firstNonBlank("jdbc.username", "db.user");
        String pass = firstNonBlank("jdbc.password", "db.password");
        if (url == null || url.isEmpty()) {
            throw new SQLException("jdbc.url is not configured in db.properties");
        }
        return DriverManager.getConnection(url, user, pass);
    }

    private String firstNonBlank(String... keys) {
        for (String key : keys) {
            String value = props.getProperty(key);
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    public Properties getProperties() {
        return props;
    }
}

