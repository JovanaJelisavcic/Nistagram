package com.XMLiWX.microservices.mediaservice.storage;

public class FileStorageException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 27687891274153226L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
