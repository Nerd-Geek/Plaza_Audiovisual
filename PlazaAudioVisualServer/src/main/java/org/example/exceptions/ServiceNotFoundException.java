package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ServiceNotFoundException extends RuntimeException {

    /**
     * El servicio no funciona
     * @param id
     */
    public ServiceNotFoundException(String id) {
        super("Could not find service " + id);
    }
}
