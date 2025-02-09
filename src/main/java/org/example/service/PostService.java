package org.example.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostEditDto;
import org.example.model.dto.PostReadDto;
import org.example.model.dto.PostReactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {
    PostReadDto savePost(PostCreateDto postCreateDto);

    Page<PostReadDto> findAllPosts(Pageable pageable);

    PostReadDto findPostById(Long postId);

    void addReaction(PostReactionDto reactionDto);

    void deletePost(Long postId);

    PostReadDto editPost(PostEditDto postEditDto, Long postId);

    void changeImage(Long postId, MultipartFile image);
}
