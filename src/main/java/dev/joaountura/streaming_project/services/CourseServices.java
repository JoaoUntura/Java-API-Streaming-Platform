package dev.joaountura.streaming_project.services;

import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.models.dtos.CourseUploadDTO;
import dev.joaountura.streaming_project.repositories.CourseRepository;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourseServices {

    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public List<Course> getAll(){
        return courseRepository.findAll();
    }

    public void createCourseService(CourseUploadDTO courseUploadDTO, String thumbNailId){

        Course course = Course.builder()
                .title(courseUploadDTO.getTitle())
                .description(courseUploadDTO.getDescription())
                .thumbNailId(UUID.fromString(thumbNailId))
                .build();

        courseRepository.save(course);
    }

    public Course findByExternalId(String externalId){
        return courseRepository.findByExternalId(externalId);
    }

}
