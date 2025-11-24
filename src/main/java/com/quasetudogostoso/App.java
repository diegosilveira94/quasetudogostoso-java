package com.quasetudogostoso;

import java.net.InetSocketAddress;

import com.quasetudogostoso.controller.CategoryController;
import com.quasetudogostoso.controller.IngredientController;
import com.quasetudogostoso.controller.RecipeController;
import com.quasetudogostoso.controller.RecipeIngredientController;
import com.quasetudogostoso.controller.UserController;
import com.sun.net.httpserver.HttpServer;

/**
 * Classe principal da aplicação. Responsável por inicializar o servidor HTTP e
 * configurar as rotas da API REST.
 */
public class App {

    private static final int PORT = 3030;

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

        // Define routes for Users
        server.createContext("/api/users", new UserController());

        // Define routes for Categories
        server.createContext("/api/categories", new CategoryController());

        // Define routes for Ingredients
        server.createContext("/api/ingredients", new IngredientController());

        // Define routes for Recipes
        server.createContext("/api/recipes", new RecipeController());

        // Define routes for Recipe Ingredients
        server.createContext("/api/recipes/", new RecipeIngredientController());

        // Start the server
        server.setExecutor(null);
        server.start();
        System.out.println("========================================");
        System.out.println("Servidor iniciado em http://localhost:" + PORT);
        System.out.println("========================================");
        System.out.println("Rotas disponíveis:");
        System.out.println("  - /api/users");
        System.out.println("  - /api/categories");
        System.out.println("  - /api/ingredients");
        System.out.println("  - /api/recipes");
        System.out.println("  - /api/recipes/{id}/ingredients");
        System.out.println("========================================");
    }
}
