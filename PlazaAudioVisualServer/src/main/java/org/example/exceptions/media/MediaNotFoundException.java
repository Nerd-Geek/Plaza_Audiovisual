package org.example.exceptions.media;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MediaNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción no encontrada
     */
    public MediaNotFoundException() {
        super("La lista de logins está vacía o no existe");
    }
}
