package com.quasetudogostoso.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.quasetudogostoso.model.User;
import com.quasetudogostoso.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Controlador REST responsável por gerenciar as requisições HTTP relacionadas
 * aos usuários. Implementa os endpoints CRUD para a entidade User.
 */
public class UserController implements HttpHandler {

    private static final UserService service = new UserService();

    /**
     * Processa as requisições HTTP recebidas e direciona para o handler
     * apropriado.
     *
     * @param exchange O objeto contendo a requisição e resposta HTTP
     * @throws IOException se ocorrer erro de I/O
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();

        if ("GET".equalsIgnoreCase(method)) {
            if (path.matches("/api/users")) {
                handleGetAll(exchange);
            } else if (path.matches("/api/users/\\d+")) {
                handleGetById(exchange, path);
            } else {
                notFound(exchange);
            }
        } else if ("POST".equalsIgnoreCase(method) && path.equals("/api/users")) {
            handleCreate(exchange);
        } else if ("PUT".equalsIgnoreCase(method) && path.matches("/api/users/\\d+")) {
            handleUpdate(exchange, path);
        } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/api/users/\\d+")) {
            handleDelete(exchange, path);
        } else {
            notFound(exchange);
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<User> users = service.getAllUsers();
        String json = new Gson().toJson(users);
        sendResponse(exchange, 200, json);
    }

    private void handleGetById(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        User user = service.getUser(id);
        if (user.getId() == 0) {
            sendResponse(exchange, 404, "{\"error\":\"Usuário não encontrado\"}");
        } else {
            String json = new Gson().toJson(user);
            sendResponse(exchange, 200, json);
        }
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        User user = new Gson().fromJson(body, User.class);
        try {
            service.registerUser(user);
            String resp = new Gson().toJson(user);
            sendResponse(exchange, 200, resp);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleUpdate(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        User user = new Gson().fromJson(body, User.class);
        user.setId(id);
        try {
            service.updateUser(user);
            sendResponse(exchange, 200, "{\"status\":\"Usuário atualizado!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        try {
            service.deleteUser(id);
            sendResponse(exchange, 200, "{\"status\":\"Usuário deletado!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void notFound(HttpExchange exchange) throws IOException {
        String resp = "Recurso não encontrado";
        byte[] bytes = resp.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(404, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }

}
