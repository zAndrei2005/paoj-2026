package com.pao.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.nio.charset.StandardCharsets;

public class DatabaseInitializer {
    public static void runSchema() {
        DatabaseConnection db = DatabaseConnection.getInstance();
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("schema.sql")){
            if (is == null) {
                System.err.println("schema.sql not found on classpath");
                return;
            }
            String schemaSql;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder file = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    file.append(line).append('\n');
                }
                schemaSql = file.toString();
            }

            schemaSql = schemaSql.replace("\uFEFF", "");
            schemaSql = schemaSql.replaceAll("(?m)^\s*--.*(\r?\n)?", "");

            try (Connection conn = db.getConnection(); Statement st = conn.createStatement()) {
                try {
                    st.execute("DROP ALL OBJECTS DELETE FILES");
                } catch (SQLException ignored) {
                    // database may be empty on first run
                }

                for (String statement : schemaSql.split(";")) {
                    String sql = statement.trim();
                    if (sql.isEmpty() || sql.startsWith("--")) {
                        continue;
                    }
                    try {
                        st.execute(sql);
                    } catch (SQLException e) {
                        System.err.println("Failed to execute statement: " + e.getMessage());
                        System.err.println("SQL statement: " + sql);
                    }
                }
            }
        } catch (IOException | SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}

