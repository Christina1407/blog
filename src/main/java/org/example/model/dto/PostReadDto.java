package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostReadDto(Long id,
                          String title,
                          byte[] image,
                          String text,
                          @JsonFormat(pattern = DATETIME_FORMAT)
                          LocalDateTime createdDate,
                          Long authorId) {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
