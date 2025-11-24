
package com.quasetudogostoso.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.quasetudogostoso.model.Category;
import com.quasetudogostoso.service.CategoryService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Controlador REST responsável por gerenciar as requisições HTTP relacionadas
 * às categorias. Implementa os endpoints CRUD para a entidade Category.
 */

public class CategoryController implements HttpHandler {

    private static final CategoryService service = new CategoryService();

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
            if (path.matches("/api/categories")) {
                handleGetAll(exchange);
            } else if (path.matches("/api/categories/active")) {
                handleGetAllActive(exchange);
            } else if (path.matches("/api/categories/\\d+")) {
                handleGetById(exchange, path);
            } else if (path.matches("/api/categories/\\d+/activate")) {
                handleActivate(exchange, path);
            } else if (path.matches("/api/categories/\\d+/deactivate")) {
                handleDeactivate(exchange, path);
            } else {
                notFound(exchange);
            }
        } else if ("POST".equalsIgnoreCase(method) && path.equals("/api/categories")) {
            handleCreate(exchange);
        } else if ("PUT".equalsIgnoreCase(method) && path.matches("/api/categories/\\d+")) {
            handleUpdate(exchange, path);
        } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/api/categories/\\d+")) {
            handleDelete(exchange, path);
        } else {
            notFound(exchange);
        }
    }

    private void handleGetAll(HttpExchange exchange) throws IOException {
        List<Category> categories = service.getAllCategories();
        String json = new Gson().toJson(categories);
        sendResponse(exchange, 200, json);
    }

    private void handleGetAllActive(HttpExchange exchange) throws IOException {
        List<Category> categories = service.getActiveCategories();
        String json = new Gson().toJson(categories);
        sendResponse(exchange, 200, json);
    }

    private void handleGetById(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        Category category = service.getCategory(id);
        if (category.getId() == 0) {
            sendResponse(exchange, 404, "{\"error\":\"Categoria não encontrada\"}");
        } else {
            String json = new Gson().toJson(category);
            sendResponse(exchange, 200, json);
        }
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Category category = new Gson().fromJson(body, Category.class);
        try {
            service.registerCategory(category);
            String resp = new Gson().toJson(category);
            sendResponse(exchange, 200, resp);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleUpdate(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        Category category = new Gson().fromJson(body, Category.class);
        category.setId(id);
        try {
            service.updateCategory(category);
            sendResponse(exchange, 200, "{\"status\":\"Categoria atualizada!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        int id = Integer.parseInt(path.substring(path.lastIndexOf('/') + 1));
        try {
            service.deleteCategory(id);
            sendResponse(exchange, 200, "{\"status\":\"Categoria deletada!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleActivate(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int id = Integer.parseInt(parts[parts.length - 2]);
        try {
            service.activateCategory(id);
            sendResponse(exchange, 200, "{\"status\":\"Categoria ativada!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleDeactivate(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int id = Integer.parseInt(parts[parts.length - 2]);
        try {
            service.deactivateCategory(id);
            sendResponse(exchange, 200, "{\"status\":\"Categoria desativada!\"}");
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
