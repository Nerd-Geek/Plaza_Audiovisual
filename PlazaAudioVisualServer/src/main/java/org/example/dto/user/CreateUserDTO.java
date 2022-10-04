package org.example.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "El id no puede estar vacío")
    private String id;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String userername;
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String name;
    @NotBlank(message = "El apellido del usuario no puede estar vacío")
    private String lastName;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
    @Size(min = 9, max = 15)
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String phoneNumber;
    @NotBlank(message = "La password no puede estar vacía")
    private String password;
    @NotBlank(message = "La confirmación de la password no puede estar vacía")
    private String passwordConfirm;
    private String image;
    private String description;

    public CreateUserDTO(String userername, String name, String lastName, String email, String phoneNumber, String password, String passwordConfirm, String image, String description) {
        this.id = UUID.randomUUID().toString();
        this.userername = userername;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.image = image;
        this.description = description;
    }
}
