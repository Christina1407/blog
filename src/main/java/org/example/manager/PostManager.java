package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repo.PostRepository;
import org.example.repo.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {
    private final PostRepository postRepository;

    public Post findPostById(Long postId) {
      return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(String.format("Post with id =  %d was not found", postId)));
    }
}
