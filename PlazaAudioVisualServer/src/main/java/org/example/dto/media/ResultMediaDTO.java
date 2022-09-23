package org.example.dto.media;

import lombok.Getter;
import lombok.Setter;
import org.example.config.APIConfig;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResultMediaDTO {
    LocalDateTime consult = LocalDateTime.now();
    String project = "SpringBootAPIRest";
    String version = APIConfig.API_VERSION;
    List<CreateMediaDTO> data;

    public ResultMediaDTO(List<CreateMediaDTO> data) {
        this.data = data;
    }
}
