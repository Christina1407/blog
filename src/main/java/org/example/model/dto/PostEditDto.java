package org.example.model.dto;

import jakarta.validation.constraints.Size;

import java.util.List;

public record PostEditDto(@Size(min = 1, max = 100, message = "title minLength = 1 maxLength = 100")
                          String title,
                          @Size(min = 1, max = 2000, message = "text minLength = 1 maxLength = 2000")
                          String text,
                          @Size(max = 5, message = "Количество тегов не должно превышать 5")
                          List<String> tags) {
}
