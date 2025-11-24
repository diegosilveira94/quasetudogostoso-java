package com.quasetudogostoso.service;

import java.util.List;

import com.quasetudogostoso.model.Ingredient;
import com.quasetudogostoso.repository.IngredientRepository;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada aos
 * ingredientes. Implementa validações e regras de negócio antes de persistir os
 * dados.
 */
public class IngredientService {

    private final IngredientRepository ingredientRepository = new IngredientRepository();

    /**
     * Registra um novo ingrediente no sistema. Valida os dados do ingrediente
     * antes de persistir no banco de dados.
     *
     * @param ingredient O ingrediente a ser registrado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se o ingrediente já estiver registrado
     */
    public void registerIngredient(Ingredient ingredient) {
        if (ingredient.getIngredient() == null || ingredient.getIngredient().isBlank()) {
            throw new IllegalArgumentException("Nome do ingrediente é obrigatório!");
        }
        if (ingredient.getIngredient().length() < 2) {
            throw new IllegalArgumentException("Nome do ingrediente deve ter no mínimo 2 caracteres!");
        }
        if (ingredient.getIngredient().length() > 90) {
            throw new IllegalArgumentException("Nome do ingrediente deve ter no máximo 90 caracteres!");
        }
        if (ingredientRepository.existsByName(ingredient.getIngredient())) {
            throw new IllegalStateException("Ingrediente já registrado!");
        }

        ingredientRepository.insert(ingredient);
    }

    /**
     * Retorna todos os ingredientes cadastrados no sistema.
     *
     * @return Lista contendo todos os ingredientes
     */
    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.selectAll();

        return ingredients;
    }

    /**
     * Busca um ingrediente específico pelo ID.
     *
     * @param id O ID do ingrediente a ser buscado
     * @return O ingrediente encontrado ou um objeto vazio se não existir
     */
    public Ingredient getIngredient(int id) {
        Ingredient ingredient = ingredientRepository.select(id);

        return ingredient;
    }

    /**
     * Atualiza os dados de um ingrediente existente.
     *
     * @param ingredient O ingrediente com os dados atualizados
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void updateIngredient(Ingredient ingredient) {
        if (ingredient.getIngredient() == null || ingredient.getIngredient().isBlank()) {
            throw new IllegalArgumentException("Nome do ingrediente é obrigatório!");
        }
        if (ingredient.getIngredient().length() < 2) {
            throw new IllegalArgumentException("Nome do ingrediente deve ter no mínimo 2 caracteres!");
        }
        if (ingredient.getIngredient().length() > 90) {
            throw new IllegalArgumentException("Nome do ingrediente deve ter no máximo 90 caracteres!");
        }

        ingredientRepository.update(ingredient);
    }

    /**
     * Remove um ingrediente do sistema.
     *
     * @param id O ID do ingrediente a ser removido
     */
    public void deleteIngredient(int id) {
        ingredientRepository.delete(id);
    }

    /**
     * Busca ingredientes por nome parcial.
     *
     * @param searchTerm O termo de busca
     * @return Lista de ingredientes encontrados
     */
    public List<Ingredient> searchIngredients(String searchTerm) {
        if (searchTerm == null || searchTerm.isBlank()) {
            throw new IllegalArgumentException("Termo de busca é obrigatório!");
        }
        return ingredientRepository.searchByName(searchTerm);
    }
}
