package org.udg.pds.springtodo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.udg.pds.springtodo.dto.ImageRequest;
import org.udg.pds.springtodo.dto.ImageResponse;
import org.udg.pds.springtodo.service.ImageService;

import java.io.InputStream;
import java.net.URLConnection;

@RequestMapping(path = "/images")
@RestController
public class ImageController extends BaseController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ImageResponse upload(HttpSession session,
                                @RequestParam("file") MultipartFile file,
                                @RequestPart("data") ImageRequest data) {
        return imageService.upload(file, data.description());
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<InputStreamResource> download(@PathVariable("filename") String filename) {
        InputStream file = imageService.download(filename);
        InputStreamResource body = new InputStreamResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(URLConnection.guessContentTypeFromName(filename)));
        return ResponseEntity.ok().headers(headers).body(body);
    }
}
