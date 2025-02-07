package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.example.model.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record PostReadDto(Long id,
                          String title,
                          String text,
                          @JsonFormat(pattern = DATETIME_FORMAT)
                          LocalDateTime createdDate,
                          Long authorId,
                          List<CommentReadDto> comments) {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
