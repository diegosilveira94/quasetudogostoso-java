package com.quasetudogostoso;

import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.quasetudogostoso.controller.UserController;
import com.sun.net.httpserver.HttpServer;

public class App {

    private static final int PORT = 3030;
    public static UserController userController = new UserController();

    public static void main(String[] args) throws Exception {
        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        // Define routes
        server.createContext("/users", userController);
        // Start the server
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor iniciado em http://localhost:" + PORT);

        // testing
        final String URL = "jdbc:mysql://localhost:3306/quasetudogostoso";
        final String USER = "root";
        final String PASSWORD = "";

        // Connection conn = DAO.createConnection();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement()) {
            // Execute a simple query, e.g., to get the current date or a count from a small table
            stmt.executeQuery("SELECT * FROM usuario;");
            System.out.println("Database connection successful!");
        } catch (SQLException e) {
            System.err.println("Database query failed: " + e.getMessage());
        }
    }
}
