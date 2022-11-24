package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.media.MediaDTO;
import org.example.model.Media;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MediaMapper {
    private final ModelMapper modelMapper;

    /**
     * Convertir DAO a DTO
     * @param media
     * @return MediaDTO
     */
    public MediaDTO toDTO(Media media) {
        return modelMapper.map(media, MediaDTO.class);
    }

    /**
     * Convertir DTO a DAO
     * @param mediaDTO
     * @return Media
     */
    public Media fromDTO(MediaDTO mediaDTO) {
        return modelMapper.map(mediaDTO, Media.class);
    }

    /**
     * Convertir lista de DAO a lista de dTO
     * @param medias
     * @return lista DTO
     */
    public List<MediaDTO> toDTO(List<Media> medias) {
        return medias.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
