package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.config.APIConfig;
import org.example.config.security.jwt.JwtTokenProvider;
import org.example.config.security.model.JwtUserResponse;
import org.example.config.security.model.LoginRequest;
import org.example.dto.login.LoginDTO;
import org.example.dto.user.CreateUserDTO;
import org.example.dto.user.UserDTO;
import org.example.exceptions.GeneralBadRequestException;
import org.example.exceptions.ServiceNotFoundException;
import org.example.exceptions.login.LoginNotFoundException;
import org.example.exceptions.storage.StorageException;
import org.example.exceptions.user.*;
import org.example.mapper.UserMapper;
import org.example.model.Login;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.UserRepository;
import org.example.service.uploads.StorageService;
import org.example.service.users.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginRepository loginRepository;
    private final UserRepository userRepository;

    private final StorageService storageService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    /**
     * Obtener todos los usuarios
     * @param searchQuery
     * @return respuesta lista de usuarios
     */
    @ApiOperation(value = "Obtener todos los usuarios", notes = "Obtiene todos los usuarios")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(
            @RequestParam("searchQuery") Optional<String> searchQuery
    ) {
        List<User> users;
        try {
            if (searchQuery.isPresent())
                users = userService.findByUsernameContainsIgnoreCase(searchQuery.get());
            else
                users = userService.findAll();
            return ResponseEntity.ok(userMapper.toDTO(users));
        } catch (Exception e) {
            throw new GeneralBadRequestException("Selección de Datos", "Parámetros de consulta incorrectos");
        }
    }

    /**
     * Obtener un usuario por su id
     * @param id
     * @return respuesta UserDTO
     */
    @ApiOperation(value = "Obtener un usuario por id", notes = "Obtiene un usuario en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    /**
     * Buscar un usuario por su nombre
     * @param username
     * @return respuesta UserDTO
     */
    @ApiOperation(value = "Obtener un usuario por username", notes = "Obtiene un usuario en base al username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/name/{username}")
    public ResponseEntity<UserDTO> findByUsername(@PathVariable String username) {
        User user = userService.findByUsernameIgnoreCase(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    /**
     * Buscar un usuario por su email
     * @param email
     * @return respuesta UserDTO
     */
    @ApiOperation(value = "Obtener un usuario por email", notes = "Obtiene un usuario en base al email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<?> findByEmail(@PathVariable String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundByEmailException(email);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    /**
     * Obtener un usuario
     * @param user
     * @return respuesta UserDTO
     */
    @ApiOperation(value = "Obtener un usuario", notes = "Obtiene un usuario que esta logueado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal User user) {
        return userMapper.toDTO(user);
    }

    /**
     * Loguear un usuario
     * @param loginRequest
     * @return respuesta JwtUserResponse
     */
    @ApiOperation(value = "Loguear un usuario", notes = "Loguea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/login")
    public JwtUserResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwtToken = tokenProvider.generateToken(authentication);
        Login login = new Login(jwtToken, Date.from(Instant.now()), user);
        loginRepository.save(login);
        return convertUserEntityAndTokenToJwtUserResponse(user, jwtToken);
    }

    /**
     * Crear un usuario
     * @param newUser
     * @return respuesta UserDTO
     * @throws MalformedURLException
     */
    @ApiOperation(value = "Crear un usuario", notes = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public UserDTO nuevoUsuario(@RequestBody CreateUserDTO newUser) throws MalformedURLException {
        if (userService.findByUsernameIgnoreCase(newUser.getUsername()).isPresent()) {
            throw new UserNameDuplicatedException();
        } else {
            User user = userMapper.fromDTOCreate(newUser);

            URL firstURL = new URL("http://carlosmoreno.duckdns.org:9090/rest/files/1666536760224_avatar2.png");
            String imagen = firstURL.toString();
            user.setImage(imagen);
            newUser.setImage(user.getImage());
            return userMapper.toDTO(userService.save(newUser));
        }
    }

    /**
     * Crear un usuario
     * @param file
     * @param createUserDTO
     * @return respuesta UserDTI
     */
    @ApiOperation(value = "Crear un usuario", notes = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> nuevoUsuario(
            @RequestPart("file") MultipartFile file, @RequestPart("user") CreateUserDTO createUserDTO) {

        User user = userMapper.fromDTOCreate(createUserDTO);
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            user.setImage(urlImagen);
            createUserDTO.setImage(user.getImage());
        }
        try {
            User userInsertado = userService.save(createUserDTO);
            return ResponseEntity.ok(userMapper.toDTO(userInsertado));
        } catch (ServiceNotFoundException ex) {
            throw new GeneralBadRequestException("Insertar", "Error al insertar el producto. Campos incorrectos");
        }
    }

    /**
     * Actualizar un usuario
     * @param user
     * @param userModifyDTO
     * @return respuesta UserDTO
     */
    @ApiOperation(value = "Actualizar usuario", notes = "Actualiza el usuario logueado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CreateUserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralBadRequestException.class)
    })
    @PutMapping("/me")
    public ResponseEntity<UserDTO> mePut(@AuthenticationPrincipal User user, @RequestBody CreateUserDTO userModifyDTO) {
        System.out.println(userModifyDTO);
        User created = userService.updateUser(userModifyDTO, user);
        return ResponseEntity.ok(userMapper.toDTO(created));
    }

    /**
     * Actualizar el avatar de un usuario
     * @param user
     * @param file
     * @return respuesta avatar
     */
    @ApiOperation(value = "Actualizar el avatar del usuario", notes = "Actualiza el avatar del usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CreateUserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = GeneralBadRequestException.class)
    })
    @PostMapping("/avatar")
    public ResponseEntity<?> updateAvatar(@AuthenticationPrincipal User user, @RequestPart("file") MultipartFile file) {
        String urlImage = null;
        if (!file.isEmpty()) {
            String imagen = storageService.store(file);
            String urlImagen = storageService.getUrl(imagen);
            user.setImage(urlImagen);
        }
        try {
            String image = storageService.store(file);
            userRepository.save(user);
            urlImage = storageService.getUrl(image);
            String response =  urlImage;
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch(StorageException e)
        {
            throw new StorageException("No se puede subir un fichero vacío");
        }
    }

    /**
     *
     * @param user
     * @param jwtToken
     * @return JwtUserResponse
     */
    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(User user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .image(user.getImage())
                .description(user.getDescription())
                .roles(user.getRoles().stream().map(UserRol::name).collect(Collectors.toSet()))
                .token(jwtToken)
                .expirateToken(86400000)
                .build();
    }
}
