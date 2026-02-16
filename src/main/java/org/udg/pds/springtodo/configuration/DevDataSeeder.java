package org.udg.pds.springtodo.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.udg.pds.springtodo.dto.TagRequest;
import org.udg.pds.springtodo.model.Tag;
import org.udg.pds.springtodo.model.User;
import org.udg.pds.springtodo.repository.TagRepository;
import org.udg.pds.springtodo.repository.TaskRepository;
import org.udg.pds.springtodo.repository.UserRepository;
import org.udg.pds.springtodo.service.TagService;
import org.udg.pds.springtodo.service.TaskService;
import org.udg.pds.springtodo.service.UserService;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Component
@Profile("dev")
public class DevDataSeeder implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DevDataSeeder.class);

    private final UserService userService;
    private final TaskService taskService;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    public DevDataSeeder(UserService userService, TaskService taskService, TagService tagService,
                         UserRepository userRepository, TaskRepository taskRepository,
                         TagRepository tagRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public void run(String... args) {
        logger.info("Clearing database ...");
        taskRepository.deleteAll();
        tagRepository.deleteAll();
        userRepository.deleteAll();

        logger.info("Starting populating database ...");

        User user = userService.registerEntity("usuari", "usuari@hotmail.com", "123456");
        Long taskId = taskService.addTaskEntity("Una tasca", user.getId(),
            ZonedDateTime.now(), ZonedDateTime.now());
        Tag tag = tagService.getTagEntity(
            tagService.addTag(new TagRequest("ATag", "Just a tag")).id());
        taskService.addTagsToTask(user.getId(), taskId, new ArrayList<>() {{
            add(tag.getId());
        }});
        userService.registerEntity("user", "user@hotmail.com", "0000");
    }
}
