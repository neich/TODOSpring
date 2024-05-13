package org.udg.pds.springtodo.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tag extends BaseEntity implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String description;

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
