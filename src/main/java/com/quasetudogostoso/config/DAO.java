package com.quasetudogostoso.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DAO {

    private static Connection conn;

    public static Connection createConnection() {
        try {
            final String URL = "jdbc:mysql://localhost:3306/quasetudogostoso";
            final String USER = "root";
            final String PASSWORD = "";

            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
