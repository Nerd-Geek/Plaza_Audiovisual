package org.example.controllers.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.login.LoginDTO;
import org.example.dto.user.LoginUserDTO;
import org.example.mapper.LoginMapper;
import org.example.model.Login;
import org.example.model.User;
import org.example.repositories.LoginRepository;
import org.example.service.uploads.StorageService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRestControllerMockMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private final LoginRepository loginRepository;
    @MockBean
    private final StorageService storageService;
    @MockBean
    private final LoginMapper loginMapper;

    private final User user = User.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d946")
            .username("das")
            .name("Jaime")
            .lastname("pepo")
            .email("pepo@pepo.com")
            .phonenumber("633623787")
            .password("123")
            .description("user")
            .build();

    Login login = Login.builder()
            .id("233149e4-b6f3-4692-ac71-2e8123bc24b2")
            .user(user)
            .instant(null)
            .token("123213412")
            .build();

    LoginUserDTO loginUserDTO = LoginUserDTO.builder()
            .username(user.getUsername())
            .password(user.getPassword())
            .build();
    LoginDTO loginDTO = LoginDTO.builder()
            .id(login.getId())
            .user(loginUserDTO)
            .instance(login.getInstant())
            .token(login.getToken())
            .build();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JacksonTester<LoginDTO> jsonCreateLogin;
    @Autowired
    private JacksonTester<LoginDTO> jsonLogin;

    @Autowired
    public LoginRestControllerMockMVCTest(LoginRepository loginRepository, StorageService storageService, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.storageService = storageService;
        this.loginMapper = loginMapper;
    }

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        Mockito.when(loginRepository.findAll())
                .thenReturn(List.of(login));

        Mockito.when(loginMapper.toDTO(List.of(login)))
                .thenReturn(List.of(loginDTO));

        mockMvc
                .perform(
                        get("/rest/logins/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].instance", is(login.getInstant())))
                .andExpect(jsonPath("$[0].token", is(login.getToken())))
                .andReturn();

        Mockito.verify(loginRepository, Mockito.times(1)).findAll();
        Mockito.verify(loginMapper, Mockito.times(1)).toDTO(List.of(login));
    }

    @Test
    @Order(2)
    void deleteTest() throws Exception {
        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(Optional.of(login));

        Mockito.when(loginMapper.toDTO(login)).thenReturn(loginDTO);

        Mockito.doNothing().when(loginRepository).delete(login);

        mockMvc.perform(
                        delete("/rest/logins/" + login.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.instance", is(login.getInstant())))
                .andExpect(jsonPath("$.token", is(login.getToken())))
                .andReturn();

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
        Mockito.verify(loginRepository, Mockito.times(1))
                .delete(login);
        Mockito.verify(loginMapper, Mockito.times(1))
                .toDTO(login);
    }

    @Test
    @Order(3)
    void deleteExceptionTest() throws Exception {
        Mockito.when(loginRepository.findById(login.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(
                        delete("/rest/logins/" + login.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw"))
                .andExpect(status().isNotFound());

        Mockito.verify(loginRepository, Mockito.times(1))
                .findById(login.getId());
    }
}
