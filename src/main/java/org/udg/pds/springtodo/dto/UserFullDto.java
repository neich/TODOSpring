package org.udg.pds.springtodo.dto;

public record UserFullDto(
    Long id,
    String username,
    String email
) {
}
