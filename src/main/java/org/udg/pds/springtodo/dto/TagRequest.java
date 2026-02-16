package org.udg.pds.springtodo.dto;

import jakarta.validation.constraints.NotBlank;

public record TagRequest(
    @NotBlank(message = "Tag name is required")
    String name,

    String description
) {
}
