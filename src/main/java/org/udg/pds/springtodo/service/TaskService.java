package org.udg.pds.springtodo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.dto.IdDto;
import org.udg.pds.springtodo.dto.TagDto;
import org.udg.pds.springtodo.dto.TaskDto;
import org.udg.pds.springtodo.dto.TaskFullDto;
import org.udg.pds.springtodo.dto.TaskRequest;
import org.udg.pds.springtodo.exception.ResourceNotFoundException;
import org.udg.pds.springtodo.exception.ServiceException;
import org.udg.pds.springtodo.mapper.TagMapper;
import org.udg.pds.springtodo.mapper.TaskMapper;
import org.udg.pds.springtodo.model.Tag;
import org.udg.pds.springtodo.model.Task;
import org.udg.pds.springtodo.model.User;
import org.udg.pds.springtodo.repository.TaskRepository;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TagService tagService;
    private final TaskMapper taskMapper;
    private final TagMapper tagMapper;

    public TaskService(TaskRepository taskRepository, UserService userService,
                       TagService tagService, TaskMapper taskMapper, TagMapper tagMapper) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.tagService = tagService;
        this.taskMapper = taskMapper;
        this.tagMapper = tagMapper;
    }

    @Transactional(readOnly = true)
    public List<TaskDto> getTasks(Long userId) {
        User user = userService.getUserEntity(userId);
        return taskMapper.toTaskDtoList(user.getTasks());
    }

    @Transactional(readOnly = true)
    public TaskFullDto getTask(Long userId, Long id) {
        Task task = getTaskEntity(userId, id);
        return taskMapper.taskToTaskFullDto(task);
    }

    @Transactional
    public IdDto addTask(TaskRequest request, Long userId) {
        User user = userService.getUserEntity(userId);
        Task task = new Task(request.dateCreated(), request.dateLimit(), false, request.text());
        task.setUser(user);
        user.addTask(task);
        taskRepository.save(task);
        return new IdDto(task.getId());
    }

    @Transactional
    public void addTagsToTask(Long userId, Long taskId, Collection<Long> tags) {
        Task task = getTaskEntity(userId, taskId);

        for (Long tagId : tags) {
            Tag tag = tagService.getTagEntity(tagId);
            task.addTag(tag);
        }
    }

    @Transactional(readOnly = true)
    public List<TagDto> getTaskTags(Long userId, Long id) {
        Task task = getTaskEntity(userId, id);
        return tagMapper.toTagDtoList(task.getTags());
    }

    @Transactional
    public void deleteTask(Long userId, Long taskId) {
        Task task = getTaskEntity(userId, taskId);
        taskRepository.delete(task);
    }

    @Transactional
    public Long addTaskEntity(String text, Long userId,
                              ZonedDateTime created, ZonedDateTime limit) {
        User user = userService.getUserEntity(userId);
        Task task = new Task(created, limit, false, text);
        task.setUser(user);
        user.addTask(task);
        taskRepository.save(task);
        return task.getId();
    }

    private Task getTaskEntity(Long userId, Long id) {
        Task task = taskRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Task does not exist"));
        if (!Objects.equals(task.getUser().getId(), userId)) {
            throw new ServiceException("User does not own this task");
        }
        return task;
    }
}
