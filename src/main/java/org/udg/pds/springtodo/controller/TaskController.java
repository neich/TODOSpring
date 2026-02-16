package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.udg.pds.springtodo.dto.IdDto;
import org.udg.pds.springtodo.dto.TagDto;
import org.udg.pds.springtodo.dto.TaskDto;
import org.udg.pds.springtodo.dto.TaskFullDto;
import org.udg.pds.springtodo.dto.TaskRequest;
import org.udg.pds.springtodo.service.TaskService;

import java.net.URI;
import java.util.Collection;
import java.util.List;

@RequestMapping(path = "/tasks")
@RestController
public class TaskController extends BaseController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<TaskFullDto> getTask(HttpSession session,
                                               @PathVariable("id") Long id) {
        Long userId = getLoggedUser(session);
        return ResponseEntity.ok(taskService.getTask(userId, id));
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> listAllTasks(HttpSession session) {
        Long userId = getLoggedUser(session);
        return ResponseEntity.ok(taskService.getTasks(userId));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<IdDto> addTask(HttpSession session,
                                         @Valid @RequestBody TaskRequest request) {
        Long userId = getLoggedUser(session);
        IdDto created = taskService.addTask(request, userId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTask(HttpSession session,
                                           @PathVariable("id") Long taskId) {
        Long userId = getLoggedUser(session);
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/tags")
    public ResponseEntity<Void> addTags(HttpSession session,
                                        @PathVariable("id") Long taskId,
                                        @RequestBody Collection<Long> tags) {
        Long userId = getLoggedUser(session);
        taskService.addTagsToTask(userId, taskId, tags);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/tags")
    public ResponseEntity<List<TagDto>> getTaskTags(HttpSession session,
                                                    @PathVariable("id") Long taskId) {
        Long userId = getLoggedUser(session);
        return ResponseEntity.ok(taskService.getTaskTags(userId, taskId));
    }
}
