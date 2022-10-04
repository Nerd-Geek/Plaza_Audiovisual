package org.example.repositories.user;

import org.example.model.Login;
import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryMockTest {
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private MediaRepository mediaRepository;

    private static final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .name("nombre")
            .lastName("apellido")
            .email("adsa@sdas.com")
            .phoneNumber("123456789")
            .password("123")
            .description("descripcion")
            .roles(Set.of(UserRol.NORMAL, UserRol.ADMIN))
            .medias(new HashSet<>())
            .logins(new HashSet<>())
            .build();

    private static final Login login = Login.builder()
            .id("233149e4-b6f3-4692-ac71-2e8123bc24b3")
            .user(user)
            .instant(Date.from(Instant.now()))
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0" +
                    "xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLCJpYXQiOjE2NDY1NjY4OTIsI" +
                    "mV4cCI6MTY0NjY1MzI5MiwibmFtZSI6IkFkbWluIiwicm9sZXMiOiJVU0VSLCBBRE" +
                    "1JTiJ9.8LxNLPBZF5xxmpVF3GRNyFOQCVnSGQlnOZiEW6BNZjb5VkvELVJR30Rc4BK4W" +
                    "ddlAivBy7dUEbArgy-EYgTW0w")
            .build();

    private static final Media media = Media.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120003")
            .size(4.4)
            .type(".png")
            .name("Nombre")
            .description("descripcion")
            .dimension(512)
            .user(user)
            .build();

    @Test
    @Order(1)
    void findAll() {
        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        List<User> users = userRepository.findAll();
        assertAll(
                () -> assertEquals(users.size(), 1)
        );
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        assertEquals(userRepository.findById(user.getId()).orElse(null), user);
        Mockito.verify(userRepository, Mockito.times(1)).findById(user.getId());
    }

    @Test
    @Order(3)
    void save() {
        Mockito.when(userRepository.save(user)).thenReturn(user);

        assertEquals(userRepository.save(user), user);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    @Order(4)
    void delete() {
        Mockito.doNothing().when(userRepository).delete(user);
        userRepository.delete(user);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }
}
