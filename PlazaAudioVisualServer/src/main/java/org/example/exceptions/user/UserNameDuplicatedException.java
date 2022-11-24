package org.example.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserNameDuplicatedException extends RuntimeException{
    private static final long serialVersionUID = 86546786467580534L;

    /**
     * Nombre de usuario duplicado
     */
    public UserNameDuplicatedException() {
        super("El nombre de usuario esta duplicado");
    }
}
