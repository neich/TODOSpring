package org.udg.pds.springtodo;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.entity.Tag;
import org.udg.pds.springtodo.entity.User;
import org.udg.pds.springtodo.service.TagService;
import org.udg.pds.springtodo.service.TaskService;
import org.udg.pds.springtodo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;

@Service
public class Global {
    private MinioClient minioClient;
    private String minioBucket;
    private String BASE_URL;

    private Logger logger = LoggerFactory.getLogger(Global.class);

    @Autowired
    private
    UserService userService;

    @Autowired
    private
    TaskService taskService;

    @Autowired
    private
    TagService tagService;

    @PostConstruct
    void init() {
        String minioURL = null;
        String minioAccessKey = null;
        String minioSecretKey = null;

        logger.info("Starting Minio connection ...");
        try {
            minioURL = System.getProperty("swarm.project.minio.url");
            minioAccessKey = System.getProperty("swarm.project.minio.access-key");
            minioSecretKey = System.getProperty("swarm.project.minio.secret-key");
            minioClient = new MinioClient(minioURL, minioAccessKey, minioSecretKey);
            minioBucket = System.getProperty("swarm.project.minio.bucket");
        } catch (Exception e) {
            logger.warn("Cannot initialize minio service with url:" + minioURL + ", access-key:" + minioAccessKey + ", secret-key:" + minioSecretKey);
        }

        if (minioBucket == null) {
            logger.warn("Cannot initialize minio bucket: " + minioBucket);
            minioClient = null;
        }

        if (System.getProperty("swarm.project.base-url") != null)
            BASE_URL = System.getProperty("swarm.project.base-url");
        else {
            String port = System.getProperty("swarm.http.port") != null ? System.getProperty("swarm.http.port") : "8080";
            BASE_URL = "http://localhost:" + port;
        }

        initData();
    }

    private void initData() {
        logger.info("Starting populating database ...");
        User user = userService.register("usuari", "usuari@hotmail.com", "123456");
        IdObject taskId = taskService.addTask("Una tasca", user.getId(), new Date(), new Date());
        Tag tag = tagService.addTag("ATag", "Just a tag");
        taskService.addTagsToTask(user.getId(), taskId.getId(), new ArrayList<Long>() {{
            add(tag.getId());
        }});
    }

    public MinioClient getMinioClient() {
        return minioClient;
    }

    public String getMinioBucket() {
        return minioBucket;
    }

    public String getBaseURL() {
        return BASE_URL;
    }
}
