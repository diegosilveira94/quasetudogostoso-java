package com.quasetudogostoso.service;

import java.util.List;

import com.quasetudogostoso.model.Recipe;
import com.quasetudogostoso.repository.RecipeRepository;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada às receitas.
 * Implementa validações e regras de negócio antes de persistir os dados.
 */
public class RecipeService {

    private final RecipeRepository recipeRepository = new RecipeRepository();

    /**
     * Cria uma nova receita. Valida se o autor está presente antes de inserir
     * no banco.
     *
     * @param recipe A receita a ser criada
     * @throws IllegalArgumentException se a receita não tiver um autor
     * vinculado
     */
    public void registerRecipe(Recipe recipe) {
        // Validação: garante que a receita tem um autor
        if (recipe.getAuthor() == null) {
            throw new IllegalArgumentException("A receita deve ter um autor vinculado.");
        }

        // Validação: garante que o autor tem um ID válido
        if (recipe.getAuthor().getId() <= 0) {
            throw new IllegalArgumentException("O autor da receita deve ter um ID válido.");
        }

        // Validação: título obrigatório
        if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            throw new IllegalArgumentException("Título da receita é obrigatório!");
        }

        // Validação: tamanho do título
        if (recipe.getTitle().length() < 3) {
            throw new IllegalArgumentException("Título da receita deve ter no mínimo 3 caracteres!");
        }

        if (recipe.getTitle().length() > 100) {
            throw new IllegalArgumentException("Título da receita deve ter no máximo 100 caracteres!");
        }

        recipeRepository.insert(recipe);
    }

    /**
     * Retorna todas as receitas cadastradas no sistema.
     *
     * @return Lista contendo todas as receitas
     */
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = recipeRepository.selectAll();

        return recipes;
    }

    /**
     * Busca uma receita específica pelo ID.
     *
     * @param id O ID da receita a ser buscada
     * @return A receita encontrada ou um objeto vazio se não existir
     */
    public Recipe getRecipe(int id) {
        Recipe recipe = recipeRepository.select(id);

        return recipe;
    }

    /**
     * Atualiza os dados de uma receita existente.
     *
     * @param recipe A receita com os dados atualizados
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void updateRecipe(Recipe recipe) {
        // Validação: título obrigatório
        if (recipe.getTitle() == null || recipe.getTitle().isBlank()) {
            throw new IllegalArgumentException("Título da receita é obrigatório!");
        }

        // Validação: tamanho do título
        if (recipe.getTitle().length() < 3) {
            throw new IllegalArgumentException("Título da receita deve ter no mínimo 3 caracteres!");
        }

        if (recipe.getTitle().length() > 100) {
            throw new IllegalArgumentException("Título da receita deve ter no máximo 100 caracteres!");
        }

        recipeRepository.update(recipe);
    }

    /**
     * Remove uma receita do sistema.
     *
     * @param id O ID da receita a ser removida
     */
    public void deleteRecipe(int id) {
        recipeRepository.delete(id);
    }
}
