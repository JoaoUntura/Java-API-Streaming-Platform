package dev.joaountura.streaming_project.data_loaders;

import com.netflix.graphql.dgs.DgsDataLoader;
import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.repositories.VideoRepository;
import org.dataloader.BatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Component
@DgsDataLoader(name = "videoByCourseDataLoader")
public class VideoByCourseDataLoader implements BatchLoader<Long, List<Video>> {
    @Autowired
    private VideoRepository videoRepository;

    @Override
    public CompletionStage<List<List<Video>>> load(List<Long> courseIds) {


        return CompletableFuture.supplyAsync(() -> {
                List<Video> videos = videoRepository.findByCourseIdIn(courseIds);

                Map<Long, List<Video>> videoListByCourseId = videos.stream().collect(Collectors.groupingBy(video -> video.getCourse().getId()));

                return courseIds.stream().map(courseId -> ( videoListByCourseId.getOrDefault(courseId, Collections.emptyList()))).toList();

                }

                 );



    }
}
