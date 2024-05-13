package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.dto.Tag.TagDto;
import org.udg.pds.springtodo.dto.Tag.TagMapper;
import org.udg.pds.springtodo.dto.Task.TaskDto;
import org.udg.pds.springtodo.dto.Task.TaskFullDto;
import org.udg.pds.springtodo.dto.Task.TaskMapper;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.service.TaskService;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;

@RequestMapping(path = "/tasks")
@RestController
public class TaskController extends BaseController {

    @Autowired
    TaskMapper taskMapper;

    @Autowired
    TagMapper tagMapper;

    @Autowired
    TaskService taskService;

    @GetMapping(path = "/{id}")
    public TaskFullDto getTask(HttpSession session,
                               @PathVariable("id") Long id) {
        Long userId = getLoggedUser(session);

        return taskMapper.taskToTaskFullDto(taskService.getTask(userId, id));
    }

    @GetMapping
    public Collection<TaskDto> listAllTasks(HttpSession session,
                                         @RequestParam(value = "from", required = false) Date from) {
        Long userId = getLoggedUser(session);

        return taskMapper.map(taskService.getTasks(userId));
    }

    @PostMapping(consumes = "application/json")
    public IdObject addTask(HttpSession session, @Valid @RequestBody R_Task task) {

        Long userId = getLoggedUser(session);

        return taskService.addTask(task.text, userId, task.dateCreated, task.dateLimit);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteTask(HttpSession session,
                                     @PathVariable("id") Long taskId) {
        Long userId = getLoggedUser(session);
        taskService.deleteTask(userId, taskId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}/tags")
    public ResponseEntity<Void> addTags(@RequestBody Collection<Long> tags, HttpSession session,
                          @PathVariable("id") Long taskId) {

        Long userId = getLoggedUser(session);
        taskService.addTagsToTask(userId, taskId, tags);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(path = "/{id}/tags")
    public Collection<TagDto> getTaskTags(HttpSession session,
                                          @PathVariable("id") Long taskId) {

        Long userId = getLoggedUser(session);
        return tagMapper.map(taskService.getTaskTags(userId, taskId));
    }

    static class R_Task {

        @NotNull
        public String text;

        @NotNull
        public ZonedDateTime dateCreated;

        @NotNull
        public ZonedDateTime dateLimit;
    }

}
