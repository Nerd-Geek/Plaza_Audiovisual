package org.example.repositories.login;

import org.example.model.Login;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class LoginRepositoryIntegrationTest {
    private final User user = User.builder()
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

    private final Login login = Login.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120002")
            .user(user)
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC")
            .instant(Date.from(Instant.now()))
            .build();
    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
        loginRepository.save(login);
    }

    @Test
    @Order(1)
    void save() {
        Login res = loginRepository.save(login);

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );
    }

    @Test
    @Order(2)
    void getAllLogin() {
        assertTrue(loginRepository.findAll().size() > 0);
    }

    @Test
    @Order(3)
    void getLoginById() {
        Login log = loginRepository.save(login);
        Login res = loginRepository.findById(log.getId()).get();

        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant()),
                () -> assertEquals(login.getToken(), res.getToken())
        );
    }

    @Test
    @Order(4)
    void updateLogin() {
        Login logi = loginRepository.save(login);
        logi = loginRepository.findById(logi.getId()).get();
        logi.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC");

        Login res = loginRepository.save(logi);
        assertAll(
                () -> assertNotNull(res),
                () -> assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC", res.getToken()),
                () -> assertEquals(login.getUser().getUsername(), res.getUser().getUsername()),
                () -> assertEquals(login.getInstant(), res.getInstant())
        );
    }

    @Test
    @Order(5)
    public void deleteLogin() {
        Login res = loginRepository.save(login);
        res = loginRepository.findById(res.getId()).get();

        loginRepository.delete(res);

        assertNull(loginRepository.findById(res.getId()).orElse(null));

    }
}
