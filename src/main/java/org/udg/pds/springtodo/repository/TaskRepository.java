package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.udg.pds.springtodo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
