package dev.joaountura.streaming_project.repositories;

import dev.joaountura.streaming_project.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByExternalId(String externalId);
}
