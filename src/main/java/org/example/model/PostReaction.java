package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.example.model.enums.ReactionType;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "post_reaction", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AccessType(AccessType.Type.PROPERTY)
public class PostReaction {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "post_id", nullable = false)
    private Long postId;
//    @Column(name = "user_id", nullable = false)
//    private Long userId;
    @InsertOnlyProperty
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "reaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
