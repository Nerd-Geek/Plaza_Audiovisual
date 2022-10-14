package org.example.controllers.login;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.login.LoginDTO;
import org.example.model.Login;
import org.example.model.User;
import org.example.model.UserRol;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginRestControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;
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
            .id("ec272c62-9d31-11ec-b909-0242ac120005")
            .user(user)
            .instant(null)
            .token("123213412")
            .build();

    @Autowired
    private JacksonTester<LoginDTO> jsonCreateDTO;
    @Autowired
    private JacksonTester<LoginDTO> jsonLoginDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/logins/")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw"))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<LoginDTO> myObjects = Arrays.asList(mapper.readValue(response.getContentAsString(), LoginDTO[].class));

        assertAll(
                () -> assertEquals(myObjects.get(0).getId(), login.getId()),
                () -> assertEquals(myObjects.get(0).getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(myObjects.get(0).getInstance(), login.getInstant()),
                () -> assertEquals(myObjects.get(0).getToken(), login.getToken())
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {

        var response = mockMvc.perform(
                        get("/rest/logins/" + login.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw"))
                .andReturn().getResponse();

        var res = jsonLoginDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getId(), login.getId()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstance(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );
    }

    @Test
    @Order(3)
    void deleteTest() throws Exception {

        var response = mockMvc.perform(MockMvcRequestBuilders.delete("/rest/logins/" + login.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                        "AyUDaO-t6zeiEw")).andReturn().getResponse();

        var res = jsonLoginDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),

                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getUser().getUsername(), login.getUser().getUsername()),
                () -> assertEquals(res.getInstance(), login.getInstant()),
                () -> assertEquals(res.getToken(), login.getToken())
        );
    }
}
