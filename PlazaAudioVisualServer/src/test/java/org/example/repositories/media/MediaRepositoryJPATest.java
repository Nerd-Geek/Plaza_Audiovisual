package org.example.repositories.media;

import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
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
public class MediaRepositoryJPATest {
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
    private TestEntityManager entityManager;
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user.setMedias(Set.of(media));
        entityManager.persist(user);
        entityManager.persist(media);
        entityManager.flush();
    }

    @Test
    @Order(1)
    void getAll() {
        assertAll (
                () -> assertTrue(mediaRepository.findAll().size() > 0)
        );
    }

    @Test
    @Order(2)
    void getById() {
        Media me = mediaRepository.findById(media.getId()).orElse(null);
        assertAll(
                () -> assertEquals(me.getId(), media.getId()),
                () -> assertEquals(me.getSize(), media.getSize()),
                () -> assertEquals(me.getType(), media.getType()),
                () -> assertEquals(me.getName(), media.getName()),
                () -> assertEquals(me.getDescription(), media.getDescription()),
                () -> assertEquals(me.getDimension(), media.getDimension()),
                () -> assertEquals(me.getUser().getUsername(), media.getUser().getUsername())
        );
    }

    @Test
    @Order(3)
    void save() {
        Media me = mediaRepository.save(media);

        assertAll(
                () -> assertEquals(me.getId(), media.getId()),
                () -> assertEquals(me.getSize(), media.getSize()),
                () -> assertEquals(me.getType(), media.getType()),
                () -> assertEquals(me.getName(), media.getName()),
                () -> assertEquals(me.getDescription(), media.getDescription()),
                () -> assertEquals(me.getDimension(), media.getDimension()),
                () -> assertEquals(me.getUser().getUsername(), media.getUser().getUsername())
        );
    }

    @Test
    @Order(4)
    void update() {
        media.setName("Cambiado");
        Media me = mediaRepository.save(media);

        assertAll(
                () -> assertEquals(me.getId(), media.getId()),
                () -> assertEquals(me.getSize(), media.getSize()),
                () -> assertEquals(me.getType(), media.getType()),
                () -> assertEquals(me.getName(), media.getName()),
                () -> assertEquals(me.getDescription(), media.getDescription()),
                () -> assertEquals(me.getDimension(), media.getDimension()),
                () -> assertEquals(me.getUser().getUsername(), media.getUser().getUsername())
        );
    }

    @Test
    @Order(5)
    void delete() {
        mediaRepository.delete(media);
        assertNull(mediaRepository.findById(media.getId()).orElse(null));
    }
}
