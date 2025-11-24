package com.quasetudogostoso.service;

import java.util.List;

import com.quasetudogostoso.model.RecipeIngredient;
import com.quasetudogostoso.repository.RecipeIngredientRepository;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada aos
 * ingredientes de receitas. Implementa validações e regras de negócio antes de
 * persistir os dados.
 */
public class RecipeIngredientService {

    private final RecipeIngredientRepository recipeIngredientRepository = new RecipeIngredientRepository();

    /**
     * Adiciona um ingrediente a uma receita. Valida os dados antes de persistir
     * no banco de dados.
     *
     * @param recipeIngredient O ingrediente da receita a ser adicionado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se o ingrediente já estiver na receita
     */
    public void addIngredientToRecipe(RecipeIngredient recipeIngredient) {
        if (recipeIngredient.getRecipe() == null || recipeIngredient.getRecipe().getId() <= 0) {
            throw new IllegalArgumentException("Receita inválida!");
        }
        if (recipeIngredient.getIngredient() == null || recipeIngredient.getIngredient().getId() <= 0) {
            throw new IllegalArgumentException("Ingrediente inválido!");
        }
        if (recipeIngredient.getQuantity() == null || recipeIngredient.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero!");
        }
        if (recipeIngredientRepository.exists(
                recipeIngredient.getRecipe().getId(),
                recipeIngredient.getIngredient().getId())) {
            throw new IllegalStateException("Este ingrediente já está adicionado à receita!");
        }

        recipeIngredientRepository.insert(recipeIngredient);
    }

    /**
     * Retorna todos os ingredientes de uma receita específica.
     *
     * @param recipeId O ID da receita
     * @return Lista de ingredientes da receita
     * @throws IllegalArgumentException se o ID da receita for inválido
     */
    public List<RecipeIngredient> getRecipeIngredients(int recipeId) {
        if (recipeId <= 0) {
            throw new IllegalArgumentException("ID da receita inválido!");
        }
        return recipeIngredientRepository.selectByRecipeId(recipeId);
    }

    /**
     * Atualiza a quantidade de um ingrediente na receita.
     *
     * @param recipeIngredient O ingrediente da receita com dados atualizados
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void updateRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (recipeIngredient.getRecipe() == null || recipeIngredient.getRecipe().getId() <= 0) {
            throw new IllegalArgumentException("Receita inválida!");
        }
        if (recipeIngredient.getIngredient() == null || recipeIngredient.getIngredient().getId() <= 0) {
            throw new IllegalArgumentException("Ingrediente inválido!");
        }
        if (recipeIngredient.getQuantity() == null || recipeIngredient.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero!");
        }

        recipeIngredientRepository.update(recipeIngredient);
    }

    /**
     * Remove um ingrediente específico de uma receita.
     *
     * @param recipeId O ID da receita
     * @param ingredientId O ID do ingrediente
     * @throws IllegalArgumentException se os IDs forem inválidos
     */
    public void removeIngredientFromRecipe(int recipeId, int ingredientId) {
        if (recipeId <= 0 || ingredientId <= 0) {
            throw new IllegalArgumentException("IDs inválidos!");
        }
        recipeIngredientRepository.delete(recipeId, ingredientId);
    }

    /**
     * Remove todos os ingredientes de uma receita.
     *
     * @param recipeId O ID da receita
     * @throws IllegalArgumentException se o ID da receita for inválido
     */
    public void removeAllIngredientsFromRecipe(int recipeId) {
        if (recipeId <= 0) {
            throw new IllegalArgumentException("ID da receita inválido!");
        }
        recipeIngredientRepository.deleteAllByRecipeId(recipeId);
    }
}
