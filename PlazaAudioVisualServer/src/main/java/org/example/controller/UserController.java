package org.example.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.config.APIConfig;
import org.example.config.security.jwt.JwtTokenProvider;
import org.example.config.security.model.JwtUserResponse;
import org.example.config.security.model.LoginRequest;
import org.example.dto.user.CreateUserDTO;
import org.example.dto.user.UserDTO;
import org.example.exceptions.GeneralBadRequestException;
import org.example.exceptions.ServiceNotFoundException;
import org.example.exceptions.user.*;
import org.example.mapper.UserMapper;
import org.example.model.Login;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.service.uploads.StorageService;
import org.example.service.users.UserService;
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
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(APIConfig.API_PATH + "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final LoginRepository loginRepository;

    private final StorageService storageService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

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

    @ApiOperation(value = "Obtener un usuario por id", notes = "Obtiene un usuario en base al id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        User user = userService.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundByIdException(id);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

    @ApiOperation(value = "Obtener un usuario por username", notes = "Obtiene un usuario en base al username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/name/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable String username) {
        User user = userService.findByUsernameIgnoreCase(username).orElse(null);
        if (user == null) {
            throw new UserNotFoundByUsernameException(username);
        } else {
            return ResponseEntity.ok(userMapper.toDTO(user));
        }
    }

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


    @CrossOrigin(origins = "http://localhost:8080")
    @ApiOperation(value = "Obtener un usuario", notes = "Obtiene un usuario que esta logueado")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = UserDTO.class),
            @ApiResponse(code = 404, message = "Not Found", response = UsersNotFoundException.class)
    })
    @GetMapping("/me")
    public UserDTO me(@AuthenticationPrincipal User user) {
        return userMapper.toDTO(user);
    }

    @CrossOrigin(origins = "http://localhost:8080")
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

    @ApiOperation(value = "Crear un usuario", notes = "Crea un usuario")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Created", response = UserDTO.class),
            @ApiResponse(code = 400, message = "Bad Request", response = GeneralBadRequestException.class)
    })
    @PostMapping("/")
    public UserDTO nuevoUsuario(@RequestBody CreateUserDTO newUser) {
        if (userService.findByUsernameIgnoreCase(newUser.getUserername()).isPresent()) {
            throw new UserNameDuplicatedException();
        } else {
            return userMapper.toDTO(userService.save(newUser));
        }
    }

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
    private JwtUserResponse convertUserEntityAndTokenToJwtUserResponse(User user, String jwtToken) {
        return JwtUserResponse
                .jwtUserResponseBuilder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .image(user.getImage())
                .description(user.getDescription())
                .roles(user.getRoles().stream().map(UserRol::name).collect(Collectors.toSet()))
                .token(jwtToken)
                .build();
    }
}
