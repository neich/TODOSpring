package org.udg.pds.springtodo.dto.Task;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Collection;

import org.udg.pds.springtodo.dto.User.UserDto;

@Getter
@Setter
public class TaskFullDto {

    private Long id;

    private ZonedDateTime dateCreated;

    private ZonedDateTime dateLimit;

    private Boolean completed;

    private String text;

    private UserDto user;

    private Collection<String> tags;
}
