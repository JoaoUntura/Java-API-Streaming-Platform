package dev.joaountura.streaming_project.repositories;

import dev.joaountura.streaming_project.models.Video;
import dev.joaountura.streaming_project.models.VideoReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoReviewRepository extends JpaRepository<VideoReview, Long> {
    Page<VideoReview> findAllByVideoOrderByIdDesc(Pageable page, Video video);
    Page<VideoReview> findByVideoAndIdLessThanOrderByIdDesc(Pageable page, Video video, Long id);
}
