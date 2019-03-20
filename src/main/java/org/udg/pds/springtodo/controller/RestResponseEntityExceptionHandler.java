package org.udg.pds.springtodo.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
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

    // Overriding of predefined methods in ResponseEntityExceptionHandler
    // NOT ALL are overrided. Look ResponseEntityExceptionHandler

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        StringBuilder sb = new StringBuilder("Errors: ");
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            FieldError fe = (FieldError) error;
            if (fe != null) sb.append(String.format("[Field: %s, error: %s ] ", fe.getField(), fe.getDefaultMessage()));
        }

        return handleExceptionInternal(ex,
                new Error(Global.dateFormat.format(new Date()),
                        status.value(),
                        "Validation failed for body",
                        sb.toString()),
                headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return handleExceptionInternal(ex,
                new Error(Global.dateFormat.format(new Date()),
                        status.value(),
                        "Message not readable",
                        ex.getMessage()),
                headers, status, request);
    }

    // Other exceptions. Add your own exception handling here

    @ExceptionHandler({ Exception.class })
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

}