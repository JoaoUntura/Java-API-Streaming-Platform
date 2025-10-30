package dev.joaountura.streaming_project.models.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class VideoUploadDTO {
    private MultipartFile file;
    private MultipartFile thumbNail;
    private String title;
    private String courseId;
}
