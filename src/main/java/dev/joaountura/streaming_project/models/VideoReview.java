package dev.joaountura.streaming_project.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class VideoReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(unique = true)
    private String externalId = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "userApp_id")
    private UserApp userApp;

    private String viewer;

    private String comment;

    private Double rating;

    @ManyToOne
    @JoinColumn(name = "video_id")
    private Video video;

    @CreationTimestamp
    private Instant created_at;

}
