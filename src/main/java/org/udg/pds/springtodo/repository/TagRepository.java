package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.udg.pds.springtodo.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
