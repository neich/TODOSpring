package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Objecte;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.repository.ObjectRepository;
import org.udg.pds.springtodo.repository.TagRepository;

import javax.swing.text.html.Option;
import java.rmi.server.ServerCloneException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ObjecteService {

    @Autowired
    ObjectRepository objRepository;

    public ObjectRepository crud() {
        return objRepository;
    }

    //OBTENIR UN OBJECTE
    public Objecte getObjecte(Long id){
        Optional<Objecte> optionalObjecte = objRepository.findById(id);

        if(!optionalObjecte.isPresent()){
            throw new ServiceException("Objecte inexistent");
        }
        else{
            return optionalObjecte.get();
        }
    }

    //AFEGIR OBJECTE
    public Objecte addObjecte(String nom, String descripcio){
        try {
            Objecte obj = new Objecte(nom, descripcio);
            objRepository.save(obj);
            return obj;
        }catch (Exception ex) {
            throw new ServiceException(ex.getMessage());
        }
    }

    public Collection<Objecte> getObjectes(){
        Collection<Tag> r = new ArrayList<>();

        return StreamSupport.stream(objRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }



}
