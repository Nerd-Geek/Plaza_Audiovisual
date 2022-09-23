package org.example.dto.media;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.example.dto.user.UserDTO;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateMediaDTO {
    private String id = UUID.randomUUID().toString();
    private Double size;
    private String type;
    private String name;
    private int dimension;
    @JsonBackReference
    private UserDTO user;
}
