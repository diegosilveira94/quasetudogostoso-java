package com.quasetudogostoso.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.quasetudogostoso.model.User;
import com.quasetudogostoso.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public void registerUser(User user) {

        // Some validations (missing others)
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
        userRepository.registerUser(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.getAllUsers();

        return users;
    }

    public User getUser(Integer id, String email) {
        // Option uses just id or email to get user
        if (id == null) {
            id = 0;
        }
        if (email == null || email.isBlank()) {
            email = "";
        }
        User user = userRepository.getUser(id, email);

        return user;
    }

    // Birth data formatted to SQL standard YYYY-MM-DD
    public String formatBirthDateSQL(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate formattedDate = LocalDate.parse(birthDate, formatter);
        String dateString = formattedDate.toString();

        return dateString;
    }
}
