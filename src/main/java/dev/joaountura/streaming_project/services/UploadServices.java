package dev.joaountura.streaming_project.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class UploadServices {


    public String uploadFile(MultipartFile file, Path directory) throws Exception {
        if (file.isEmpty()) throw new Exception("File is Empty");

        if (!Files.exists(directory)) Files.createDirectories(directory);

        String fileId = UUID.randomUUID().toString();

        Path uploadedFilePath = directory.resolve(fileId);

        Files.copy(file.getInputStream(), uploadedFilePath, StandardCopyOption.REPLACE_EXISTING); //Copy file data to file path

        return fileId;

    }



}
