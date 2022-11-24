package org.example.exceptions.media;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MediaBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción de solicitud incorrecta
     * @param campo
     * @param error
     */
    public MediaBadRequestException(String campo, String error) {
        super("Existe un error en el campo: " + campo + " Error: " + error);
    }
}
