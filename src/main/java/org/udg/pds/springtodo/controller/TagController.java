package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.service.TagService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RequestMapping("/tags")
@Controller
public class TagController extends BaseController {

  @Autowired
  TagService tagService;

  @GetMapping("{id}")
  @ResponseBody
  public Tag getTag(HttpSession session,
                    @PathVariable("id") Long id) {

    getLoggedUser(session);
    return tagService.getTag(id);
  }

  @GetMapping
  @ResponseBody
  public Collection<Tag> listAllTags(HttpSession session) {

    Long userId = getLoggedUser(session);

    return tagService.getTags();
  }

  @PostMapping(consumes = "application/json")
  @ResponseBody
  public String addTag(@Valid @RequestBody R_Tag tag, HttpSession session) {

    Long userId = getLoggedUser(session);

    if (tag.description == null) {
      tag.description = "";
    }

    tagService.addTag(tag.name, tag.description);
    return BaseController.OK_MESSAGE;
  }

  @DeleteMapping(path="/{id}")
  @ResponseBody
  public String deleteTag(HttpSession session,
                            @PathVariable("id") Long tagId) {

    Long userId = getLoggedUser(session);

    tagService.crud().deleteById(tagId);
    return BaseController.OK_MESSAGE;
  }

  static class R_Tag {
    @NotNull
    public String name;

    public String description;
  }

}
