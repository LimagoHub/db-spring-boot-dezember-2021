package de.db.webapp.services;

public class SchweineServiceException extends ServiceException {
    public SchweineServiceException() {
    }

    public SchweineServiceException(String message) {
        super(message);
    }

    public SchweineServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchweineServiceException(Throwable cause) {
        super(cause);
    }

    public SchweineServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
