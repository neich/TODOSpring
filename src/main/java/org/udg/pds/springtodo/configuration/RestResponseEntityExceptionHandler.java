package org.udg.pds.springtodo.configuration;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.configuration.exceptions.ControllerException;
import org.udg.pds.springtodo.configuration.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Error;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatusCode status, @NotNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        Error error = new Error(Global.AppDateFormatter.format(ZonedDateTime.now()),
            HttpStatus.BAD_REQUEST.value(),
            "Service error",
            errors.toString());
        return handleExceptionInternal(
            ex, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
        HttpMediaTypeNotSupportedException ex,
        @NotNull HttpHeaders headers,
        @NotNull HttpStatusCode status,
        @NotNull WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

        Error apiError = new Error(Global.AppDateFormatter.format(ZonedDateTime.now()),
            HttpStatus.BAD_REQUEST.value(),
            builder.toString(),
            ex.getMessage());
        return new ResponseEntity<>(
            apiError, new HttpHeaders(), apiError.status);
    }
    // Other exceptions. Add your own exception handling here

    @ExceptionHandler({ServiceException.class})
    protected ResponseEntity<Object> handleServiceExceptions(ServiceException ex, WebRequest request) {
        return handleExceptionInternal(ex,
            new Error(Global.AppDateFormatter.format(ZonedDateTime.now()),
                HttpStatus.BAD_REQUEST.value(),
                "Service error",
                ex.getMessage()),
            new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ControllerException.class})
    protected ResponseEntity<Object> handleControllerExceptions(ControllerException ex, WebRequest request) {
        return new ResponseEntity<Object>(new Error(Global.AppDateFormatter.format(ZonedDateTime.now()),
            HttpStatus.BAD_REQUEST.value(),
            "Controller error",
            ex.getMessage()),
            new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        return new ResponseEntity<>(new Error(Global.AppDateFormatter.format(ZonedDateTime.now()),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Unknown error",
            ex.getMessage()), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(statusCode)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
        }
        if (body == null) {
            body = new Error(Global.AppDateFormatter.format(ZonedDateTime.now()), statusCode.value(), "Unknown error", ex.getMessage());
        }
        return new ResponseEntity<>(body, headers, statusCode);
    }

}
