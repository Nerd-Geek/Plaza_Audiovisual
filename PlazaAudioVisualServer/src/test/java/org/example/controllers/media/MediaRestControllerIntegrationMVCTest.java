package org.example.controllers.media;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.media.MediaDTO;
import org.example.model.Media;
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

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MediaRestControllerIntegrationMVCTest {
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    MockMvc mockMvc;

    private final Media media = Media.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d940")
            .size(2.5)
            .type("png")
            .name("adas")
            .description("as")
            .dimension(512)
            .build();

    @Autowired
    private JacksonTester<MediaDTO> jsonCreateCourseDTO;
    @Autowired
    private JacksonTester<MediaDTO> jsonCourseDTO;

    @Test
    @Order(1)
    void findAllTest() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(
                        get("/rest/media/")
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw"))
                .andReturn().getResponse();

        ObjectMapper mapper = new ObjectMapper();
        List<MediaDTO> myObjects = Arrays.asList(mapper.readValue(response.getContentAsString(), MediaDTO[].class));

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(myObjects.get(0).getSize(), media.getSize()),
                () -> assertEquals(myObjects.get(0).getType(), media.getType()),
                () -> assertEquals(myObjects.get(0).getName(), media.getName()),
                () -> assertEquals(myObjects.get(0).getDescription(), media.getDescription()),
                () -> assertEquals(myObjects.get(0).getDimension(), media.getDimension())
        );
    }

    @Test
    @Order(2)
    void findByIdTest() throws Exception {

        var response = mockMvc.perform(
                        get("/rest/media/" + media.getId())
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                                        "AyUDaO-t6zeiEw"))
                .andReturn().getResponse();

        var res = jsonCourseDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getSize(), media.getSize()),
                () -> assertEquals(res.getType(), media.getType()),
                () -> assertEquals(res.getName(), media.getName()),
                () -> assertEquals(res.getDescription(), media.getDescription()),
                () -> assertEquals(res.getDimension(), media.getDimension())
        );
    }

    @Test
    @Order(3)
    void saveTest() throws Exception {
        MediaDTO mediaDTO = MediaDTO.builder()
                .id(media.getId())
                .size(media.getSize())
                .type(media.getType())
                .name(media.getName())
                .description(media.getDescription())
                .dimension(media.getDimension())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.post("/rest/media/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCreateCourseDTO.write(mediaDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                        "AyUDaO-t6zeiEw")).andReturn().getResponse();

        var res = jsonCourseDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getSize(), media.getSize()),
                () -> assertEquals(res.getType(), media.getType()),
                () -> assertEquals(res.getName(), media.getName()),
                () -> assertEquals(res.getDescription(), media.getDescription()),
                () -> assertEquals(res.getDimension(), media.getDimension())
        );
    }

    @Test
    @Order(4)
    void updateTest() throws Exception {

        MediaDTO mediaDTO = MediaDTO.builder()
                .size(media.getSize())
                .type(media.getType())
                .name(media.getName())
                .description(media.getDescription())
                .dimension(media.getDimension())
                .build();

        var response = mockMvc.perform(MockMvcRequestBuilders.put("/rest/media/" + media.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCourseDTO.write(mediaDTO).getJson())
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9" +
                        ".eyJzdWIiOiJjNTU4MTNkZS1jZGJhLTQyYzYtOTU1NC01NzllNDM2OGQ5NDYiLCJpYXQiOjE2N" +
                        "jQxMjE0MDUsImV4cCI6MTY2NDIwNzgwNSwibmFtZSI6IkphaW1lIiwicm9sZXMiOiJBRE1JTiJ" +
                        "9.z9-ATCMWiluAdKuC1bJ7_FYLzmtd7MpThv1eM3ruUb0iJvXIU8Rsw6f7bk_wn1RZCYQwOBvG" +
                        "AyUDaO-t6zeiEw")).andReturn().getResponse();

        var res = jsonCourseDTO.parseObject(response.getContentAsString());

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatus()),
                () -> assertEquals(res.getSize(), media.getSize()),
                () -> assertEquals(res.getType(), media.getType()),
                () -> assertEquals(res.getName(), media.getName()),
                () -> assertEquals(res.getDescription(), media.getDescription()),
                () -> assertEquals(res.getDimension(), media.getDimension())
        );
    }
}
