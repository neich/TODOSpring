package org.udg.pds.springtodo;

import io.minio.MinioClient;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
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

@Service
public class Global {

    private final Logger logger = LoggerFactory.getLogger(Global.class);

    private final UserService userService;
    private final TaskService taskService;
    private final TagService tagService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TagRepository tagRepository;

    private MinioClient minioClient;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Value("${todospring.minio.url:}")
    private String minioURL;

    @Value("${todospring.minio.access-key:}")
    private String minioAccessKey;

    @Value("${todospring.minio.secret-key:}")
    private String minioSecretKey;

    @Value("${todospring.minio.bucket:}")
    private String minioBucket;

    @Value("${todospring.base-url:#{null}}")
    private String baseUrl;

    @Value("${todospring.base-port:8080}")
    private String basePort;

    public Global(UserService userService, TaskService taskService, TagService tagService,
                  UserRepository userRepository, TaskRepository taskRepository,
                  TagRepository tagRepository) {
        this.userService = userService;
        this.taskService = taskService;
        this.tagService = tagService;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.tagRepository = tagRepository;
    }

    @PostConstruct
    void init() {
        logger.info(String.format("Starting Minio connection to URL: %s", minioURL));
        try {
            minioClient = MinioClient.builder()
                .endpoint(minioURL)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
        } catch (Exception e) {
            logger.warn("Cannot initialize minio service with url:" + minioURL
                + ", access-key:" + minioAccessKey + ", secret-key:" + minioSecretKey);
        }

        if (minioBucket.isEmpty()) {
            logger.warn("Cannot initialize minio bucket: " + minioBucket);
            minioClient = null;
        }

        if (baseUrl == null) baseUrl = "http://localhost";
        baseUrl += ":" + basePort;

        initData();
    }

    private void initData() {
        if (activeProfile.equals("dev")) {
            logger.info("Clearing database ...");
            taskRepository.deleteAll();
            tagRepository.deleteAll();
            userRepository.deleteAll();

            logger.info("Starting populating database ...");

            User user = userService.registerEntity("usuari", "usuari@hotmail.com", "123456");
            Long taskId = taskService.addTaskEntity("Una tasca", user.getId(),
                ZonedDateTime.now(), ZonedDateTime.now());
            Tag tag = tagService.getTagEntity(
                tagService.addTag(new org.udg.pds.springtodo.dto.TagRequest("ATag", "Just a tag")).id());
            taskService.addTagsToTask(user.getId(), taskId, new ArrayList<>() {{
                add(tag.getId());
            }});
            userService.registerEntity("user", "user@hotmail.com", "0000");
        }
    }

    public String getBaseURL() {
        return baseUrl;
    }

    public MinioClient getMinioClient() {
        return minioClient;
    }

    public String getMinioBucket() {
        return minioBucket;
    }
}
