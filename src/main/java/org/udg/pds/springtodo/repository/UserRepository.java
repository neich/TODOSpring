package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.User;

import java.util.List;

@Component
public interface UserRepository extends CrudRepository<User, Long> {
    @Query("SELECT u FROM users u WHERE u.username=:username")
    List<User> findByUsername(@Param("username") String username);

    @Query("SELECT u FROM users u WHERE u.email=:email")
    List<User> findByEmail(@Param("email") String email);

}
