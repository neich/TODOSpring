package org.udg.pds.springtodo.dto;

import java.time.ZonedDateTime;

public record TaskDto(
    Long id,
    ZonedDateTime dateCreated,
    ZonedDateTime dateLimit,
    Boolean completed,
    String text,
    Long userId
) {
}
