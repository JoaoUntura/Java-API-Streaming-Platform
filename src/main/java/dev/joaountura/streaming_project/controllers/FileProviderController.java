package dev.joaountura.streaming_project.controllers;

import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.services.TranscodeService;
import dev.joaountura.streaming_project.services.UploadServices;
import dev.joaountura.streaming_project.services.VideoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.headers.ContentTypeOptionsDsl;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/file")
public class FileProviderController {

    @Autowired
    private VideoServices videoServices;

    @GetMapping("/manifest")
    public ResponseEntity<Resource> getManifest(@RequestParam String manifestId) throws MalformedURLException {

        Path manifestPath = TranscodeService.OUTPUT_MANIFEST_DIR
                .resolve(manifestId)
                .resolve("master.m3u8");

        Resource file = new UrlResource(manifestPath.toUri());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("application/vnd.apple.mpegurl"))
                .body(file);

    }

    @GetMapping("/image")
    public ResponseEntity<Resource> getImage(@RequestParam String thumbNailId) throws MalformedURLException {
        Path thumbNail = UploadController.IMAGE_UPLOAD_DIR.resolve(thumbNailId);

        Resource file = new UrlResource(thumbNail.toUri());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(file);

    }
}
