package dev.joaountura.streaming_project.services;

import dev.joaountura.streaming_project.controllers.UploadController;
import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.models.dtos.VideoUploadDTO;
import dev.joaountura.streaming_project.repositories.VideoRepository;
import dev.joaountura.streaming_project.types.CursorSearch;
import dev.joaountura.streaming_project.types.VideoConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class VideoServices {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CourseServices courseServices;

    public void createVideo(VideoUploadDTO videoUploadDTO, String fileId, String thumbNailId){
        Course course = courseServices.findByExternalId(videoUploadDTO.getCourseId());

        Video video = Video.builder()
                .originalFileName(videoUploadDTO.getFile().getOriginalFilename())
                .course(course)
                .title(videoUploadDTO.getTitle())
                .manifestId(UUID.fromString(fileId))
                .thumbNailId(UUID.fromString(thumbNailId))
                .build();

        videoRepository.save(video);

    }

    public VideoConnection getVideos(CursorSearch cursorSearch){
        Pageable page = PageRequest.of(0, cursorSearch.getFirst());

        Page<Video> pageDb = cursorSearch.getAfter() == null
                ? videoRepository.findAll(page)
                : videoRepository.findByIdGreaterThanOrderByIdAsc(cursorSearch.getAfter().longValue(), page);

        List<Video> videos = pageDb.getContent();
        VideoConnection videoConnection = new VideoConnection();
        videoConnection.setVideos(videos);
        videoConnection.setHasNextPage(pageDb.hasNext());
        videoConnection.setEndCursor(!videos.isEmpty() ? Math.toIntExact(videos.getLast().getId()) : 0);

        return videoConnection;
    }

    public Video findVideoById(String id){
        return videoRepository.findByExternalId(id).orElseThrow();
    }


    public void deleteByExternalId(String externalId) throws IOException {

        Video video = findVideoById(externalId);
        String fileId = video.getManifestId().toString();

        Path thumbNailLocation = UploadController.IMAGE_UPLOAD_DIR.resolve(video.getThumbNailId().toString());

        Path originalVideo = UploadController.VIDEO_UPLOAD_DIR.resolve(fileId);

        Path segments = TranscodeService.OUTPUT_SEGMENTS_DIR.resolve(fileId);

        Path manifest = TranscodeService.OUTPUT_MANIFEST_DIR.resolve(fileId);


        Files.deleteIfExists(thumbNailLocation);
        Files.deleteIfExists(originalVideo);

        if (Files.exists(segments)){
            Files.walk(segments).sorted(Comparator.reverseOrder()).forEach(file -> {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }

        if (Files.exists(manifest)){
            Files.walk(manifest).sorted(Comparator.reverseOrder()).forEach(file -> {
                try {
                    Files.deleteIfExists(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        }



        videoRepository.delete(video);
    }
}
