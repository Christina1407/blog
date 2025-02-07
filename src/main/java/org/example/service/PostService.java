package org.example.service;

import jakarta.validation.constraints.Min;
import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostService {
    PostReadDto savePost(PostCreateDto postCreateDto);

    Page<Post> findAllPosts(Pageable pageable);

    PostReadDto findPostById(@Min(1) Long postId);
}
