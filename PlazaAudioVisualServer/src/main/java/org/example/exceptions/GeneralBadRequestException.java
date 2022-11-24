package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GeneralBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción general de solicitud incorrecta
     * @param op
     * @param error
     */
    public GeneralBadRequestException(String op, String error) {
        super("Error al procesar: " + op + " : " + error);
    }
}
