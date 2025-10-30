package dev.joaountura.streaming_project.services;

import dev.joaountura.streaming_project.models.UserApp;
import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.models.VideoReview;

import dev.joaountura.streaming_project.repositories.VideoReviewRepository;
import dev.joaountura.streaming_project.types.CursorSearch;
import dev.joaountura.streaming_project.types.VideoReviewConnection;
import dev.joaountura.streaming_project.types.VideoReviewInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoReviewServices {

    @Autowired
    private VideoReviewRepository videoReviewRepository;

    @Autowired
    private VideoServices videoServices;

    @Autowired
    private UserServices userServices;

    public VideoReviewConnection getReviewsPaginated(CursorSearch cursorSearch, String videoId){
        Pageable page = PageRequest.of(0, cursorSearch.getFirst());

        Video video = videoServices.findVideoById(videoId);

        Page<VideoReview> pageDb = cursorSearch.getAfter() == null
                ? videoReviewRepository.findAllByVideoOrderByIdDesc(page,video)
                : videoReviewRepository.findByVideoAndIdLessThanOrderByIdDesc(page, video, cursorSearch.getAfter().longValue());

        List<VideoReview> videoReviews = pageDb.getContent();

        return VideoReviewConnection
                .newBuilder()
                .videosReviews(videoReviews)
                .hasNextPage(pageDb.hasNext())
                .endCursor(!videoReviews.isEmpty() ? Math.toIntExact(videoReviews.getLast().getId()) : 0)
                .build();
    }

    public VideoReview createVideoReview(VideoReviewInput videoReviewInput, String externalId){

        UserApp userApp = userServices.findByExternalId(externalId).orElseThrow();

        Video video = videoServices.findVideoById(videoReviewInput.getVideoExternalId());

        VideoReview videoReview = VideoReview.builder()
                .video(video)
                .userApp(userApp)
                .rating(videoReviewInput.getRating())
                .comment(videoReviewInput.getComment())
                .viewer(videoReviewInput.getViewer())
                .build();

        return videoReviewRepository.save(videoReview);
    }


}
