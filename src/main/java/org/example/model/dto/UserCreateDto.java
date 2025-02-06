package org.example.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;



@Builder
public record UserCreateDto(
        @NotBlank(message = "email is empty")
        @Email(message = "email is not well-formed email address")
        @Size(min = 6, max = 254, message = "email minLength = 6 maxLength = 254")
        String email,
        @NotBlank(message = "name is empty")
        @Size(min = 2, max = 250, message = "name minLength = 2 maxLength = 250")
        String name) {
}
