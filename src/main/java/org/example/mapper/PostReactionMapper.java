package org.example.mapper;

import org.example.model.PostReaction;
import org.example.model.dto.PostReactionDto;
import org.springframework.stereotype.Component;

@Component
public class PostReactionMapper {
    public PostReaction map(PostReactionDto postReactionDto) {
        return getBuild(postReactionDto);
    }
    private PostReaction getBuild(PostReactionDto postReactionDto) {
        return PostReaction.builder()
              //  .userId(postReactionDto.userId())
                .postId(postReactionDto.postId())
                .reactionType(postReactionDto.reactionType())
                .build();
    }
}
