package org.example.exceptions.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LoginsNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción no encontrada
     */
    public LoginsNotFoundException() {
        super("La lista de logins está vacía o no existe");
    }
}