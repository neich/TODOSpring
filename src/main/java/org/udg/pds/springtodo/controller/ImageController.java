package org.udg.pds.springtodo.controller;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.udg.pds.springtodo.Global;
import org.udg.pds.springtodo.configuration.exceptions.ControllerException;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.UUID;

@RequestMapping(path = "/images")
@RestController
public class ImageController extends BaseController {

    @Autowired
    Global global;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ImageReturn upload(HttpSession session,
                              @RequestParam("file") MultipartFile file,
                              @RequestPart("data") ImageData data) {

        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null)
            throw new ControllerException("Minio client not configured");

        try {
            // Handle the body of that part with an InputStream
            InputStream istream = file.getInputStream();
            String contentType = file.getContentType();
            UUID imgName = UUID.randomUUID();

            String objectName = imgName + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // Upload the file to the bucket with putObject
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(global.getMinioBucket())
                    .object(objectName)
                    .stream(istream, -1, 10485760)
                    .build());

            return new ImageReturn(data.description, "http://localhost:8080/images/" + objectName);
        } catch (Exception e) {
            throw new ControllerException("Error saving file: " + e.getMessage());
        }
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("filename") String filename) {

        MinioClient minioClient = global.getMinioClient();
        if (minioClient == null)
            throw new ControllerException("Minio client not configured");

        try {
            InputStream file = minioClient.getObject(GetObjectArgs.builder().bucket(global.getMinioBucket()).object(filename).build());
            InputStreamResource body = new InputStreamResource(file);
            HttpHeaders headers = new HttpHeaders();
            // headers.setContentLength(body.contentLength());
            // headers.setContentDispositionFormData("attachment", "test.csv");
            headers.setContentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(filename)));
            return ResponseEntity.ok().headers(headers).body(body);

        } catch (Exception e) {
            throw new ControllerException("Error downloading file: " + e.getMessage());
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
