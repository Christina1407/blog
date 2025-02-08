package org.example.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CommentEditDto(@NotBlank(message = "text is empty")
                             @Size(min = 1, max = 2000, message = "text minLength = 1 maxLength = 2000")
                             String text) {
}
