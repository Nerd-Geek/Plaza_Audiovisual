package org.example.config.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.dto.user.UserDTO;
import org.example.model.Media;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class JwtUserResponse extends UserDTO {
    @NotNull(message = "El token no puede ser nulo")
    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String id, String username, String name, String lastName, String email,
                           String phoneNumber, String image, String description, Set<String> usersRoles, Set<Media> medias,
                           String token) {
        super(id, username, name, lastName, email, phoneNumber, image, description, usersRoles, medias);
        this.token = token;
    }
}
