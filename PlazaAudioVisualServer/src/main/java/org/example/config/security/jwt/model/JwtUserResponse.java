package org.example.config.security.jwt.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.dto.user.UserDTO;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserDTO {
    @NotNull(message = "El token no puede ser nulo")
    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String id, String userName, String firstName, String lastName, String email,
                           String phoneNumber, String image, String description, Set<String> usersRoles, String token) {
        super(id, userName, firstName, lastName, email, phoneNumber, image, description, usersRoles);
        this.token = token;
    }
}
