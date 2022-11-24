package org.example.exceptions.user;

public class UserNotFoundByEmailException extends RuntimeException{
    private static final long serialVersionUID = 86546786467580534L;

    /**
     * No se encuentra el email
     * @param email
     */
    public UserNotFoundByEmailException(String email) {
        super("No se ha encontrado el user con el email: " + email);
    }
}
