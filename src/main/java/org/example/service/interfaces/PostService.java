package org.example.service.interfaces;

import jakarta.servlet.http.HttpServletResponse;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostEditDto;
import org.example.model.dto.PostReadDto;
import org.example.model.dto.PostReactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface PostService {
    PostReadDto savePost(PostCreateDto postCreateDto);

    Page<PostReadDto> findAllPosts(Pageable pageable);

    PostReadDto findPostById(Long postId);

    void addReaction(PostReactionDto reactionDto);

    void deletePost(Long postId);

    PostReadDto editPost(PostEditDto postEditDto, Long postId);

    void changeImage(Long postId, MultipartFile image);

    Page<PostReadDto> findPostsByTag(String tagName, Pageable pageable);

    void getImage(Long postId, HttpServletResponse response);
}
