package org.example.dto.user;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    @NotBlank(message = "El id no puede estar vacío")
    private String id;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String userName;
    @NotBlank(message = "El nombre del usuario no puede estar vacío")
    private String firstName;
    @NotBlank(message = "El apellido del usuario no puede estar vacío")
    private String lastName;
    @Email(regexp = ".*@.*\\..*", message = "Email debe ser válido")
    private String email;
    @Size(min = 9, max = 15)
    @NotBlank(message = "El número de teléfono no puede estar vacío")
    private String phoneNumber;
    private String image;
    private String description;
    @NotNull(message = "Los roles no pueden ser nulos")
    private Set<String> usersRoles;
}
