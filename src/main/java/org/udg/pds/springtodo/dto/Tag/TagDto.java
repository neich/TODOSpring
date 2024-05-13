package org.udg.pds.springtodo.dto.Tag;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class TagDto {
    private Long id;
    private String description;
}
