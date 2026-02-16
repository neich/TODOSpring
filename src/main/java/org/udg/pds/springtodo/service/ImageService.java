package org.udg.pds.springtodo.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.udg.pds.springtodo.dto.ImageResponse;
import org.udg.pds.springtodo.exception.ServiceException;

import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService {

    private final Optional<MinioClient> minioClient;
    private final String minioBucket;
    private final String baseUrl;

    public ImageService(Optional<MinioClient> minioClient,
                        @Value("${todospring.minio.bucket:}") String minioBucket,
                        @Value("${todospring.base-url:http://localhost}") String baseUrl,
                        @Value("${todospring.base-port:8080}") String basePort) {
        this.minioClient = minioClient;
        this.minioBucket = minioBucket;
        this.baseUrl = baseUrl + ":" + basePort;
    }

    public ImageResponse upload(MultipartFile file, String description) {
        MinioClient client = getClientOrThrow();

        try {
            InputStream istream = file.getInputStream();
            UUID imgName = UUID.randomUUID();
            String objectName = imgName + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            client.putObject(
                PutObjectArgs.builder()
                    .bucket(minioBucket)
                    .object(objectName)
                    .stream(istream, -1, 10485760)
                    .build());

            return new ImageResponse(description, baseUrl + "/images/" + objectName);
        } catch (Exception e) {
            throw new ServiceException("Error saving file: " + e.getMessage());
        }
    }

    public InputStream download(String filename) {
        MinioClient client = getClientOrThrow();

        try {
            return client.getObject(
                GetObjectArgs.builder()
                    .bucket(minioBucket)
                    .object(filename)
                    .build());
        } catch (Exception e) {
            throw new ServiceException("Error downloading file: " + e.getMessage());
        }
    }

    private MinioClient getClientOrThrow() {
        return minioClient.orElseThrow(
            () -> new ServiceException("MinIO client not configured"));
    }
}
