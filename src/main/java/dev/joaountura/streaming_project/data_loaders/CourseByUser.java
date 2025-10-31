package dev.joaountura.streaming_project.data_loaders;

import com.netflix.graphql.dgs.DgsDataLoader;
import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.repositories.CourseRepository;
import org.dataloader.BatchLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

@Component
@DgsDataLoader(name = "courseByUser")
public class CourseByUser implements MappedBatchLoader<Long, List<Course>> {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public CompletionStage<Map<Long, List<Course>>> load(Set<Long> userId) {

        List<Course> courses = courseRepository.findByUserAppIdIn(userId);

        return CompletableFuture.supplyAsync( () -> courses.stream().collect(Collectors.groupingBy(
                course -> course.getUserApp().getId()
        )));

    }
}
