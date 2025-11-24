
package com.quasetudogostoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quasetudogostoso.config.DAO;
import com.quasetudogostoso.model.Category;

/**
 * Repositório responsável pelas operações de persistência de categorias.
 * Implementa o padrão DAO (Data Access Object) para acesso ao banco de dados.
 */

public class CategoryRepository extends DAO {

    /**
     * Insere uma nova categoria no banco de dados.
     *
     * @param category A categoria a ser inserida
     * @throws RuntimeException se ocorrer erro na inserção
     */
    public void insert(Category category) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO categoria (categoria, ativo) VALUES (?, ?);"
            );
            stmt.setString(1, category.getCategory());
            stmt.setBoolean(2, category.isActive());
            stmt.executeUpdate();

            System.out.println("Categoria registrada com sucesso!");

            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir a categoria no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna todas as categorias cadastradas no banco de dados.
     *
     * @return Lista de todas as categorias
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public List<Category> selectAll() {
        List<Category> categories = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT * FROM categoria;"
            );
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("idcategoria"));
                category.setCategory(rs.getString("categoria"));
                category.setActive(rs.getBoolean("ativo"));
                categories.add(category);
            }

            closeConnection();

            return categories;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar as categorias no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Busca uma categoria específica pelo ID.
     *
     * @param id O ID da categoria
     * @return A categoria encontrada ou um objeto vazio
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public Category select(int id) {
        Category category = new Category();

        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM categoria WHERE idcategoria = ?;"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category.setId(rs.getInt("idcategoria"));
                category.setCategory(rs.getString("categoria"));
                category.setActive(rs.getBoolean("ativo"));
            }

            closeConnection();

            return category;
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar a categoria no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de uma categoria no banco de dados.
     *
     * @param category A categoria com os dados atualizados
     * @throws RuntimeException se ocorrer erro na atualização
     */
    public void update(Category category) {
        try {
            final Connection conn = DAO.createConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE categoria SET categoria = ?, ativo = ? WHERE idcategoria = ?;"
            );
            stmt.setString(1, category.getCategory());
            stmt.setBoolean(2, category.isActive());
            stmt.setInt(3, category.getId());
            stmt.executeUpdate();
            System.out.println("Categoria atualizada com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar a categoria no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Remove uma categoria do banco de dados.
     *
     * @param id O ID da categoria a ser removida
     * @throws RuntimeException se ocorrer erro na remoção
     */
    public void delete(int id) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM categoria WHERE idcategoria = ?;"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Categoria deletada com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar a categoria no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se uma categoria já existe no banco de dados pelo nome.
     * Utilizado para evitar registros duplicados.
     *
     * @param categoryName O nome da categoria a ser verificada
     * @return true se a categoria já existe, false caso contrário
     * @throws RuntimeException se ocorrer erro na verificação
     */
    public boolean existsByName(String categoryName) {
        boolean categoryExists = false;
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT categoria FROM categoria WHERE categoria = ?"
            );
            stmt.setString(1, categoryName);
            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                categoryExists = true;
            }

            closeConnection();

            return categoryExists;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar categoria no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna todas as categorias ativas.
     *
     * @return Lista de categorias ativas
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public List<Category> selectAllActive() {
        List<Category> categories = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM categoria WHERE ativo = true;"
            );
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("idcategoria"));
                category.setCategory(rs.getString("categoria"));
                category.setActive(rs.getBoolean("ativo"));
                categories.add(category);
            }

            closeConnection();

            return categories;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar categorias ativas no banco de dados: " + e.getMessage(), e);
        }
    }
}
