package org.example.repositories.user;

import org.example.model.Login;
import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Instant;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryIntegrationTest {
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

    @BeforeEach
    void setUp() {
        user.setMedias(new HashSet<>());
        user.setLogins(new HashSet<>());
        userRepository.save(user);
        loginRepository.save(login);
        mediaRepository.save(media);
        user.setMedias(Set.of(media));
        user.setLogins(Set.of(login));
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        User newUser = User.builder()
                .id(UUID.randomUUID().toString())
                .username("pepo")
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
        User savedUser = userRepository.save(newUser);

        assertAll(
                () -> assertEquals(savedUser.getId(), newUser.getId()),
                () -> assertEquals(savedUser.getUsername(), newUser.getUsername()),
                () -> assertEquals(savedUser.getName(), newUser.getName()),
                () -> assertEquals(savedUser.getLastname(), newUser.getLastname()),
                () -> assertEquals(savedUser.getEmail(), newUser.getEmail()),
                () -> assertEquals(savedUser.getPhoneNumber(), newUser.getPhoneNumber()),
                () -> assertEquals(savedUser.getPassword(), newUser.getPassword()),
                () -> assertEquals(savedUser.getDescription(), newUser.getDescription()),
                () -> assertEquals(savedUser.getRoles(), newUser.getRoles()),
                () -> assertEquals(savedUser.getMedias().size(), newUser.getMedias().size()),
                () -> assertEquals(savedUser.getLogins().size(), newUser.getLogins().size())
        );
    }

    @Test
    @Order(2)
    void findAll() {

        assertAll(
                () -> assertNotNull(userRepository.findAll()),
                () -> assertTrue(userRepository.findAll().stream().count() > 0)
        );
    }

    @Test
    @Order(3)
    void findById() {
        User savedUser = userRepository.findById(user.getId()).orElse(null);

        assertAll(
                () -> assertNotNull(savedUser),
                () -> assertEquals(savedUser.getId(), savedUser.getId()),
                () -> assertEquals(savedUser.getUsername(), savedUser.getUsername()),
                () -> assertEquals(savedUser.getName(), savedUser.getName()),
                () -> assertEquals(savedUser.getLastname(), savedUser.getLastname()),
                () -> assertEquals(savedUser.getEmail(), savedUser.getEmail()),
                () -> assertEquals(savedUser.getPhoneNumber(), savedUser.getPhoneNumber()),
                () -> assertEquals(savedUser.getPassword(), savedUser.getPassword()),
                () -> assertEquals(savedUser.getDescription(), savedUser.getDescription()),
                () -> assertEquals(savedUser.getRoles(), savedUser.getRoles()),
                () -> assertEquals(savedUser.getMedias().size(), savedUser.getMedias().size()),
                () -> assertEquals(savedUser.getLogins().size(), savedUser.getLogins().size())
        );
    }

    @Test
    @Order(4)
    void update() {
        User newUser = User.builder()
                .id("ec272c62-9d31-11ec-b909-0242ac120002")
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
                () -> assertEquals(savedUser.getId(), newUser.getId()),
                () -> assertEquals(savedUser.getUsername(), newUser.getUsername()),
                () -> assertEquals(savedUser.getName(), newUser.getName()),
                () -> assertEquals(savedUser.getLastname(), newUser.getLastname()),
                () -> assertEquals(savedUser.getEmail(), newUser.getEmail()),
                () -> assertEquals(savedUser.getPhoneNumber(), newUser.getPhoneNumber()),
                () -> assertEquals(savedUser.getPassword(), newUser.getPassword()),
                () -> assertEquals(savedUser.getDescription(), newUser.getDescription()),
                () -> assertEquals(savedUser.getRoles(), newUser.getRoles()),
                () -> assertEquals(savedUser.getMedias().size(), newUser.getMedias().size()),
                () -> assertEquals(savedUser.getLogins().size(), newUser.getLogins().size())
        );
    }

    @Test
    @Order(5)
    void delete() {
        User newUser = User.builder()
                .id("ec272c62-9d31-11ec-b909-0242ac120012")
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
        newUser = userRepository.save(newUser);
        userRepository.delete(newUser);
        User begone = userRepository.findById(newUser.getId()).orElse(null);
        assertNull(begone);
    }

    @Test
    @Order(6)
    void findByUsernameIgnoreCase() {
        User savedUser = userRepository.findByUsernameIgnoreCase(user.getUsername().toUpperCase()).orElse(new User());

        assertAll(
                () -> assertEquals(savedUser.getId(), savedUser.getId()),
                () -> assertEquals(savedUser.getUsername(), savedUser.getUsername()),
                () -> assertEquals(savedUser.getName(), savedUser.getName()),
                () -> assertEquals(savedUser.getLastname(), savedUser.getLastname()),
                () -> assertEquals(savedUser.getEmail(), savedUser.getEmail()),
                () -> assertEquals(savedUser.getPhoneNumber(), savedUser.getPhoneNumber()),
                () -> assertEquals(savedUser.getPassword(), savedUser.getPassword()),
                () -> assertEquals(savedUser.getDescription(), savedUser.getDescription()),
                () -> assertEquals(savedUser.getRoles(), savedUser.getRoles()),
                () -> assertEquals(savedUser.getMedias().size(), savedUser.getMedias().size()),
                () -> assertEquals(savedUser.getLogins().size(), savedUser.getLogins().size())
        );
    }

    @Test
    @Order(7)
    void findByUsernameContainsIgnoreCase() {
        List<User> allUsers = userRepository.findByUsernameContainsIgnoreCase("");
        List<User> oneUser = userRepository.findByUsernameContainsIgnoreCase("nombre usuario");
        List<User> noUser = userRepository.findByUsernameContainsIgnoreCase("1203894571-0293548120-9349857");
        List<User> findAll = userRepository.findAll();
        assertAll(
                () -> assertEquals(oneUser.get(0).getUsername(), "nombre usuario"),
                () -> assertEquals(allUsers.size(), findAll.size()),
                () -> assertEquals(noUser.size(), 0)
        );
    }

    @Test
    @Order(8)
    void findByEmail() {
        User emailUser = userRepository.findByEmail("adsa@sdas.com");
        User nonEmailUser  = userRepository.findByEmail("asdfasdfasdfasdfasdfasdfasdfasdf@sdasasdfasdfasdfasdfasdfasdfasdfasdfasddd.comnot");
        assertAll(
                () -> assertEquals(emailUser.getEmail(), "adsa@sdas.com"),
                () -> assertNull(nonEmailUser)
        );
    }
}
