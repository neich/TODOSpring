package org.udg.pds.springtodo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
// This tells JAXB that it has to ignore getters and setters and only use fields for JSON marshaling/unmarshaling
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Task extends BaseEntity implements Serializable {

    @ManyToMany
    private final Collection<Tag> tags = new ArrayList<>();

    private ZonedDateTime dateCreated;

    private ZonedDateTime dateLimit;

    private Boolean completed;

    // This is needed because TEXT is a SQL reserved word
    @Column(name = "task_text")
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner")
    private User user;

    public Task(ZonedDateTime dateCreated, ZonedDateTime dateLimit, Boolean completed, String text) {
        this.dateCreated = dateCreated;
        this.dateLimit = dateLimit;
        this.completed = completed;
        this.text = text;
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }
}
