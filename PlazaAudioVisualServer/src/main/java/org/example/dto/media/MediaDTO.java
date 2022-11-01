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
public class MediaDTO {
    private String id = UUID.randomUUID().toString();
    private Long size;
    private String type;
    private String name;
    private String description;
    private int dimension;
}
