package dev.joaountura.streaming_project.repositories;

import dev.joaountura.streaming_project.models.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByExternalId(String externalId);

    Page<Video> findByIdGreaterThanOrderByIdAsc(Long cursorId, Pageable pageable);


    List<Video> findByCourseIdIn(List<Long> courseIds);

}
