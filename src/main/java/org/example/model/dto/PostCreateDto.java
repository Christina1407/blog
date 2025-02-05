package org.example.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public record PostCreateDto(
        Long id,
        @NotBlank(message = "title is empty")
        @Size(min = 1, max = 100, message = "title minLength = 1 maxLength = 100")
        String title,
        byte[] image,
        @NotBlank(message = "text is empty")
        @Size(min = 1, max = 1000, message = "name minLength = 1 maxLength = 1000")
        String text,
        @JsonFormat(pattern = DATETIME_FORMAT)
        LocalDateTime createdOn,
        Long authorId

) {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
}
