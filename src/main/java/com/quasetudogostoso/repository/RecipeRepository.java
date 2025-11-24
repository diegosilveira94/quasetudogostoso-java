package com.quasetudogostoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quasetudogostoso.config.DAO;
import com.quasetudogostoso.model.Recipe;
import com.quasetudogostoso.model.User;

/**
 * Repositório responsável pelas operações de persistência de receitas.
 * Implementa o padrão DAO (Data Access Object) para acesso ao banco de dados.
 */
public class RecipeRepository extends DAO {

    /**
     * Insere uma nova receita no banco de dados.
     *
     * @param recipe A receita a ser inserida
     * @throws RuntimeException se ocorrer erro na inserção
     */
    public void insert(Recipe recipe) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO receita (titulo, descricao, imagem, cadastro_idusuario, preparo_idpreparo, dificuldade_iddificuldade, custo_idcusto) VALUES (?, ?, ?, ?, ?, ?, ?);"
            );
            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getDescription());
            stmt.setString(3, recipe.getImageURL());
            stmt.setInt(4, recipe.getAuthor().getId());
            stmt.setInt(5, recipe.getIdPreparation());
            stmt.setInt(6, recipe.getIdDifficulty());
            stmt.setInt(7, recipe.getIdCost());
            stmt.executeUpdate();

            System.out.println("Receita registrada com sucesso!");

            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir a receita no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna todas as receitas cadastradas no banco de dados.
     *
     * @return Lista de todas as receitas
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public List<Recipe> selectAll() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT r.*, u.nome as autor_nome FROM receita r "
                    + "INNER JOIN usuario u ON r.cadastro_idusuario = u.idusuario;"
            );
            while (rs.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(rs.getInt("idreceita"));
                recipe.setTitle(rs.getString("titulo"));
                recipe.setDescription(rs.getString("descricao"));
                recipe.setImageURL(rs.getString("imagem"));

                // Criar objeto User simplificado
                User author = new User();
                author.setId(rs.getInt("cadastro_idusuario"));
                author.setName(rs.getString("autor_nome"));
                recipe.setAuthor(author);

                recipe.setIdPreparation(rs.getInt("preparo_idpreparo"));
                recipe.setIdDifficulty(rs.getInt("dificuldade_iddificuldade"));
                recipe.setIdCost(rs.getInt("custo_idcusto"));
                recipes.add(recipe);
            }

            closeConnection();

            return recipes;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar as receitas no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Busca uma receita específica pelo ID.
     *
     * @param id O ID da receita
     * @return A receita encontrada ou um objeto vazio
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public Recipe select(int id) {
        Recipe recipe = new Recipe();

        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT r.*, u.nome as autor_nome, u.email as autor_email FROM receita r "
                    + "INNER JOIN usuario u ON r.cadastro_idusuario = u.idusuario "
                    + "WHERE r.idreceita = ?;"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                recipe.setId(rs.getInt("idreceita"));
                recipe.setTitle(rs.getString("titulo"));
                recipe.setDescription(rs.getString("descricao"));
                recipe.setImageURL(rs.getString("imagem"));

                // Criar objeto User simplificado
                User author = new User();
                author.setId(rs.getInt("cadastro_idusuario"));
                author.setName(rs.getString("autor_nome"));
                author.setEmail(rs.getString("autor_email"));
                recipe.setAuthor(author);

                recipe.setIdPreparation(rs.getInt("preparo_idpreparo"));
                recipe.setIdDifficulty(rs.getInt("dificuldade_iddificuldade"));
                recipe.setIdCost(rs.getInt("custo_idcusto"));
            }

            closeConnection();

            return recipe;
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar a receita no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de uma receita no banco de dados.
     *
     * @param recipe A receita com os dados atualizados
     * @throws RuntimeException se ocorrer erro na atualização
     */
    public void update(Recipe recipe) {
        try {
            final Connection conn = DAO.createConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE receita SET titulo = ?, descricao = ?, imagem = ?, preparo_idpreparo = ?, dificuldade_iddificuldade = ?, custo_idcusto = ? WHERE idreceita = ?;"
            );
            stmt.setString(1, recipe.getTitle());
            stmt.setString(2, recipe.getDescription());
            stmt.setString(3, recipe.getImageURL());
            stmt.setInt(4, recipe.getIdPreparation());
            stmt.setInt(5, recipe.getIdDifficulty());
            stmt.setInt(6, recipe.getIdCost());
            stmt.setInt(7, recipe.getId());
            stmt.executeUpdate();
            System.out.println("Receita atualizada com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar a receita no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Remove uma receita do banco de dados.
     *
     * @param id O ID da receita a ser removida
     * @throws RuntimeException se ocorrer erro na remoção
     */
    public void delete(int id) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM receita WHERE idreceita = ?;"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Receita deletada com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar a receita no banco de dados: " + e.getMessage(), e);
        }
    }
}
