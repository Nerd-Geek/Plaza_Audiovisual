package org.example.repositories.login;

import lombok.RequiredArgsConstructor;
import org.example.model.Login;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import javax.transaction.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TypeExcludeFilters(value= DataJpaTypeExcludeFilter.class)
@AutoConfigureCache
@AutoConfigureDataJpa
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@ImportAutoConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRepositoryJPATest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoginRepository loginRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user.setMedias(null);
        entityManager.persist(user);
        entityManager.persist(login);
    }
    private final User user = User.builder()
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

    private final Login login = Login.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC")
            .instant(Date.from(Instant.now()))
            .build();

    @Test
    @Order(1)
    void save() {
        Login saved = loginRepository.save(login);
        assertAll(
                () -> assertEquals(login.getUser().getUsername(), saved.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), saved.getInstant()),
                () -> assertEquals(login.getToken(), saved.getToken())
        );
    }

    @Test
    @Order(2)
    void getAllTest() {
        entityManager.persist(login);
        entityManager.flush();

        assertTrue(loginRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getByIdTest() {
        entityManager.persist(login);
        entityManager.flush();

        Login found = loginRepository.findById(login.getId()).get();
        assertAll(
                () -> assertEquals(login.getUser().getUsername(), found.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), found.getInstant())
        );
    }

    @Test
    @Order(4)
    void update() {
        entityManager.persist(login);
        entityManager.flush();

        Login found = loginRepository.findById(login.getId()).get();
        found.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC");
        Login updated = loginRepository.save(found);
        assertAll(
                () -> assertEquals(login.getUser().getUsername(), updated.getUser().getUsername()),
                () -> assertEquals(login.getToken(), updated.getToken())
        );
    }

    @Test
    @Order(5)
    void delete() {
        entityManager.persist(login);
        entityManager.flush();
        Login res = loginRepository.findById(login.getId()).get();
        loginRepository.delete(login);
        res = loginRepository.findById(login.getId()).orElse(null);
        assertNull(res);
    }
}
