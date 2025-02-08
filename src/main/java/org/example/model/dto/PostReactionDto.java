package org.example.model.dto;

import jakarta.validation.constraints.NotNull;
import org.example.model.enums.ReactionType;


public record PostReactionDto(
        @NotNull(message = "postId is null")
        Long postId,
//        @NotNull(message = "userId is null")
//        Long userId,
        @NotNull
        ReactionType reactionType) {
}
