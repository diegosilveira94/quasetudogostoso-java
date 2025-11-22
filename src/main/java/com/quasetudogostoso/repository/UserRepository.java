package com.quasetudogostoso.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.quasetudogostoso.config.DAO;
import com.quasetudogostoso.model.User;

public class UserRepository extends DAO {

    public void registerUser(User user) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO usuario (nome, email, data_nascimento, cep, genero, senha) VALUES (?, ?, ?, ?, ?, ?);"
            );
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBirthDate());
            stmt.setInt(4, user.getCep());
            stmt.setInt(5, user.getGender());
            stmt.setString(6, user.getPassword());
            stmt.executeUpdate();

            System.out.println("Usuário registrado com sucesso!");

            closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir o usuário no banco de dados", e);
        }
    }

    public List<User> getAllUsers() {
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
                user.setGender(rs.getInt("genero"));
                user.setPassword(rs.getString("senha"));
                user.setSalt(rs.getString("salt"));
                user.setRegistrationDate(rs.getString("inscrito"));
                user.setUuId(rs.getString("uuid"));
                users.add(user);
            }

            closeConnection();

            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar usuários no banco de dados", e);
        }
    }

    public User getUser(int id, String email) {
        // GET USER BY ID OR EMAIL
        User user = new User();

        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM usuario WHERE idusuario = ? OR email = ?;"
            );
            stmt.setInt(1, id);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user.setId(rs.getInt("idusuario"));
                user.setName(rs.getString("nome"));
                user.setEmail(rs.getString("email"));
                user.setBirthDate(rs.getString("data_nascimento"));
                user.setCep(rs.getInt("cep"));
                user.setGender(rs.getInt("genero"));
                user.setPassword(rs.getString("senha"));
                user.setSalt(rs.getString("salt"));
                user.setRegistrationDate(rs.getString("inscrito"));
                user.setUuId(rs.getString("uuid"));
            }

            closeConnection();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao consultar usuário no banco de dados", e);
        }
    }

    public void updateUser(User user) {
        try {
            final Connection conn = DAO.createConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE usuario SET nome = ? SET email = ? SET data_nascimento = ? WHERE id = ?;"
            );
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getBirthDate());
            stmt.setInt(4, user.getId());
            stmt.executeUpdate();
            System.out.println("Usuário atualizado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário no banco de dados", e);
        }

        closeConnection();
    }

    public void deleteUser(User user) {
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "DELETE FROM usuario WHERE id = ?;"
            );
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
            System.out.println("Usuário deletado com sucesso!");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar usuário no banco de dados", e);
        }

        closeConnection();
    }

    public boolean existsByEmail(String email) {
        boolean emailExists = false;
        try {
            final Connection conn = DAO.createConnection();

            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT email FROM usuario WHERE email = ?"
            );
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs != null) {
                emailExists = true;
            }

            closeConnection();

            return emailExists;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar email no banco de dados", e);
        }
    }
}
