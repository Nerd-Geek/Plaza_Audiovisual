package org.example.service.logins;

import lombok.RequiredArgsConstructor;
import org.example.model.Login;
import org.example.repositories.LoginRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginRepository loginRepository;

    /**
     * Buscar por token
     * @param token
     * @return Login
     */
    public Optional<Login> findByToken(String token) {
        return loginRepository.findByToken(token);
    }
}
