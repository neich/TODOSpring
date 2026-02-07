package org.udg.pds.springtodo.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Entity
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

    public Task() {
    }

    public Task(ZonedDateTime dateCreated, ZonedDateTime dateLimit, Boolean completed, String text) {
        this.dateCreated = dateCreated;
        this.dateLimit = dateLimit;
        this.completed = completed;
        this.text = text;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(ZonedDateTime dateLimit) {
        this.dateLimit = dateLimit;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public Collection<Tag> getTags() {
        return tags;
    }
}
