package com.quasetudogostoso.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.quasetudogostoso.model.User;
import com.quasetudogostoso.repository.UserRepository;

/**
 * Camada de serviço responsável pela lógica de negócio relacionada aos
 * usuários. Implementa validações e regras de negócio antes de persistir os
 * dados.
 */
public class UserService {

    private final UserRepository userRepository = new UserRepository();

    /**
     * Registra um novo usuário no sistema. Valida os dados do usuário antes de
     * persistir no banco de dados.
     *
     * @param user O usuário a ser registrado
     * @throws IllegalArgumentException se os dados forem inválidos
     * @throws IllegalStateException se o email já estiver registrado
     */
    public void registerUser(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Nome é obrigatório!");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email é obrigatório!");
        }
        if (!user.getEmail().contains("@")) {
            throw new IllegalArgumentException("Email inválido!");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalStateException("Email já registrado!");
        }

        user.setBirthDate(formatBirthDateSQL(user.getBirthDate()));

        // Garante que salt e uuid tenham valores padrão se não foram definidos
        if (user.getSalt() == null || user.getSalt().isBlank()) {
            user.setSalt("XxX");
        }
        if (user.getUuId() == null || user.getUuId().isBlank()) {
            user.setUuId("zZz");
        }

        userRepository.insert(user);
    }

    /**
     * Retorna todos os usuários cadastrados no sistema.
     *
     * @return Lista contendo todos os usuários
     */
    public List<User> getAllUsers() {
        List<User> users = userRepository.selectAll();

        return users;
    }

    /**
     * Busca um usuário específico pelo ID.
     *
     * @param id O ID do usuário a ser buscado
     * @return O usuário encontrado ou um objeto vazio se não existir
     */
    public User getUser(int id) {
        User user = userRepository.select(id);

        return user;
    }

    /**
     * Atualiza os dados de um usuário existente.
     *
     * @param user O usuário com os dados atualizados
     */
    public void updateUser(User user) {
        user.setBirthDate(formatBirthDateSQL(user.getBirthDate()));
        userRepository.update(user);
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param id O ID do usuário a ser removido
     */
    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    /**
     * Formata a data de nascimento do formato brasileiro (dd/MM/yyyy) para o
     * formato aceito pelo banco de dados MySQL (yyyy-MM-dd).
     *
     * @param birthDate A data no formato dd/MM/yyyy
     * @return A data no formato yyyy-MM-dd
     */
    public String formatBirthDateSQL(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate formattedDate = LocalDate.parse(birthDate, formatter);
        String dateString = formattedDate.toString();

        return dateString;
    }
}
