package de.db.webapp.services;

public class PersonenServiceException extends ServiceException {


    public PersonenServiceException() {
    }

    public PersonenServiceException(String message) {
        super(message);
    }

    public PersonenServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonenServiceException(Throwable cause) {
        super(cause);
    }

    public PersonenServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
