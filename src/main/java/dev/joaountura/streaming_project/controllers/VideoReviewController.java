package dev.joaountura.streaming_project.controllers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;

import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import dev.joaountura.streaming_project.models.UserApp;
import dev.joaountura.streaming_project.models.VideoReview;
import dev.joaountura.streaming_project.security.AuthUtils;
import dev.joaountura.streaming_project.services.VideoReviewServices;
import dev.joaountura.streaming_project.types.CursorSearch;
import dev.joaountura.streaming_project.types.VideoReviewConnection;
import dev.joaountura.streaming_project.types.VideoReviewInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@DgsComponent
public class VideoReviewController {

    @Autowired
    private VideoReviewServices videoReviewServices;

    @DgsQuery
    public VideoReviewConnection getVideoReviews(@InputArgument("input")CursorSearch cursorSearch, @InputArgument("videoId") String videoId){
        return videoReviewServices.getReviewsPaginated(cursorSearch, videoId);
    }


    @DgsMutation
    public VideoReview createVideoReview(@InputArgument("input") VideoReviewInput videoReviewInput){
        return videoReviewServices.createVideoReview(videoReviewInput, AuthUtils.getCurrentUsername());

    }

}
