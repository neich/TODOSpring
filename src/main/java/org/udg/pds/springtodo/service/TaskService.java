package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.Task;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.repository.TaskRepository;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    protected TagService tagService;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserService userService;

    public TaskRepository crud() {
        return taskRepository;
    }

    public Collection<Task> getTasks(Long id) {
        return userService.getUser(id).getTasks();
    }

    public Task getTask(Long userId, Long id) {
        Optional<Task> t = taskRepository.findById(id);
        if (!t.isPresent()) throw new ServiceException("Task does not exists");
        if (t.get().getUser().getId() != userId)
            throw new ServiceException("User does not own this task");
        return t.get();
    }

    @Transactional
    public IdObject addTask(String text, Long userId,
                            Date created, Date limit) {
        try {
            User user = userService.getUser(userId);

            Task task = new Task(created, limit, false, text);

            task.setUser(user);

            user.addTask(task);

            taskRepository.save(task);
            return new IdObject(task.getId());
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }
    }

    @Transactional
    public void addTagsToTask(Long userId, Long taskId, Collection<Long> tags) {
        Task t = this.getTask(userId, taskId);

        if (t.getUser().getId() != userId)
            throw new ServiceException("This user is not the owner of the task");

        try {
            for (Long tagId : tags) {
                Tag tag = tagService.getTag(tagId);
                t.addTag(tag);
            }
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }
    }

    public Collection<Tag> getTaskTags(Long userId, Long id) {
        Task t = this.getTask(userId, id);
        User u = t.getUser();

        if (u.getId() != userId)
            throw new ServiceException("Logged user does not own the task");

        return t.getTags();
    }

}
