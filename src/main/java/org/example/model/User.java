package org.example.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Builder
@Entity
@Table(name = "user_account", schema = "public")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@AccessType(AccessType.Type.PROPERTY)
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", nullable = false, length = 254)
    private String email;
    @Column(name = "name", nullable = false, length = 250)
    private String name;
}
