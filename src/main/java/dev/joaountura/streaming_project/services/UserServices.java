package dev.joaountura.streaming_project.services;

import dev.joaountura.streaming_project.models.UserApp;
import dev.joaountura.streaming_project.models.enums.Role;
import dev.joaountura.streaming_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;


    public Optional<UserApp> findUserByEmail (String email){
        return  userRepository.findByEmail(email);
    }

    public Optional<UserApp> findByExternalId (String externalId){
        return userRepository.findByExternalId(externalId);
    }

    public UserApp createUser(String email, String name, String picture){

        UserApp newUserApp = UserApp.builder()
                .email(email)
                .name(name)
                .picture(picture)
                .role(Role.VIEWER)
                .build();

        return userRepository.save(newUserApp);

    }

}
