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
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.example.service.uploads.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired

    public MediaController(MediaRepository mediaRepository, MediaMapper mediaMapper, StorageService storageService, UserRepository userRepository) {
        this.mediaRepository = mediaRepository;
        this.mediaMapper = mediaMapper;
        this.storageService = storageService;
        this.userRepository = userRepository;
    }


    @ApiOperation(value = "Obtener todos los servicios", notes = "Obtiene todos los servicios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<MediaDTO>> findAll(@RequestParam(name = "limit") Optional<String> limit) {
        List<Media> services = null;
        try {
            services = mediaRepository.findAll();

            if (limit.isPresent() && !services.isEmpty() && services.size() > Integer.parseInt(limit.get())) {
                return ResponseEntity.ok(mediaMapper.toDTO(services.subList(0, Integer.parseInt(limit.get()))));
            } else {
                if (!services.isEmpty()) {
                    return ResponseEntity.ok(mediaMapper.toDTO(services));
                } else {
                    throw new MediaNotFoundException();
                }
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    @ApiOperation(value = "Obtener todos un servicio", notes = "Obtiene un servicio en base a su nombre")
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

    @ApiOperation(value = "Obtener un servicooo por id", notes = "Obtiene un producto por id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<MediaDTO> findById(@PathVariable String id) {
        Media service = mediaRepository.findById(id).orElse(null);
        if (service == null) {
            throw new MediaNotFountException(id);
        } else {
            return ResponseEntity.ok(mediaMapper.toDTO(service));
        }
    }

    @ApiOperation(value = "Crear un servicio", notes = "Crea un servicio")
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

    @ApiOperation(value = "Actualizar un servicio", notes = "Actualiza un servicio en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<MediaDTO> update(@PathVariable String id, @RequestBody MediaDTO newService) {
        try {
            UserMapper mapper;
            Media serviceUpdated = mediaRepository.findById(id).orElse(null);
            if (serviceUpdated == null) {
                throw new MediaNotFountException(id);
            } else {

                serviceUpdated.setSize(newService.getSize());
                serviceUpdated.setType(newService.getType());
                serviceUpdated.setName(newService.getName());
                serviceUpdated.setDescription(newService.getDescription());
                serviceUpdated.setDimension(newService.getDimension());
                serviceUpdated = mediaRepository.save(serviceUpdated);

                return ResponseEntity.ok(mediaMapper.toDTO(serviceUpdated));
            }
        } catch (Exception e) {
            throw new GeneralBadRequestException("Actualizar", "Error al actualizar el service. Campos incorrectos.");
        }
    }

    @ApiOperation(value = "Actualizar un servicio", notes = "Actualiza un servicio en base a su id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = MediaNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MediaDTO> deleteService(@PathVariable String id) {

        Media service = mediaRepository.findById(id).orElse(null);
        if (service == null) {
            throw new MediaNotFountException(id);
        }
        try {
            mediaRepository.delete(service);
            return ResponseEntity.ok(mediaMapper.toDTO(service));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Eliminar", "Error al borrar el service");
        }
    }

    @ApiOperation(value = "Crea un servicio con imagen", notes = "Crea un servicio con imagen")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = MediaDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class),
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> newMedia(
            @RequestPart("media") MediaDTO serviceDTO,
            @RequestPart("file") MultipartFile file) {

        Media service = mediaMapper.fromDTO(serviceDTO);

        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            service.setName(urlImagen);
        }
        try {
            Media serviceInsertado = mediaRepository.save(service);
            return ResponseEntity.ok(mediaMapper.toDTO(serviceInsertado));
        } catch (MediaNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el producto. Campos incorrectos");
        }
    }
}
