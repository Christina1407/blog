package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repo.interfaces.PostRepository;
import org.example.repo.interfaces.TagRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostManager {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new NotFoundException(String.format("Post with id =  %d was not found", postId)));
    }
}
