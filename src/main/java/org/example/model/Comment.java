package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;

import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "comment", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AccessType(AccessType.Type.PROPERTY)
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "text", nullable = false, length = 2000)
    private String text;
    @Column(name = "post_id", nullable = false)
    private Long postId;
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    @InsertOnlyProperty
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

}
