package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;


import java.time.LocalDateTime;

@Builder
public record PostCreateDto(
        @NotBlank(message = "title is empty")
        @Size(min = 1, max = 100, message = "title minLength = 1 maxLength = 100")
        String title,
        byte[] image,
        @NotBlank(message = "text is empty")
        @Size(min = 1, max = 1000, message = "text minLength = 1 maxLength = 1000")
        String text,
        @JsonFormat(pattern = DATETIME_FORMAT)
        LocalDateTime createdDate,
        @NotNull(message = "authorId is null")
        Long authorId

) {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
