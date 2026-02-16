package org.udg.pds.springtodo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

public record TaskRequest(
    @NotBlank(message = "Task text is required")
    String text,

    @NotNull(message = "Creation date is required")
    @DateTimeFormat
    ZonedDateTime dateCreated,

    @NotNull(message = "Limit date is required")
    @DateTimeFormat
    ZonedDateTime dateLimit
) {
}
