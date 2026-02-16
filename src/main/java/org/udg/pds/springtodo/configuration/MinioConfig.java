package org.udg.pds.springtodo.configuration;

import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    private static final Logger logger = LoggerFactory.getLogger(MinioConfig.class);

    @Value("${todospring.minio.url:}")
    private String minioURL;

    @Value("${todospring.minio.access-key:}")
    private String minioAccessKey;

    @Value("${todospring.minio.secret-key:}")
    private String minioSecretKey;

    @Bean
    @ConditionalOnProperty(prefix = "todospring.minio", name = {"url", "bucket"})
    public MinioClient minioClient() {
        logger.info("Starting MinIO connection to URL: {}", minioURL);
        try {
            return MinioClient.builder()
                .endpoint(minioURL)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
        } catch (Exception e) {
            logger.warn("Cannot initialize MinIO client with url: {}, access-key: {}, secret-key: {}",
                minioURL, minioAccessKey, minioSecretKey);
            throw new IllegalStateException("Failed to initialize MinIO client", e);
        }
    }

}
