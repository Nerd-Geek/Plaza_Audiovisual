package org.example.repositories.user;

import org.example.model.Login;
import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTypeExcludeFilter;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
public class UserRepositoryJPATest {
    private static final User user = User.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .username("nombre usuario")
            .name("nombre")
            .lastname("apellido")
            .email("adsa@sdas.com")
            .phonenumber("123456789")
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
            .size(4L)
            .type(".png")
            .name("Nombre")
            .description("descripcion")
            .dimension(512)
            .user(user)
            .build();

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        user.setMedias(Set.of(media));
        user.setLogins(Set.of(login));
        entityManager.persist(user);
        entityManager.persist(login);
        entityManager.persist(media);
        entityManager.flush();
    }

    @Test
    @Order(1)
    void getAll() {
        assertTrue(mediaRepository.findAll().size() > 0);
    }

    @Test
    @Order(2)
    void getById() {

        User theUser = userRepository.findById(user.getId()).orElse(null);
        assertAll(
                () -> assertNotNull(user),
                () -> assertEquals(user.getId(), theUser.getId()),
                () -> assertEquals(user.getUsername(), theUser.getUsername()),
                () -> assertEquals(user.getName(), theUser.getName()),
                () -> assertEquals(user.getLastname(), theUser.getLastname()),
                () -> assertEquals(user.getEmail(), theUser.getEmail()),
                () -> assertEquals(user.getPhoneNumber(), theUser.getPhoneNumber()),
                () -> assertEquals(user.getPassword(), theUser.getPassword()),
                () -> assertEquals(user.getDescription(), theUser.getDescription()),
                () -> assertEquals(user.getRoles(), theUser.getRoles()),
                () -> assertEquals(user.getMedias().size(), theUser.getMedias().size()),
                () -> assertEquals(user.getLogins().size(), theUser.getLogins().size())
        );
    }

    @Test
    @Order(3)
    void save() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("pepo")
                .name("pepo")
                .lastname("apellido")
                .email("pepo@pepo.com")
                .phonenumber("123456789")
                .password("$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu")
                .description("descripcion")
                .roles(Set.of(UserRol.NORMAL, UserRol.ADMIN))
                .medias(new HashSet<>())
                .logins(new HashSet<>())
                .build();

        User savedUser = userRepository.save(newUser);

        assertAll(
                () -> assertEquals(newUser.getId(), savedUser.getId()),
                () -> assertEquals(newUser.getUsername(), savedUser.getUsername()),
                () -> assertEquals(newUser.getName(), savedUser.getName()),
                () -> assertEquals(newUser.getLastname(), savedUser.getLastname()),
                () -> assertEquals(newUser.getEmail(), savedUser.getEmail()),
                () -> assertEquals(newUser.getPhoneNumber(), savedUser.getPhoneNumber()),
                () -> assertEquals(newUser.getPassword(), savedUser.getPassword()),
                () -> assertEquals(newUser.getDescription(), savedUser.getDescription()),
                () -> assertEquals(newUser.getRoles(), savedUser.getRoles()),
                () -> assertEquals(newUser.getMedias().size(), savedUser.getMedias().size()),
                () -> assertEquals(newUser.getLogins().size(), savedUser.getLogins().size())
        );
    }

    @Test
    @Order(4)
    void update() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("pepo")
                .name("pepo")
                .lastname("apellido")
                .email("pepo@pepo.com")
                .phonenumber("123456789")
                .password("$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu")
                .description("descripcion")
                .roles(Set.of(UserRol.NORMAL, UserRol.ADMIN))
                .medias(new HashSet<>())
                .logins(new HashSet<>())
                .build();

        User savedUser = userRepository.save(newUser);

        User modUser = userRepository.getById(savedUser.getId());
        modUser.setPhoneNumber("090999909");

        userRepository.save(modUser);
        assertAll(
                () -> assertEquals(modUser.getId(), savedUser.getId()),
                () -> assertEquals(modUser.getUsername(), savedUser.getUsername()),
                () -> assertEquals(modUser.getName(), savedUser.getName()),
                () -> assertEquals(modUser.getLastname(), savedUser.getLastname()),
                () -> assertEquals(modUser.getEmail(), savedUser.getEmail()),
                () -> assertEquals(modUser.getPhoneNumber(), savedUser.getPhoneNumber()),
                () -> assertEquals(modUser.getPassword(), savedUser.getPassword()),
                () -> assertEquals(modUser.getDescription(), savedUser.getDescription()),
                () -> assertEquals(modUser.getRoles(), savedUser.getRoles()),
                () -> assertEquals(modUser.getMedias().size(), savedUser.getMedias().size()),
                () -> assertEquals(modUser.getLogins().size(), savedUser.getLogins().size())
        );
    }

    @Test
    @Order(5)
    void delete() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("pepo")
                .name("pepo")
                .lastname("apellido")
                .email("pepo@pepo.com")
                .phonenumber("123456789")
                .password("$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu")
                .description("descripcion")
                .roles(Set.of(UserRol.NORMAL, UserRol.ADMIN))
                .medias(new HashSet<>())
                .logins(new HashSet<>())
                .build();

        entityManager.persist(newUser);
        entityManager.flush();

        userRepository.delete(newUser);
        assertNull(userRepository.findById(newUser.getId()).orElse(null));
    }
}
