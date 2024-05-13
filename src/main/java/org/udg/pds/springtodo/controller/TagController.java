package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.dto.Tag.TagDto;
import org.udg.pds.springtodo.dto.Tag.TagMapper;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.service.TagService;

import java.util.Collection;

@RequestMapping("/tags")
@RestController
public class TagController extends BaseController {

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TagService tagService;

    @GetMapping("{id}")
    public TagDto getTag(HttpSession session,
                         @PathVariable("id") Long id) {

        getLoggedUser(session);
        return tagMapper.tagToTagDto(tagService.getTag(id));
    }

    @GetMapping
    public Collection<TagDto> listAllTags(HttpSession session) {

        Long userId = getLoggedUser(session);

        return tagMapper.map(tagService.getTags());
    }

    @PostMapping(consumes = "application/json")
    public TagDto addTag(@Valid @RequestBody R_Tag tag, HttpSession session) {

        Long userId = getLoggedUser(session);

        if (tag.description == null) {
            tag.description = "";
        }

        return tagMapper.tagToTagDto(tagService.addTag(tag.name, tag.description));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(HttpSession session,
                                    @PathVariable("id") Long tagId) {

        Long userId = getLoggedUser(session);

        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }

    static class R_Tag {
        @NotNull
        public String name;

        public String description;
    }

}
