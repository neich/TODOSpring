package org.udg.pds.springtodo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.controller.exceptions.ControllerException;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Error;

import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    public RestResponseEntityExceptionHandler() {
        super();
    }

    // Other exceptions. Add your own exception handling here

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {

        if (ex instanceof ServiceException) {
            return handleExceptionInternal(ex,
                new Error(Global.dateFormat.format(new Date()),
                    HttpStatus.BAD_REQUEST.value(),
                    "Service error",
                    ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else if (ex instanceof ControllerException) {
            return handleExceptionInternal(ex,
                new Error(Global.dateFormat.format(new Date()),
                    HttpStatus.BAD_REQUEST.value(),
                    "Controller error",
                    ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
        } else
            return handleExceptionInternal(ex,
                new Error(Global.dateFormat.format(new Date()),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "Unknown error",
                    ex.getMessage()),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if (body == null) {
            body = new Error(Global.dateFormat.format(new Date()), status.value(), "Unknown error", ex.getMessage());
        }
        return new ResponseEntity<>(body, headers, status);
    }

}
