package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.udg.pds.springtodo.dto.TagDto;
import org.udg.pds.springtodo.dto.TagRequest;
import org.udg.pds.springtodo.service.TagService;

import java.net.URI;
import java.util.List;

@RequestMapping("/tags")
@RestController
public class TagController extends BaseController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("{id}")
    public ResponseEntity<TagDto> getTag(HttpSession session,
                                         @PathVariable("id") Long id) {
        getLoggedUser(session);
        return ResponseEntity.ok(tagService.getTag(id));
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> listAllTags(HttpSession session) {
        getLoggedUser(session);
        return ResponseEntity.ok(tagService.getTags());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<TagDto> addTag(HttpSession session,
                                         @Valid @RequestBody TagRequest request) {
        getLoggedUser(session);
        TagDto created = tagService.addTag(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTag(HttpSession session,
                                          @PathVariable("id") Long tagId) {
        getLoggedUser(session);
        tagService.deleteTag(tagId);
        return ResponseEntity.noContent().build();
    }
}
