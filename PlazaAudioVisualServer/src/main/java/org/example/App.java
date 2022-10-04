package org.example;

import org.example.config.security.password.PasswordEncoderConfig;
import org.example.controller.UserController;
import org.example.model.Login;
import org.example.model.Media;
import org.example.model.User;
import org.example.model.UserRol;
import org.example.repositories.LoginRepository;
import org.example.repositories.MediaRepository;
import org.example.repositories.UserRepository;
import org.example.service.uploads.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Set;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    private Media media = Media.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d940")
            .size(2.5)
            .type("png")
            .name("adas")
            .description("as")
            .dimension(512)
            .build();
    private User pupilNormal = User.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d941")
            .username("sda")
            .name("Jaime")
            .lastName("pepo")
            .email("pepo@pepo.com")
            .phoneNumber("633623787")
            .password("$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu") // Pasword 123
            .description("user")
            .roles(Collections.singleton(UserRol.NORMAL))
            .build();
    private User pupilAdmin = User.builder()
            .id("c55813de-cdba-42c6-9554-579e4368d946")
            .username("das")
            .name("Jaime")
            .lastName("pepo")
            .email("pepo@pepo.com")
            .phoneNumber("633623787")
            .password("$2a$10$Qar.LEjvMxWFsdgCQuQVU.5Eto7JqBzg3YEC94Dd6lOaNGlLirRtu") // Pasword 123
            .description("user")
            .roles(Collections.singleton(UserRol.ADMIN))
            .medias(Set.of(media))
            .build();

    Login login = Login.builder()
            .id("ec272c62-9d31-11ec-b909-0242ac120005")
            .user(pupilAdmin)
            .instant(null)
            .token("123213412")
            .build();

    @Bean
    CommandLineRunner start(UserController userController, UserRepository pupilRepository, StorageService storageService,
                            PasswordEncoder passwordEncoder, PasswordEncoderConfig e, MediaRepository mediaRepository,
                            LoginRepository loginRepository) {
        return (args -> {

//            storageService.deleteAll(); para eliminar los ficheros
          //  storageService.init();

            mediaRepository.save(media);
            pupilRepository.save(pupilNormal);
            pupilRepository.save(pupilAdmin);
            loginRepository.save(login);

            System.out.println(pupilRepository.findAll());
        });
    }
}