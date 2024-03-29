package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.service.TagService;

import java.util.Collection;

@RequestMapping("/tags")
@RestController
public class TagController extends BaseController {

    @Autowired
    TagService tagService;

    @GetMapping("{id}")
    public Tag getTag(HttpSession session,
                      @PathVariable("id") Long id) {

        getLoggedUser(session);
        return tagService.getTag(id);
    }

    @GetMapping
    public Collection<Tag> listAllTags(HttpSession session) {

        Long userId = getLoggedUser(session);

        return tagService.getTags();
    }

    @PostMapping(consumes = "application/json")
    public String addTag(@Valid @RequestBody R_Tag tag, HttpSession session) {

        Long userId = getLoggedUser(session);

        if (tag.description == null) {
            tag.description = "";
        }

        tagService.addTag(tag.name, tag.description);
        return BaseController.OK_MESSAGE;
    }

    @DeleteMapping(path = "/{id}")
    public String deleteTag(HttpSession session,
                            @PathVariable("id") Long tagId) {

        Long userId = getLoggedUser(session);

        tagService.deleteTag(tagId);
        return BaseController.OK_MESSAGE;
    }

    static class R_Tag {
        @NotNull
        public String name;

        public String description;
    }

}
