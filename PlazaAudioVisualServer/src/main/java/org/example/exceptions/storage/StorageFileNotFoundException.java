package org.example.exceptions.storage;

public class StorageFileNotFoundException extends StorageException {
    private static final long serialVersionUID = 86546786467580532L;

    /**
     *
     * @param message
     */
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    /**
     *
     * @param message
     * @param cause
     */
    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}