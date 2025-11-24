package com.quasetudogostoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quasetudogostoso.config.DAO;
import com.quasetudogostoso.model.Ingredient;
import com.quasetudogostoso.model.Recipe;
import com.quasetudogostoso.model.RecipeIngredient;

/**
 * Repositório responsável pelas operações de persistência de ingredientes de
 * receitas. Implementa o padrão DAO (Data Access Object) para acesso ao banco
 * de dados.
 */
public class RecipeIngredientRepository extends DAO {

    /**
     * Insere um novo ingrediente de receita no banco de dados.
     *
     * @param recipeIngredient O ingrediente de receita a ser inserido
     * @throws RuntimeException se ocorrer erro na inserção
     */
    public void insert(RecipeIngredient recipeIngredient) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO ingrediente_receita (ingrediente_idingrediente, receita_idreceita, quantidade, dosagem_iddosagem) VALUES (?, ?, ?, ?);"
            );
            stmt.setInt(1, recipeIngredient.getIngredient().getId());
            stmt.setInt(2, recipeIngredient.getRecipe().getId());
            stmt.setFloat(3, recipeIngredient.getQuantity());
            stmt.setInt(4, recipeIngredient.getIdMeasurement());
            stmt.executeUpdate();

            System.out.println("Ingrediente adicionado à receita com sucesso!");

            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir ingrediente na receita: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna todos os ingredientes de uma receita específica.
     *
     * @param recipeId O ID da receita
     * @return Lista de ingredientes da receita
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public List<RecipeIngredient> selectByRecipeId(int recipeId) {
        List<RecipeIngredient> recipeIngredients = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT ir.*, i.ingrediente, r.titulo "
                    + "FROM ingrediente_receita ir "
                    + "INNER JOIN ingrediente i ON ir.ingrediente_idingrediente = i.idingrediente "
                    + "INNER JOIN receita r ON ir.receita_idreceita = r.idreceita "
                    + "WHERE ir.receita_idreceita = ?;"
            );
            stmt.setInt(1, recipeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RecipeIngredient recipeIngredient = new RecipeIngredient();

                // Criar objeto Recipe simplificado
                Recipe recipe = new Recipe();
                recipe.setId(recipeId);
                recipe.setTitle(rs.getString("titulo"));

                // Criar objeto Ingredient simplificado
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("ingrediente_idingrediente"));
                ingredient.setIngredient(rs.getString("ingrediente"));

                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setQuantity(rs.getFloat("quantidade"));
                recipeIngredient.setIdMeasurement(rs.getInt("dosagem_iddosagem"));

                recipeIngredients.add(recipeIngredient);
            }

            closeConnection();

            return recipeIngredients;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar ingredientes da receita: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza a quantidade de um ingrediente na receita.
     *
     * @param recipeIngredient O ingrediente da receita com dados atualizados
     * @throws RuntimeException se ocorrer erro na atualização
     */
    public void update(RecipeIngredient recipeIngredient) {
        try {
            final Connection conn = DAO.createConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ingrediente_receita SET quantidade = ?, dosagem_iddosagem = ? "
                    + "WHERE ingrediente_idingrediente = ? AND receita_idreceita = ?;"
            );
            stmt.setFloat(1, recipeIngredient.getQuantity());
            stmt.setInt(2, recipeIngredient.getIdMeasurement());
            stmt.setInt(3, recipeIngredient.getIngredient().getId());
            stmt.setInt(4, recipeIngredient.getRecipe().getId());
            stmt.executeUpdate();
            System.out.println("Ingrediente da receita atualizado com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar ingrediente da receita: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um ingrediente específico de uma receita.
     *
     * @param recipeId O ID da receita
     * @param ingredientId O ID do ingrediente
     * @throws RuntimeException se ocorrer erro na remoção
     */
    public void delete(int recipeId, int ingredientId) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ingrediente_receita WHERE receita_idreceita = ? AND ingrediente_idingrediente = ?;"
            );
            stmt.setInt(1, recipeId);
            stmt.setInt(2, ingredientId);
            stmt.executeUpdate();
            System.out.println("Ingrediente removido da receita com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover ingrediente da receita: " + e.getMessage(), e);
        }
    }

    /**
     * Remove todos os ingredientes de uma receita.
     *
     * @param recipeId O ID da receita
     * @throws RuntimeException se ocorrer erro na remoção
     */
    public void deleteAllByRecipeId(int recipeId) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ingrediente_receita WHERE receita_idreceita = ?;"
            );
            stmt.setInt(1, recipeId);
            stmt.executeUpdate();
            System.out.println("Todos os ingredientes da receita removidos com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao remover ingredientes da receita: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se um ingrediente já está associado a uma receita.
     *
     * @param recipeId O ID da receita
     * @param ingredientId O ID do ingrediente
     * @return true se o ingrediente já está na receita, false caso contrário
     * @throws RuntimeException se ocorrer erro na verificação
     */
    public boolean exists(int recipeId, int ingredientId) {
        boolean exists = false;
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ingrediente_receita WHERE receita_idreceita = ? AND ingrediente_idingrediente = ?"
            );
            stmt.setInt(1, recipeId);
            stmt.setInt(2, ingredientId);
            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                exists = true;
            }

            closeConnection();

            return exists;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar ingrediente na receita: " + e.getMessage(), e);
        }
    }
}
