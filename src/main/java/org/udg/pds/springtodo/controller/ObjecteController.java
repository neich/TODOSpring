package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Objecte;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.service.ObjecteService;
import org.udg.pds.springtodo.service.TagService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping("/objecte")
@RestController
public class ObjecteController extends BaseController {

    @Autowired
    ObjecteService objService;

    @GetMapping("{id}")
    public Objecte getObjecte(HttpSession session, @PathVariable("id") Long id){

        getLoggedUser(session);

        return objService.getObjecte(id);
    }

    @GetMapping
    public Collection<Objecte> listAllObjectes(HttpSession session){

        getLoggedUser(session);
        return objService.getObjectes();
    }

    @PostMapping(consumes = "application/json")
    public String addObjecte(@Valid @RequestBody R_Objecte objecte, HttpSession session){

        getLoggedUser(session);

        if(objecte.descripcio == null) objecte.descripcio = " ";

        objService.addObjecte(objecte.nom, objecte.descripcio);

        return BaseController.OK_MESSAGE;

    }


    @DeleteMapping(path="/{id}")
    public String deleteObjecte(@PathVariable("id") Long objID, HttpSession session){

        getLoggedUser(session);

        objService.crud().deleteById(objID);

        return BaseController.OK_MESSAGE;
    }

    static class R_Objecte{
        public String nom;
        public String descripcio;
    }


}
