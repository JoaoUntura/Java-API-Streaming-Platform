package dev.joaountura.streaming_project.controllers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import dev.joaountura.streaming_project.models.UserApp;
import dev.joaountura.streaming_project.security.AuthUtils;
import dev.joaountura.streaming_project.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;

@DgsComponent
public class UserController {

    @Autowired
    private UserServices userServices;

    @DgsQuery
    public UserApp userApp(){

        return userServices.findByExternalId(AuthUtils.getCurrentUsername()).orElseThrow();

    }
}
