package dev.joaountura.streaming_project.services;

import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.models.UserApp;
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

    @Autowired
    private UserServices userServices;

    @Transactional
    public List<Course> getAll(){
        return courseRepository.findAll();
    }

    public void createCourseService(CourseUploadDTO courseUploadDTO, String thumbNailId, String userId){

        UserApp userApp = userServices.findByExternalId(userId).orElseThrow();

        Course course = Course.builder()
                .userApp(userApp)
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
