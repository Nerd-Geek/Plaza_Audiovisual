package org.example.exceptions.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsersNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * No se encuentra al usuario
     */
    public UsersNotFoundException() {
        super("La lista de users está vacía o no existe");
    }
}
