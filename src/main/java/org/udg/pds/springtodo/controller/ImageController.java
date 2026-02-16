package org.udg.pds.springtodo.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.exception.ServiceException;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.UUID;

@RequestMapping(path = "/images")
@RestController
public class ImageController extends BaseController {

    private final Global global;

    public ImageController(Global global) {
        this.global = global;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ImageReturn upload(HttpSession session,
                              @RequestParam("file") MultipartFile file,
                              @RequestPart("data") ImageData data) {

        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null) {
            throw new ServiceException("Minio client not configured");
        }

        try {
            InputStream istream = file.getInputStream();
            UUID imgName = UUID.randomUUID();

            String objectName = imgName + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(global.getMinioBucket())
                    .object(objectName)
                    .stream(istream, -1, 10485760)
                    .build());

            return new ImageReturn(data.description, global.getBaseURL() + "/images/" + objectName);
        } catch (Exception e) {
            throw new ServiceException("Error saving file: " + e.getMessage());
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("filename") String filename) {

        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null) {
            throw new ServiceException("Minio client not configured");
        }

        try {
            InputStream file = minioClient.getObject(
                GetObjectArgs.builder().bucket(global.getMinioBucket()).object(filename).build());
            InputStreamResource body = new InputStreamResource(file);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(filename)));
            return ResponseEntity.ok().headers(headers).body(body);
        } catch (Exception e) {
            throw new ServiceException("Error downloading file: " + e.getMessage());
        }
    }

    static class ImageData {
        public String description;
    }

    static class ImageReturn {
        public String description;
        public String url;

        public ImageReturn() {
        }

        public ImageReturn(String description, String url) {
            this.description = description;
            this.url = url;
        }
    }
}
