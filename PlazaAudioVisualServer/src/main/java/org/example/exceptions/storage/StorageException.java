package org.example.exceptions.storage;

public class StorageException extends RuntimeException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     *
     * @param message
     */
    public StorageException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
