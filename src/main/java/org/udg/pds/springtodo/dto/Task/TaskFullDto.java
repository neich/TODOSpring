package org.udg.pds.springtodo.dto.Task;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Collection;

@Getter
@Setter
public class TaskFullDto {
    private Long id;

    private Long ownerId;

    private ZonedDateTime dateCreated;

    private ZonedDateTime dateLimit;

    private Boolean completed;

    private String text;

    private Collection<String> tags;
}
