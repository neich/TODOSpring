package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.Task;

@Component
public interface TaskRepository extends JpaRepository<Task, Long> {
}
