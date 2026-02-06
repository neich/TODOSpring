package org.udg.pds.springtodo.entity;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
public class Tag extends BaseEntity implements Serializable {

    @NotNull
    private String name;

    @NotNull
    private String description;

    public Tag() {
    }

    public Tag(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
