package dev.joaountura.streaming_project.controllers;

import com.netflix.graphql.dgs.*;
import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.services.VideoServices;
import dev.joaountura.streaming_project.types.CursorSearch;
import dev.joaountura.streaming_project.types.VideoConnection;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
public class VideoController {

    @Autowired
    private VideoServices videoServices;



    @DgsQuery
    public VideoConnection videos(@InputArgument("input") CursorSearch cursorSearch)
    {
        return videoServices.getVideos(cursorSearch);
    }

    @DgsData(parentType = "Course", field = "videoList")
    public CompletableFuture<Object> videoList(DataFetchingEnvironment dfe){

        Course course = dfe.getSource();
        return dfe.getDataLoader("videoByCourseDataLoader").load(course.getId());

    }

    @DgsMutation
    public Boolean deleteVideo(@InputArgument("externalId") String externalId) throws IOException {
        videoServices.deleteByExternalId(externalId);
        return true;
    }

}
