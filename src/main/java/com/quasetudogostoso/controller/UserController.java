package com.quasetudogostoso.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.quasetudogostoso.model.User;
import com.quasetudogostoso.service.UserService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UserController implements HttpHandler {

    private static final UserService service = new UserService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();

        if ("GET".equalsIgnoreCase(method)) {
            handleGet(exchange);
        } else if ("POST".equalsIgnoreCase(method)) {
            handlePost(exchange);
        } else {
            String resp = "Método não suportado";
            byte[] bytes = resp.getBytes("UTF_8");
            exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
            exchange.sendResponseHeaders(405, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    public void handleGet(HttpExchange exchange) throws IOException {
        List<User> users = service.getAllUsers();
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            json.append(String.format(
                    "{\"id\":%s,\"nome\":\"%s\",\"email\":\"%s\",\"dataNasc\":\"%s\",\"cep\":\"%d\",\"genero\":\"%s\",\"senha\":\"%s\"}",
                    user.getId(), user.getName(), user.getEmail(), user.getBirthDate(), user.getCep(), user.getGender(), user.getPassword()
            ));
            if (i < users.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        byte[] bytes = json.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

    public void handlePost(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        String name = body.replaceAll(".*\"nome\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String email = body.replaceAll(".*\"email\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        String birthDate = body.replaceAll(".*\"dataNasc\"\\s*:\\s*\"([^\"]+)\".*", "$1");
        int cep = Integer.parseInt(body.replaceAll(".*\"cep\"\\s*:\\s*\"([^\"]+)\".*", "$1"));
        int gender = Integer.parseInt(body.replaceAll(".*\"genero\"\\s*:\\s*\"([^\"]+)\".*", "$1"));
        String password = body.replaceAll(".*\"senha\"\\s*:\\s*\"([^\"]+)\".*", "$1");

        User user = new User(name, email, birthDate, cep, gender, password);
        service.registerUser(user);

        String resp = String.format("{\"status\":\"Usuário criado!\",\"id\":%d}", user.getId());
        byte[] bytes = resp.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }

}
