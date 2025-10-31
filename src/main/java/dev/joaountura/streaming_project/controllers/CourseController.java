package dev.joaountura.streaming_project.controllers;

import com.netflix.graphql.dgs.DgsComponent;

import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;

import com.netflix.graphql.dgs.InputArgument;
import dev.joaountura.streaming_project.models.Course;
import dev.joaountura.streaming_project.models.UserApp;
import dev.joaountura.streaming_project.services.CourseServices;

import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    @DgsData(parentType = "UserApp", field = "courses")
    public CompletableFuture<Object> courses(DataFetchingEnvironment dfe){

        UserApp userApp = dfe.getSource();
        assert userApp != null;
        return dfe.getDataLoader("courseByUser").load(userApp.getId());

    }


}
