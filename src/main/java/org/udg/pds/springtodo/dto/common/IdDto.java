package org.udg.pds.springtodo.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for returning resource IDs
 */
public record IdDto(
    @JsonProperty("id")
    @NotNull
    Long id
) {
}
