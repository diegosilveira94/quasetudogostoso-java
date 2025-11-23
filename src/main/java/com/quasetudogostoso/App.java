package com.quasetudogostoso;

import java.net.InetSocketAddress;

import com.quasetudogostoso.controller.UserController;
import com.sun.net.httpserver.HttpServer;

/**
 * Classe principal da aplicação. Responsável por inicializar o servidor HTTP e
 * configurar as rotas da API REST.
 */
public class App {

    private static final int PORT = 3030;
    public static UserController userController = new UserController();

    /**
     * Método principal que inicializa o servidor HTTP. Configura o servidor
     * para escutar na porta 3030 e define as rotas disponíveis.
     *
     * @param args Argumentos de linha de comando (não utilizados)
     * @throws Exception se ocorrer erro ao iniciar o servidor
     */
    public static void main(String[] args) throws Exception {
        // Create HTTP server
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        // Define routes
        server.createContext("/api/users", userController);
        // Start the server
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor iniciado em http://localhost:" + PORT);
    }
}
