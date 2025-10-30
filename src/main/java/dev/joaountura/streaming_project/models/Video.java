package dev.joaountura.streaming_project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(unique = true)
    private String externalId = UUID.randomUUID().toString();

    private String title;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private String originalFileName;

    @Column(unique = true)
    private UUID manifestId;

    @Column(unique = true)
    private UUID thumbNailId;

    @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VideoReview> videoReviewList;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant created_at;

}
