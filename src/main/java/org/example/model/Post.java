package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.relational.core.mapping.InsertOnlyProperty;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Table(name = "post", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AccessType(AccessType.Type.PROPERTY)
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    @Lob
    @Column(name="image")
    private byte[] image;
    @InsertOnlyProperty
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    @Column(name = "text", nullable = false, length = 2000)
    private String text;
    @Column(name = "author_id", nullable = false)
    private Long authorId;
    @MappedCollection(idColumn = "post_id")
    private Set<Comment> comments;
}
