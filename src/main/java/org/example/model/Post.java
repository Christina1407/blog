package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;


import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "post", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    @CreatedDate
    @Column(name = "created_date", nullable = false)
    private LocalDateTime created_date;
    @Column(name = "text", nullable = false, length = 2000)
    private String text;
    private Long author_id;




}
