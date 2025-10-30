package dev.joaountura.streaming_project.controllers;

import com.netflix.graphql.dgs.DgsComponent;

import com.netflix.graphql.dgs.DgsQuery;

import com.netflix.graphql.dgs.InputArgument;
import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.services.CourseServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

@DgsComponent
public class CourseController {

    @Autowired
    private CourseServices courseServices;

    @Secured("VIEWER")
    @DgsQuery
    public List<Course> courses(){
        return courseServices.getAll();
    }

    @DgsQuery
    public Course courseById(@InputArgument("externalId") String externalId){
        return courseServices.findByExternalId(externalId);
    }


}
