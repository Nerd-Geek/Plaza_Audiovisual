package org.example.exceptions.media;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MediaNotFountException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción no encontrada
     * @param id
     */
    public MediaNotFountException(String id) {
        super("No se ha encontrado el service con la ID: " + id);
    }
}
