package org.udg.pds.springtodo.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 404
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {

        super(message);
    }
}
