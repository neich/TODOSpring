package org.udg.pds.springtodo.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)  // 404
public class ControllerException extends RuntimeException {
    public ControllerException(String message) {

        super(message);
    }
}
