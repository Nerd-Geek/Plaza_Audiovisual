package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.user.CreateUserDTO;
import org.example.dto.user.UserDTO;
import org.example.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    /**
     * Convertir DAO a DTO
     * @param user
     * @return UserDTO
     */
    public UserDTO toDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    /**
     * Convertir DTO a DAO
     * @param userDTO
     * @return User
     */
    public User fromDTOCreate(CreateUserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    /**
     * Convertir lista de DAO a lista de dTO
     * @param users
     * @return lista DTO
     */
    public List<UserDTO> toDTO(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
