package com.quasetudogostoso.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.quasetudogostoso.model.Ingredient;
import com.quasetudogostoso.service.IngredientService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Controlador REST responsável por gerenciar as requisições HTTP relacionadas
 * aos ingredientes. Implementa os endpoints CRUD para a entidade Ingredient.
 */
public class IngredientController implements HttpHandler {

    private static final IngredientService service = new IngredientService();

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
        String query = exchange.getRequestURI().getQuery();

        if ("GET".equalsIgnoreCase(method)) {
            if (path.matches("/api/ingredients") && query != null && query.startsWith("search=")) {
                handleSearch(exchange, query);
            } else if (path.matches("/api/ingredients")) {
                handleGetAll(exchange);
            } else if (path.matches("/api/ingredients/\\d+")) {
                handleGetById(exchange, path);
            } else {
                notFound(exchange);
            }
        } else if ("POST".equalsIgnoreCase(method) && path.equals("/api/ingredients")) {
            handleCreate(exchange);
        } else if ("PUT".equalsIgnoreCase(method) && path.matches("/api/ingredients/\\d+")) {
            handleUpdate(exchange, path);
        } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/api/ingredients/\\d+")) {
            handleDelete(exchange, path);
        } else {
            notFound(exchange);
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Ingredient> ingredients = service.getAllIngredients();
        String json = new Gson().toJson(ingredients);
        sendResponse(exchange, 200, json);
    }

    private void handleGetById(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        Ingredient ingredient = service.getIngredient(id);
        if (ingredient.getId() == 0) {
            sendResponse(exchange, 404, "{\"error\":\"Ingrediente não encontrado\"}");
        } else {
            String json = new Gson().toJson(ingredient);
            sendResponse(exchange, 200, json);
        }
    }

    private void handleSearch(HttpExchange exchange, String query) throws IOException {
        String searchTerm = query.substring(7); // Remove "search="
        try {
            List<Ingredient> ingredients = service.searchIngredients(searchTerm);
            String json = new Gson().toJson(ingredients);
            sendResponse(exchange, 200, json);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Ingredient ingredient = new Gson().fromJson(body, Ingredient.class);
        try {
            service.registerIngredient(ingredient);
            String resp = new Gson().toJson(ingredient);
            sendResponse(exchange, 200, resp);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleUpdate(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Ingredient ingredient = new Gson().fromJson(body, Ingredient.class);
        ingredient.setId(id);
        try {
            service.updateIngredient(ingredient);
            sendResponse(exchange, 200, "{\"status\":\"Ingrediente atualizado!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        try {
            service.deleteIngredient(id);
            sendResponse(exchange, 200, "{\"status\":\"Ingrediente deletado!\"}");
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
