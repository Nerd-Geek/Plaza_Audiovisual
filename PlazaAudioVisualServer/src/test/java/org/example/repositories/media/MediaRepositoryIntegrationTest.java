package org.example.repositories.media;

import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode =  DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class MediaRepositoryIntegrationTest {
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

    private final Media media = Media.builder()
            .id("7dafe5fd-976b-450a-9bab-17ab450a4fff")
            .size(4.4)
            .type(".png")
            .name("Nombre")
            .description("descripcion")
            .dimension(512)
            .user(user)
            .build();

    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    @Order(1)
    void save() {
        Media me = mediaRepository.save(media);

        assertAll(
                () -> assertNotNull(me),
                () -> assertEquals(media.getSize(), me.getSize()),
                () -> assertEquals(media.getType(), me.getType()),
                () -> assertEquals(media.getName(), me.getName()),
                () -> assertEquals(media.getDescription(), me.getDescription()),
                () -> assertEquals(media.getDimension(), me.getDimension()),
                () -> assertEquals(media.getUser().getUsername(), me.getUser().getUsername())
        );
    }

    @Test
    @Order(2)
    void findAll() {
        int size = mediaRepository.findAll().size();
        mediaRepository.save(media);


        assertAll(
                () -> assertEquals(mediaRepository.findAll().stream().count(), 1 + size),
                () -> assertNotNull(mediaRepository.findAll())
        );
    }

    @Test
    @Order(3)
    void findById() {
        Media me = mediaRepository.save(media);

        Media mediaId = mediaRepository.findById(me.getId()).orElse(null);
        assertAll(
                () -> assertEquals(mediaId.getId(), me.getId()),
                () -> assertEquals(mediaId.getSize(), me.getSize()),
                () -> assertEquals(mediaId.getType(), me.getType()),
                () -> assertEquals(mediaId.getName(), me.getName()),
                () -> assertEquals(mediaId.getDescription(), me.getDescription()),
                () -> assertEquals(mediaId.getDimension(), me.getDimension()),
                () -> assertEquals(mediaId.getUser().getUsername(), me.getUser().getUsername())
        );
    }

    @Test
    @Order(4)
    void update() {
        media.setName("Cambiado");
        media.setDescription("Cambiada");
        Media result = mediaRepository.save(media);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(media.getName(), result.getName()),
                () -> assertEquals(media.getDescription(), result.getDescription())
        );
    }

    @Test
    @Order(5)
    void delete() {
        Media me = mediaRepository.save(media);
        mediaRepository.delete(me);
        me = mediaRepository.findById(media.getId()).orElse(null);
        assertNull(me);
    }
}
