package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.example.config.APIConfig;
import org.example.dto.media.MediaDTO;
import org.example.exceptions.GeneralBadRequestException;
import org.example.exceptions.media.MediaNotFoundException;
import org.example.exceptions.media.MediaNotFountException;
import org.example.mapper.MediaMapper;
import org.example.mapper.UserMapper;
import org.example.model.Media;
import org.example.model.User;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.example.service.uploads.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(APIConfig.API_PATH + "/media")
public class MediaController {
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    private final StorageService storageService;
    private final UserRepository userRepository;

    /**
     * Constructor
     * @param mediaRepository
     * @param mediaMapper
     * @param storageService
     * @param userRepository
     */
    @Autowired
    public MediaController(MediaRepository mediaRepository, MediaMapper mediaMapper, StorageService storageService, UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.storageService = storageService;
        this.userRepository = userRepository;
    }

    /**
     * Obtener todas las medias
     * @param limit
     * @return respuesta lista de MediaDTO
     */
    @ApiOperation(value = "Obtener todas las medias", notes = "Obtiene todas las medias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<MediaDTO>> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Media> medias = null;
        try {
            medias = mediaRepository.findAll();

            if (limit.isPresent() && !medias.isEmpty() && medias.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(mediaMapper.toDTO(medias.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!medias.isEmpty()) {
                    return ResponseEntity.ok(mediaMapper.toDTO(medias));
                } else {
                    throw new MediaNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    /**
     * Buscar una media por su nombre
     * @param searchQuery
     * @return respuesta media
     */
    @ApiOperation(value = "Obtener todas las medias", notes = "Obtiene todas las medias")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findByNameContainsIgnoreCase(@RequestParam(name = "searchQuery") Optional<String> searchQuery
    ) {
        List<Media> media;
        try {
            media = mediaRepository.findAll();
            return ResponseEntity.ok(media);
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    /**
     * Obtener una media por su id
     * @param id
     * @return respuesta MediaDTO
     */
    @ApiOperation(value = "Obtener una media por id", notes = "Obtiene una media por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MediaDTO> findById(@PathVariable String id) {
        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) {
            throw new MediaNotFountException(id);
        } else {
            return ResponseEntity.ok(mediaMapper.toDTO(media));
        }
    }

    /**
     * Crear una media
     * @param newMedia
     * @return respuesta MediaDTO
     */
    @ApiOperation(value = "Crear una media", notes = "Crea una media")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = MediaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public ResponseEntity<MediaDTO> newMedia(@RequestBody MediaDTO newMedia) {
        Media media = mediaMapper.fromDTO(newMedia);
        Media mediaInsert = mediaRepository.save(media);
        return ResponseEntity.ok(mediaMapper.toDTO(mediaInsert));
    }

    /**
     * Actualizar una media por su id
     * @param id
     * @param newService
     * @return respuesta MediaDTO
     */
    @ApiOperation(value = "Actualizar una media", notes = "Actualiza una media en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MediaDTO> update(@PathVariable String id, @RequestBody MediaDTO newService) {
        try {
            Media mediaUpdated = mediaRepository.findById(id).orElse(null);
            if (mediaUpdated == null) {
                throw new MediaNotFountException(id);
            } else {

                mediaUpdated.setSize(newService.getSize());
                mediaUpdated.setType(newService.getType());
                mediaUpdated.setName(newService.getName());
                mediaUpdated.setDescription(newService.getDescription());
                mediaUpdated.setDimension(newService.getDimension());
                mediaUpdated = mediaRepository.save(mediaUpdated);

                return ResponseEntity.ok(mediaMapper.toDTO(mediaUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }

    /**
     * Eliminar una media por su id
     * @param id
     * @return respuesta MediaDTO
     */
    @ApiOperation(value = "Elimina una media", notes = "Elimina una media en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MediaDTO> deleteService(@PathVariable String id) {

        Media media = mediaRepository.findById(id).orElse(null);
        if (media == null) {
            throw new MediaNotFountException(id);
        }
        try {
            mediaRepository.delete(media);
            return ResponseEntity.ok(mediaMapper.toDTO(media));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el service");
        }
    }

    @ApiOperation(value = "Crea una media con imagen", notes = "Crea una media con imagen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class),
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MediaDTO> newMedia(
            @RequestPart("description") String description,
            @RequestPart("file") MultipartFile file,
            @AuthenticationPrincipal User user) {
        MediaDTO mediaDTO = new MediaDTO();
        Media media = mediaMapper.fromDTO(mediaDTO);

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            media.setSize(file.getSize());
            String[] type = urlImagen.split("\\.");
            media.setType(type[4]);
            media.setName(urlImagen);
            media.setDescription(description);
            media.setDimension(0);
            media.setUser(user);
        }
        try {
            Media serviceInsertado = mediaRepository.save(media);
            return ResponseEntity.ok(mediaMapper.toDTO(serviceInsertado));
        } catch (MediaNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el producto. Campos incorrectos");
        }
    }
}
