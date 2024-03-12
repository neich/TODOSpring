package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.compress.changes.ChangeSet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "username"}))
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class,
    property = "id", scope = User.class)
public class User implements Serializable {
  /**
   * Default value included to remove warning. Remove or modify at will. *
   */
  private static final long serialVersionUID = 1L;
    public User() {
  }

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.tasks = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private String username;

  @NotNull
  private String email;

  @NotNull
  private String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Collection<Group> owned_groups;

    @JsonView(Views.Private.class)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @JsonView(Views.Private.class)
  public String getEmail() {
    return email;
  }

  @JsonView(Views.Public.class)
  public String getUsername() {
    return username;
  }

  @JsonIgnore
  public String getPassword() {
    return password;
  }

  @JsonView(Views.Complete.class)
  public Collection<Task> getTasks() {
    // Since tasks is collection controlled by JPA, it has LAZY loading by default. That means
    // that you have to query the object (calling size(), for example) to get the list initialized
    // More: http://www.javabeat.net/jpa-lazy-eager-loading/
    tasks.size();
    return tasks;
  }

    public void addTask(Task task) {
    tasks.add(task);
  }

    @JsonView(Views.Complete.class)
    public Collection<Group> getOwnedGroups() {
        owned_groups.size();
        return owned_groups;
    }
}
