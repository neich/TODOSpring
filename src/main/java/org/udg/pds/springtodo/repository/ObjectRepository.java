package org.udg.pds.springtodo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.entity.Objecte;
import org.udg.pds.springtodo.entity.Tag;

import java.util.List;

@Component
public interface ObjectRepository extends CrudRepository<Objecte, Long>{
}
