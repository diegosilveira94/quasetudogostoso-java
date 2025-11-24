
package com.quasetudogostoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quasetudogostoso.config.DAO;
import com.quasetudogostoso.model.Ingredient;

/**
 * Repositório responsável pelas operações de persistência de ingredientes.
 * Implementa o padrão DAO (Data Access Object) para acesso ao banco de dados.
 */

public class IngredientRepository extends DAO {

    /**
     * Insere um novo ingrediente no banco de dados.
     *
     * @param ingredient O ingrediente a ser inserido
     * @throws RuntimeException se ocorrer erro na inserção
     */
    public void insert(Ingredient ingredient) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO ingrediente (ingrediente) VALUES (?);"
            );
            stmt.setString(1, ingredient.getIngredient());
            stmt.executeUpdate();

            System.out.println("Ingrediente registrado com sucesso!");

            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir o ingrediente no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna todos os ingredientes cadastrados no banco de dados.
     *
     * @return Lista de todos os ingredientes
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public List<Ingredient> selectAll() {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT * FROM ingrediente ORDER BY ingrediente ASC;"
            );
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("idingrediente"));
                ingredient.setIngredient(rs.getString("ingrediente"));
                ingredients.add(ingredient);
            }

            closeConnection();

            return ingredients;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar os ingredientes no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um ingrediente específico pelo ID.
     *
     * @param id O ID do ingrediente
     * @return O ingrediente encontrado ou um objeto vazio
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public Ingredient select(int id) {
        Ingredient ingredient = new Ingredient();

        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ingrediente WHERE idingrediente = ?;"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                ingredient.setId(rs.getInt("idingrediente"));
                ingredient.setIngredient(rs.getString("ingrediente"));
            }

            closeConnection();

            return ingredient;
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar o ingrediente no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um ingrediente no banco de dados.
     *
     * @param ingredient O ingrediente com os dados atualizados
     * @throws RuntimeException se ocorrer erro na atualização
     */
    public void update(Ingredient ingredient) {
        try {
            final Connection conn = DAO.createConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE ingrediente SET ingrediente = ? WHERE idingrediente = ?;"
            );
            stmt.setString(1, ingredient.getIngredient());
            stmt.setInt(2, ingredient.getId());
            stmt.executeUpdate();
            System.out.println("Ingrediente atualizado com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o ingrediente no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um ingrediente do banco de dados.
     *
     * @param id O ID do ingrediente a ser removido
     * @throws RuntimeException se ocorrer erro na remoção
     */
    public void delete(int id) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM ingrediente WHERE idingrediente = ?;"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Ingrediente deletado com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar o ingrediente no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se um ingrediente já existe no banco de dados pelo nome.
     * Utilizado para evitar registros duplicados.
     *
     * @param ingredientName O nome do ingrediente a ser verificado
     * @return true se o ingrediente já existe, false caso contrário
     * @throws RuntimeException se ocorrer erro na verificação
     */
    public boolean existsByName(String ingredientName) {
        boolean ingredientExists = false;
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT ingrediente FROM ingrediente WHERE ingrediente = ?"
            );
            stmt.setString(1, ingredientName);
            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                ingredientExists = true;
            }

            closeConnection();

            return ingredientExists;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar ingrediente no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Busca ingredientes por nome parcial (útil para autocomplete).
     *
     * @param searchTerm O termo de busca
     * @return Lista de ingredientes que correspondem ao termo
     * @throws RuntimeException se ocorrer erro na busca
     */
    public List<Ingredient> searchByName(String searchTerm) {
        List<Ingredient> ingredients = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM ingrediente WHERE ingrediente LIKE ? ORDER BY ingrediente ASC;"
            );
            stmt.setString(1, "%" + searchTerm + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("idingrediente"));
                ingredient.setIngredient(rs.getString("ingrediente"));
                ingredients.add(ingredient);
            }

            closeConnection();

            return ingredients;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar ingredientes no banco de dados: " + e.getMessage(), e);
        }
    }
}
