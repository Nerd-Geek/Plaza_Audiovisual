package org.example.exceptions.user;

public class NewUserWithDifferentPasswordsException extends RuntimeException{
    private static final long serialVersionUID = -7978601526802035152L;

    /**
     * Error las constraseñas no coinciden
     */
    public NewUserWithDifferentPasswordsException() {
        super("Las contraseñas no coinciden");
    }
}
