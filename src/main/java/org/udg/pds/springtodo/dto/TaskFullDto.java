package org.udg.pds.springtodo.dto;

import java.time.ZonedDateTime;
import java.util.Collection;

public record TaskFullDto(
    Long id,
    ZonedDateTime dateCreated,
    ZonedDateTime dateLimit,
    Boolean completed,
    String text,
    UserDto user,
    Collection<String> tags
) {
}
