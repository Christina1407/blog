package org.example.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CommentCreateDto(
        @NotNull(message = "authorId is null")
        Long authorId,
        @NotNull(message = "postId is null")
        Long postId,
        @NotBlank(message = "text is empty")
        @Size(min = 1, max = 2000, message = "text minLength = 1 maxLength = 2000")
        String text
) {
}
