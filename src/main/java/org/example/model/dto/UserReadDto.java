package org.example.model.dto;

import lombok.Builder;

@Builder
public record UserReadDto(Long id,
                          String email,
                          String name) {
}
