package dev.joaountura.streaming_project.repositories;

import dev.joaountura.streaming_project.models.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserApp, Long> {

    Optional<UserApp> findByEmail(String email);
    Optional<UserApp> findByExternalId(String externalId);

}
