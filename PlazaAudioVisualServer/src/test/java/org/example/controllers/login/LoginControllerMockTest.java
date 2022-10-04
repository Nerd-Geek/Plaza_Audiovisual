package org.example.controllers.login;

import org.example.controller.LoginController;
import org.example.dto.login.LoginDTO;
import org.example.dto.user.LoginUserDTO;
import org.example.exceptions.login.LoginNotFoundException;
import org.example.mapper.LoginMapper;
import org.example.model.Login;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.UserRepository;
import org.example.service.uploads.StorageService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerMockTest {
    @MockBean
    private final LoginRepository loginRepository;
    @MockBean
    private final StorageService storageService;
    @MockBean
    private final LoginMapper loginMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user.setMedias(null);
        userRepository.save(user);
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
            .user(user)
            .token("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTMzNGQ1Ny0xMjBiLTQzN2ItYmFlZi1jZjViNWY2OGNjM2UiLC")
            .instant(Date.from(Instant.now()))
            .build();
    @InjectMocks
    private LoginController loginController;

    @Autowired
    public LoginControllerMockTest(LoginRepository loginRepository, StorageService storageService, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.storageService = storageService;
        this.loginMapper = loginMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instance(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findAll())
                .thenReturn(List.of(login));

        Mockito.when(loginMapper.toDTO(List.of(login))).thenReturn(List.of(dto));

        var response = loginController.findAll();
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.get(0).getInstance(), login.getInstant()),
                () -> assertEquals(res.get(0).getToken(), login.getToken())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instance(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(Optional.of(login));

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.findById(login.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstance(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1)).findById(login.getId());
        Mockito.verify(loginMapper, Mockito.times(1)).toDTO(login);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(loginRepository.findById(login.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(LoginNotFoundException.class, () -> {
            loginController.findById(login.getId());
        });

        assertTrue(ex.getMessage().contains("login"));

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
    }

    @Test
    @Order(4)
    void saveTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instance(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.save(login))
                .thenReturn(login);

        Mockito.when(loginMapper.fromDTO(dto))
                .thenReturn(login);

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.save(dto);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstance(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .save(login);
        Mockito.verify(loginMapper, Mockito.times(1))
                .fromDTO(dto);
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }

    @Test
    @Order(4)
    void updateTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instance(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(java.util.Optional.of(login));

        Mockito.when(loginRepository.save(login))
                .thenReturn(login);

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.update(login.getId(), login);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstance(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
        Mockito.verify(loginRepository, Mockito.times(1))
                .save(login);
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }

    @Test
    @Order(5)
    void deleteTestMock() {
        LoginUserDTO loginUserDTO = LoginUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        LoginDTO dto = LoginDTO.builder()
                .user(loginUserDTO)
                .instance(login.getInstant())
                .token(login.getToken())
                .build();

        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(java.util.Optional.of(login));

        Mockito.when(loginMapper.toDTO(login)).thenReturn(dto);

        var response = loginController.delete(login.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstance(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }
}