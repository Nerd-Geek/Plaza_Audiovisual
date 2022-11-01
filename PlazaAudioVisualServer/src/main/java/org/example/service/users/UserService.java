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

    public User save(CreateUserDTO newUser) {
        if (newUser.getPassword().equals(newUser.getPasswordConfirm())) {
            Set<UserRol> defaultRoles = new HashSet<>();
            defaultRoles.add(UserRol.NORMAL);
            User user = User.builder()
                    .id(UUID.randomUUID().toString())
                    .username(newUser.getUsername())
                    .name(newUser.getName())
                    .lastname(newUser.getLastName())
                    .email(newUser.getEmail())
                    .phonenumber(newUser.getPhoneNumber())
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

    public User updateUser(CreateUserDTO userModifyDTO, User user) {
        String username = userModifyDTO.getUsername();
        String name = userModifyDTO.getName();
        String lastname = userModifyDTO.getLastName();
        String email = userModifyDTO.getEmail();
        String phonenumber = userModifyDTO.getPhoneNumber();
        String image = userModifyDTO.getImage();
        String password = userModifyDTO.getPassword();
        String passwordConfirm = userModifyDTO.getPasswordConfirm();
        String description = userModifyDTO.getDescription();

        if (username != null) {
            user.setUsername(username);
        }

        if (name != null) {
            user.setName(name);
        }

        if (lastname != null) {
            user.setLastname(lastname);
        }

        if (phonenumber != null) {
            user.setPhoneNumber(phonenumber);
        }

        if (image != null) {
            user.setImage(image);
        }

        if (description != null) {
            user.setDescription(description);
        }

        if (password != null && passwordConfirm != null) {
            if (password.equals(passwordConfirm)) {
                user.setPassword(passwordEncoder.encode(password));
            }
        }
        if (email != null) {
            user.setEmail(email);
        }

        return userRepository.save(user);
    }
}
