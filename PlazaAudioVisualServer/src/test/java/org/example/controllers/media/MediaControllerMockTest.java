package org.example.controllers.media;

import org.example.controller.MediaController;
import org.example.dto.media.MediaDTO;
import org.example.dto.user.UserDTO;
import org.example.exceptions.media.MediaNotFountException;
import org.example.mapper.MediaMapper;
import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.example.service.uploads.StorageService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MediaControllerMockTest {
    @MockBean
    private final MediaRepository mediaRepository;
    @MockBean
    private final StorageService storageService;
    @MockBean
    private final MediaMapper mediaMapper;
    private final Media media = Media.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d940")
            .size(2.5)
            .type("png")
            .name("adas")
            .description("as")
            .dimension(512)
            .build();
    @InjectMocks
    private MediaController mediaController;

    @Autowired
    public MediaControllerMockTest(MediaRepository mediaRepository, StorageService storageService, MediaMapper mediaMapper) {
        this.mediaRepository = mediaRepository;
        this.storageService = storageService;
        this.mediaMapper = mediaMapper;
    }

    @Test
    @Order(1)
    void getAllTestMock() {

        MediaDTO mediaDTO = MediaDTO.builder()
                .size(media.getSize())
                .type(media.getType())
                .name(media.getName())
                .description(media.getDescription())
                .dimension(media.getDimension())
                .build();

        Mockito.when(mediaRepository.findAll())
                .thenReturn(List.of(media));

        Mockito.when(mediaMapper.toDTO(List.of(media))).thenReturn(List.of(mediaDTO));

        var response = mediaController.findAll(
                java.util.Optional.empty()
        );
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.get(0).getSize(), media.getSize()),
                () -> assertEquals(res.get(0).getType(), media.getType()),
                () -> assertEquals(res.get(0).getName(), media.getName()),
                () -> assertEquals(res.get(0).getDescription(), media.getDescription()),
                () -> assertEquals(res.get(0).getDimension(), media.getDimension())
        );
    }

    @Test
    @Order(2)
    void getByIdTestMock() {

        MediaDTO mediaDTO = MediaDTO.builder()
                .size(media.getSize())
                .type(media.getType())
                .name(media.getName())
                .description(media.getDescription())
                .dimension(media.getDimension())
                .build();

        Mockito.when(mediaRepository.findById(media.getId()))
                .thenReturn(Optional.of(media));

        Mockito.when(mediaMapper.toDTO(media)).thenReturn(mediaDTO);

        var response = mediaController.findById(media.getId());
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getSize(), media.getSize()),
                () -> assertEquals(res.getType(), media.getType()),
                () -> assertEquals(res.getName(), media.getName()),
                () -> assertEquals(res.getDescription(), media.getDescription()),
                () -> assertEquals(res.getDimension(), media.getDimension())
        );

        Mockito.verify(mediaRepository, Mockito.times(1)).findById(media.getId());
        Mockito.verify(mediaMapper, Mockito.times(1)).toDTO(media);
    }

    @Test
    @Order(3)
    void findByIdException() {
        Mockito.when(mediaRepository.findById(media.getId())).thenReturn(Optional.empty());

        Exception ex = assertThrows(MediaNotFountException.class, () -> {
            mediaController.findById(media.getId());
        });

        assertTrue(ex.getMessage().contains("service"));

        Mockito.verify(mediaRepository, Mockito.times(1))
                .findById(media.getId());
    }

    @Test
    @Order(4)
    void updateTestMock() {


        MediaDTO mediaDTO = MediaDTO.builder()
                .size(media.getSize())
                .type(media.getType())
                .name(media.getName())
                .description(media.getDescription())
                .dimension(media.getDimension())
                .build();

        Mockito.when(mediaRepository.findById(media.getId()))
                .thenReturn(java.util.Optional.of(media));

        Mockito.when(mediaRepository.save(media))
                .thenReturn(media);

        Mockito.when(mediaMapper.toDTO(media)).thenReturn(mediaDTO);

        var response = mediaController.update(media.getId(), mediaDTO);
        var res = response.getBody();

        assertAll(
                () -> assertEquals(HttpStatus.OK.value(), response.getStatusCode().value()),
                () -> assertEquals(res.getSize(), media.getSize()),
                () -> assertEquals(res.getType(), media.getType()),
                () -> assertEquals(res.getName(), media.getName()),
                () -> assertEquals(res.getDescription(), media.getDescription()),
                () -> assertEquals(res.getDimension(), media.getDimension())
        );

        Mockito.verify(mediaRepository, Mockito.times(1))
                .findById(media.getId());
        Mockito.verify(mediaRepository, Mockito.times(1))
                .save(media);
        Mockito.verify(mediaMapper, Mockito.times(1))
                .toDTO(media);
    }
}
