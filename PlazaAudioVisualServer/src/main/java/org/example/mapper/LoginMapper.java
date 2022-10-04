package org.example.mapper;

import lombok.RequiredArgsConstructor;
import org.example.dto.login.LoginDTO;
import org.example.model.Login;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LoginMapper {
    private final ModelMapper modelMapper;

    public LoginDTO toDTO(Login login) {
        return modelMapper.map(login, LoginDTO.class);
    }

    public Login fromDTO(LoginDTO LoginDTO) {
        return modelMapper.map(LoginDTO, Login.class);
    }

    public List<LoginDTO> toDTO(List<Login> logins) {
        return logins.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
