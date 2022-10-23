package org.example.repositories.media;

import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MediaRepositoryMockTest {

    @MockBean
    private MediaRepository mediaRepository;
    @MockBean
    private UserRepository userRepository;
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
            .size(4L)
            .type(".png")
            .name("Nombre")
            .description("descripcion")
            .dimension(512)
            .user(user)
            .build();

    @Test
    @Order(1)
    void findAll() {
        Mockito.when(mediaRepository.findAll()).thenReturn(List.of(media));
        List<Media> medias = mediaRepository.findAll();
        assertAll(
                () -> assertEquals(medias.size(), 1)
        );
        Mockito.verify(mediaRepository, Mockito.times(1)).findAll();
    }

    @Test
    @Order(2)
    void findById() {
        Mockito.when(mediaRepository.findById(media.getId())).thenReturn(Optional.of(media));

        assertEquals(mediaRepository.findById(media.getId()).orElse(null), media);
        Mockito.verify(mediaRepository, Mockito.times(1)).findById(media.getId());
    }

    @Test
    @Order(3)
    void save() {
        Mockito.when(mediaRepository.save(media)).thenReturn(media);

        assertEquals(mediaRepository.save(media), media);
        Mockito.verify(mediaRepository, Mockito.times(1)).save(media);
    }

    @Test
    @Order(4)
    void delete() {
        Mockito.doNothing().when(mediaRepository).delete(media);
        mediaRepository.delete(media);
        Mockito.verify(mediaRepository, Mockito.times(1)).delete(media);
    }
}
