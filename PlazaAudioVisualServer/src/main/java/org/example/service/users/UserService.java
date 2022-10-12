package org.example.service.users;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.CreateUserDTO;
import org.example.exceptions.user.NewUserWithDifferentPasswordsException;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findByUsernameIgnoreCase(String username) {
        return userRepository.findByUsernameIgnoreCase(username);
    }

    public List<User> findByUsernameContainsIgnoreCase(String username) {
        return userRepository.findByUsernameContainsIgnoreCase(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserById(String userId) {
        return userRepository.findById(userId);
    }

    public User save(CreateUserDTO newUser) {
        if (newUser.getPassword().equals(newUser.getPasswordConfirm())) {
            Set<UserRol> defaultRoles = new HashSet<>();
            defaultRoles.add(UserRol.NORMAL);
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username(newUser.getUserername())
                    .name(newUser.getName())
                    .lastName(newUser.getLastName())
                    .email(newUser.getEmail())
                    .phoneNumber(newUser.getPhoneNumber())
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .image(newUser.getImage())
                    .roles(defaultRoles)
                    .description(newUser.getDescription())
                    .build();
            try {
                return userRepository.save(user);
            } catch (DataIntegrityViolationException ex) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre del usuario ya existe");
            }
        } else {
            throw new NewUserWithDifferentPasswordsException();
        }
    }
}
