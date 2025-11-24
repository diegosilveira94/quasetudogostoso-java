package com.quasetudogostoso.service;

import java.util.List;

import com.quasetudogostoso.model.Category;
import com.quasetudogostoso.repository.CategoryRepository;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada às
 * categorias. Implementa validações e regras de negócio antes de persistir os
 * dados.
 */
public class CategoryService {

    private final CategoryRepository categoryRepository = new CategoryRepository();

    /**
     * Registra uma nova categoria no sistema. Valida os dados da categoria
     * antes de persistir no banco de dados.
     *
     * @param category A categoria a ser registrada
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se a categoria já estiver registrada
     */
    public void registerCategory(Category category) {
        if (category.getCategory() == null || category.getCategory().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório!");
        }
        if (category.getCategory().length() < 3) {
            throw new IllegalArgumentException("Nome da categoria deve ter no mínimo 3 caracteres!");
        }
        if (category.getCategory().length() > 80) {
            throw new IllegalArgumentException("Nome da categoria deve ter no máximo 80 caracteres!");
        }
        if (categoryRepository.existsByName(category.getCategory())) {
            throw new IllegalStateException("Categoria já registrada!");
        }

        categoryRepository.insert(category);
    }

    /**
     * Retorna todas as categorias cadastradas no sistema.
     *
     * @return Lista contendo todas as categorias
     */
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.selectAll();

        return categories;
    }

    /**
     * Retorna apenas as categorias ativas do sistema.
     *
     * @return Lista contendo apenas categorias ativas
     */
    public List<Category> getActiveCategories() {
        List<Category> categories = categoryRepository.selectAllActive();

        return categories;
    }

    /**
     * Busca uma categoria específica pelo ID.
     *
     * @param id O ID da categoria a ser buscada
     * @return A categoria encontrada ou um objeto vazio se não existir
     */
    public Category getCategory(int id) {
        Category category = categoryRepository.select(id);

        return category;
    }

    /**
     * Atualiza os dados de uma categoria existente.
     *
     * @param category A categoria com os dados atualizados
     * @throws IllegalArgumentException se os dados forem inválidos
     */
    public void updateCategory(Category category) {
        if (category.getCategory() == null || category.getCategory().isBlank()) {
            throw new IllegalArgumentException("Nome da categoria é obrigatório!");
        }
        if (category.getCategory().length() < 3) {
            throw new IllegalArgumentException("Nome da categoria deve ter no mínimo 3 caracteres!");
        }
        if (category.getCategory().length() > 80) {
            throw new IllegalArgumentException("Nome da categoria deve ter no máximo 80 caracteres!");
        }

        categoryRepository.update(category);
    }

    /**
     * Remove uma categoria do sistema.
     *
     * @param id O ID da categoria a ser removida
     */
    public void deleteCategory(int id) {
        categoryRepository.delete(id);
    }

    /**
     * Desativa uma categoria ao invés de deletá-la.
     *
     * @param id O ID da categoria a ser desativada
     */
    public void deactivateCategory(int id) {
        Category category = categoryRepository.select(id);
        if (category.getId() == 0) {
            throw new IllegalArgumentException("Categoria não encontrada!");
        }
        category.setActive(false);
        categoryRepository.update(category);
    }

    /**
     * Ativa uma categoria previamente desativada.
     *
     * @param id O ID da categoria a ser ativada
     */
    public void activateCategory(int id) {
        Category category = categoryRepository.select(id);
        if (category.getId() == 0) {
            throw new IllegalArgumentException("Categoria não encontrada!");
        }
        category.setActive(true);
        categoryRepository.update(category);
    }
}
