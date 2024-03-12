package org.udg.pds.springtodo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Task;
import org.udg.pds.springtodo.entity.Views;
import org.udg.pds.springtodo.service.TaskService;

import java.util.Collection;
import java.util.Date;

@RequestMapping(path = "/tasks")
@RestController
public class TaskController extends BaseController {

    @Autowired
    TaskService taskService;

    @GetMapping(path = "/{id}")
    public Task getTask(HttpSession session,
                        @PathVariable("id") Long id) {
        Long userId = getLoggedUser(session);

        return taskService.getTask(userId, id);
    }

    @GetMapping
    @JsonView(Views.Private.class)
    public Collection<Task> listAllTasks(HttpSession session,
                                         @RequestParam(value = "from", required = false) Date from) {
        Long userId = getLoggedUser(session);

        return taskService.getTasks(userId);
    }

    @PostMapping(consumes = "application/json")
    public IdObject addTask(HttpSession session, @Valid @RequestBody R_Task task) {

        Long userId = getLoggedUser(session);

        return taskService.addTask(task.text, userId, task.dateCreated, task.dateLimit);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteTask(HttpSession session,
                             @PathVariable("id") Long taskId) {
        Long userId = getLoggedUser(session);
        taskService.deleteTask(userId, taskId);
        return BaseController.OK_MESSAGE;
    }

    @PostMapping(path = "/{id}/tags")
    public String addTags(@RequestBody Collection<Long> tags, HttpSession session,
                          @PathVariable("id") Long taskId) {

        Long userId = getLoggedUser(session);
        taskService.addTagsToTask(userId, taskId, tags);
        return BaseController.OK_MESSAGE;
    }

    @GetMapping(path = "/{id}/tags")
    public Collection<Tag> getTaskTags(HttpSession session,
                                       @PathVariable("id") Long taskId) {

        Long userId = getLoggedUser(session);
        return taskService.getTaskTags(userId, taskId);
    }

    static class R_Task {

        @NotNull
        public String text;

        @NotNull
        public String dateCreated;

        @NotNull
        public String dateLimit;
    }

}
