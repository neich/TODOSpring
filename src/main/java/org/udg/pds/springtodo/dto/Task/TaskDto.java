package org.udg.pds.springtodo.dto.Task;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TaskDto {

    private Long id;

    private ZonedDateTime dateCreated;

    private ZonedDateTime dateLimit;

    private Boolean completed;

    private String text;

    private Long userId;
}
