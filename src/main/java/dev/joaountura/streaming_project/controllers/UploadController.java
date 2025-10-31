package dev.joaountura.streaming_project.controllers;

import dev.joaountura.streaming_project.models.dtos.CourseUploadDTO;
import dev.joaountura.streaming_project.models.dtos.VideoUploadDTO;
import dev.joaountura.streaming_project.producers.VideoTranscoderProducer;
import dev.joaountura.streaming_project.security.AuthUtils;
import dev.joaountura.streaming_project.services.CourseServices;
import dev.joaountura.streaming_project.services.UploadServices;
import dev.joaountura.streaming_project.services.VideoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;


@RestController
@RequestMapping("/upload")
public class UploadController {

    public static final Path VIDEO_UPLOAD_DIR = Paths.get("videos");
    public static final Path IMAGE_UPLOAD_DIR = Paths.get("images");

    @Autowired
    private UploadServices uploadServices;

    @Autowired
    private VideoServices videoServices;

    @Autowired
    private CourseServices courseServices;


    @Autowired
    private VideoTranscoderProducer videoTranscoderProducer;


    @PostMapping("/video")
    public ResponseEntity<String> uploadVideo(@ModelAttribute VideoUploadDTO videoUploadDTO) throws Exception {

       String fileId = uploadServices.uploadFile(videoUploadDTO.getFile(),VIDEO_UPLOAD_DIR);
       String thumbNailId = uploadServices.uploadFile(videoUploadDTO.getThumbNail(), IMAGE_UPLOAD_DIR);
       videoServices.createVideo(videoUploadDTO, fileId, thumbNailId);
       videoTranscoderProducer.sendVideo(fileId);

       return ResponseEntity.status(HttpStatus.OK).body("Uploaded");

    }

    @PostMapping("/course")
    public ResponseEntity<String> uploadCourse(@ModelAttribute CourseUploadDTO courseUploadDTO) throws Exception {

        String thumbNailId = uploadServices.uploadFile(courseUploadDTO.getThumbNail(),IMAGE_UPLOAD_DIR);

        courseServices.createCourseService(courseUploadDTO, thumbNailId, AuthUtils.getCurrentUsername());

        return ResponseEntity.status(HttpStatus.OK).body("Uploaded");

    }


}
