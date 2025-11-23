package com.quasetudogostoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quasetudogostoso.config.DAO;
import com.quasetudogostoso.model.User;

/**
 * Repositório responsável pelas operações de persistência de usuários.
 * Implementa o padrão DAO (Data Access Object) para acesso ao banco de dados.
 */
public class UserRepository extends DAO {

    /**
     * Insere um novo usuário no banco de dados.
     *
     * @param user O usuário a ser inserido
     * @throws RuntimeException se ocorrer erro na inserção
     */
    public void insert(User user) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO usuario (nome, email, data_nascimento, cep, genero, senha, salt) VALUES (?, ?, ?, ?, ?, ?, ?);"
            );
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBirthDate());
            stmt.setInt(4, user.getCep());
            int genderInt = convertGender(user.getGender());
            stmt.setInt(5, genderInt); // Converte gênero de String para int
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getSalt());
            stmt.executeUpdate();

            System.out.println("Usuário registrado com sucesso!");

            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao inserir o usuário no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Retorna todos os usuários cadastrados no banco de dados.
     *
     * @return Lista de todos os usuários
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try {
            final Connection conn = DAO.createConnection();

            ResultSet rs = conn.createStatement().executeQuery(
                    "SELECT * FROM usuario;"
            );
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("idusuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getString("data_nascimento"));
                user.setCep(rs.getInt("cep"));
                user.setGender(convertGender(rs.getInt("genero"))); // Converte gênero de int para String
                user.setRegistrationDate(rs.getString("inscrito"));
                user.setUuId(rs.getString("uuid"));
                users.add(user);
            }

            closeConnection();

            return users;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar os usuários no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Busca um usuário específico pelo ID.
     *
     * @param id O ID do usuário
     * @return O usuário encontrado ou um objeto vazio
     * @throws RuntimeException se ocorrer erro na consulta
     */
    public User select(int id) {
        User user = new User();

        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE idusuario = ?;"
            );
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("idusuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getString("data_nascimento"));
                user.setCep(rs.getInt("cep"));
                user.setGender(convertGender(rs.getInt("genero"))); // Converte gênero de int para String
                user.setRegistrationDate(rs.getString("inscrito"));
                user.setUuId(rs.getString("uuid"));
            }

            closeConnection();

            return user;
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao consultar o usuário no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Atualiza os dados de um usuário no banco de dados.
     *
     * @param user O usuário com os dados atualizados
     * @throws RuntimeException se ocorrer erro na atualização
     */
    public void update(User user) {
        try {
            final Connection conn = DAO.createConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE usuario SET nome = ?, email = ?, data_nascimento = ? WHERE idusuario = ?;"
            );
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBirthDate());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar o usuário no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Remove um usuário do banco de dados.
     *
     * @param id O ID do usuário a ser removido
     * @throws RuntimeException se ocorrer erro na remoção
     */
    public void delete(int id) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM usuario WHERE idusuario = ?;"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso!");
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar o usuário no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Verifica se um email já está registrado no banco de dados. Utilizado para
     * evitar registros duplicados.
     *
     * @param email O email a ser verificado
     * @return true se o email já existe, false caso contrário
     * @throws RuntimeException se ocorrer erro na verificação
     */
    public boolean existsByEmail(String email) {
        boolean emailExists = false;
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT email FROM usuario WHERE email = ?"
            );
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                emailExists = true;
            }

            closeConnection();

            return emailExists;

        } catch (SQLException e) {
            System.err.println("Message: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Erro ao verificar email no banco de dados: " + e.getMessage(), e);
        }
    }

    /**
     * Converte o gênero do usuário de inteiro para string. Utiliza sobrecarga
     * de método (overloading).
     *
     * @param genderInt O gênero em formato inteiro (1=Masculino, 2=Feminino,
     * 3=Outro)
     * @return O gênero em formato string
     */
    public String convertGender(int genderInt) {
        String genderStr;
        switch (genderInt) {
            case 1 ->
                genderStr = "Masculino";
            case 2 ->
                genderStr = "Feminino";
            default ->
                genderStr = "Outro";
        }

        return genderStr;
    }

    /**
     * Converte o gênero do usuário de string para inteiro. Utiliza sobrecarga
     * de método (overloading).
     *
     * @param genderStr O gênero em formato string ("Masculino", "Feminino" ou
     * "Outro")
     * @return O gênero em formato inteiro (1=Masculino, 2=Feminino, 3=Outro)
     */
    public int convertGender(String genderStr) {
        int genderInt;
        switch (genderStr) {
            case "Masculino" ->
                genderInt = 1;
            case "Feminino" ->
                genderInt = 2;
            default ->
                genderInt = 3;
        }

        return genderInt;
    }
}
