package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.repository.TagRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TagService {

    @Autowired
    TagRepository tagRepository;

    public TagRepository crud() {
        return tagRepository;
    }

    public Tag getTag(Long id) {
        Optional<Tag> ot = tagRepository.findById(id);
        if (!ot.isPresent())
            throw new ServiceException("Tag does not exists");
        else
            return ot.get();
    }

    public Tag addTag(String name, String description) {
        try {
            Tag tag = new Tag(name, description);

            tagRepository.save(tag);
            return tag;
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an EJBException
            // We catch the normal exception and then transform it in a EJBException
            throw new ServiceException(ex.getMessage());
        }
    }

    public Collection<Tag> getTags() {
        Collection<Tag> r = new ArrayList<>();

        return StreamSupport.stream(tagRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
