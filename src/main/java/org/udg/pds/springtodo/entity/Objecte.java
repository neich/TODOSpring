package org.udg.pds.springtodo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.udg.pds.springtodo.serializer.JsonTagSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@JsonSerialize(using = JsonTagSerializer.class)
@Entity(name = "Objectes")
public class Objecte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String descripcio;

    public Objecte(String nom, String descripcio) {
        this.nom = nom;
        this.descripcio = descripcio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    //RELACIÓ 0.. - * AMB PERFIL
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<Objecte> objectes;

    /*
    //RELACIÓ 1 - 0..1 AMB SUBHASTA
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Subhasta propietari;*/

}


