package org.example.config.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.config.security.jwt.JwtTokenProvider;
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
    private int expirateToken;
    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(String id, String username, String name, String lastName, String email,
                           String phoneNumber, String image, String description, Set<String> roles, Set<Media> medias,
                           String token, int expirateToken) {
        super(id, username, name, lastName, email, phoneNumber, image, description, roles, medias);
        this.token = token;
        this.expirateToken = expirateToken;
    }
}
