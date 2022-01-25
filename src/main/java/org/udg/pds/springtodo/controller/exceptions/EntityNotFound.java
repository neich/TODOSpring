package org.udg.pds.springtodo.controller.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such entity")  // 404
public class EntityNotFound extends RuntimeException {
}
