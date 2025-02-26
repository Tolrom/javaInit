package com.tolrom.javaInit.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Database settings
    static final String DB_URL = "jdbc:mysql://localhost/javaDb?serverTimezone=UTC";
    static final String USERNAME = "root";
    static final String PASSWORD = "";
    // Database connection
    private static final Connection connect;
    static {
        try {
            connect = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connect;
    }
}
