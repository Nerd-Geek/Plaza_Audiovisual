package org.example.exceptions.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoginNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción no encontrada
     * @param id
     */
    public LoginNotFoundException(String id) {
        super("No se ha encontrado el login con la ID: " + id);
    }
}
