package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity implements Serializable {

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @NotNull
  @Column(unique = true)
  private String username;

  @NotNull
  @Column(unique = true)
  private String email;

  @NotNull
  private String password;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Collection<Task> tasks = new ArrayList<>();

  public void addTask(Task task) {
    tasks.add(task);
  }

}
