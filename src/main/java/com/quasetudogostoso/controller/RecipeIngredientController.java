package com.quasetudogostoso.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.quasetudogostoso.model.RecipeIngredient;
import com.quasetudogostoso.service.RecipeIngredientService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * Controlador REST responsável por gerenciar as requisições HTTP relacionadas
 * aos ingredientes de receitas. Implementa os endpoints CRUD para a entidade
 * RecipeIngredient.
 */
public class RecipeIngredientController implements HttpHandler {

    private static final RecipeIngredientService service = new RecipeIngredientService();

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

        if ("GET".equalsIgnoreCase(method) && path.matches("/api/recipes/\\d+/ingredients")) {
            handleGetByRecipeId(exchange, path);
        } else if ("POST".equalsIgnoreCase(method) && path.matches("/api/recipes/\\d+/ingredients")) {
            handleCreate(exchange, path);
        } else if ("PUT".equalsIgnoreCase(method) && path.matches("/api/recipes/\\d+/ingredients/\\d+")) {
            handleUpdate(exchange, path);
        } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/api/recipes/\\d+/ingredients/\\d+")) {
            handleDelete(exchange, path);
        } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/api/recipes/\\d+/ingredients")) {
            handleDeleteAll(exchange, path);
        } else {
            notFound(exchange);
        }
    }

    private void handleGetByRecipeId(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int recipeId = Integer.parseInt(parts[3]);
        try {
            List<RecipeIngredient> ingredients = service.getRecipeIngredients(recipeId);
            String json = new Gson().toJson(ingredients);
            sendResponse(exchange, 200, json);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleCreate(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int recipeId = Integer.parseInt(parts[3]);

        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        RecipeIngredient recipeIngredient = new Gson().fromJson(body, RecipeIngredient.class);

        // Garantir que o recipeId da URL seja usado
        if (recipeIngredient.getRecipe() != null) {
            recipeIngredient.getRecipe().setId(recipeId);
        }

        try {
            service.addIngredientToRecipe(recipeIngredient);
            String resp = new Gson().toJson(recipeIngredient);
            sendResponse(exchange, 200, resp);
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleUpdate(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int recipeId = Integer.parseInt(parts[3]);
        int ingredientId = Integer.parseInt(parts[5]);

        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        RecipeIngredient recipeIngredient = new Gson().fromJson(body, RecipeIngredient.class);

        // Garantir que os IDs da URL sejam usados
        if (recipeIngredient.getRecipe() != null) {
            recipeIngredient.getRecipe().setId(recipeId);
        }
        if (recipeIngredient.getIngredient() != null) {
            recipeIngredient.getIngredient().setId(ingredientId);
        }

        try {
            service.updateRecipeIngredient(recipeIngredient);
            sendResponse(exchange, 200, "{\"status\":\"Ingrediente da receita atualizado!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleDelete(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int recipeId = Integer.parseInt(parts[3]);
        int ingredientId = Integer.parseInt(parts[5]);

        try {
            service.removeIngredientFromRecipe(recipeId, ingredientId);
            sendResponse(exchange, 200, "{\"status\":\"Ingrediente removido da receita!\"}");
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void handleDeleteAll(HttpExchange exchange, String path) throws IOException {
        String[] parts = path.split("/");
        int recipeId = Integer.parseInt(parts[3]);

        try {
            service.removeAllIngredientsFromRecipe(recipeId);
            sendResponse(exchange, 200, "{\"status\":\"Todos os ingredientes removidos da receita!\"}");
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
