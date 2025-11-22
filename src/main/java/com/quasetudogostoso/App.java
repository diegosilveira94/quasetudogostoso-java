package com.quasetudogostoso;

import java.net.InetSocketAddress;

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
    }
}
