package org.example.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record PostCreateDto(
        @NotNull
        @NotBlank(message = "title is empty")
        @Size(min = 1, max = 100, message = "title minLength = 1 maxLength = 100")
        String title,
        MultipartFile image,
        @NotNull
        @NotBlank(message = "text is empty")
        @Size(min = 1, max = 2000, message = "text minLength = 1 maxLength = 2000")
        String text,
        @NotNull(message = "authorId is null")
        Long authorId
) {
}
