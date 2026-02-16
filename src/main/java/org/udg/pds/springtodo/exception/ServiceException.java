package org.udg.pds.springtodo.exception;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }
}
