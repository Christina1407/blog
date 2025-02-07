package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CommentReadDto(Long id,
                             Long authorId,
                             Long postId,
                             @JsonFormat(pattern = DATETIME_FORMAT)
                             LocalDateTime createdDate,
                             String text) {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

}
