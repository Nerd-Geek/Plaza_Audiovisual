package org.example.exceptions.login;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginBadRequestException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     * excepción de solicitud incorrecta
     * @param campo
     * @param error
     */
    public LoginBadRequestException(String campo, String error) {
        super("Existe un error en el campo: " + campo + " Error: " + error);
    }
}
