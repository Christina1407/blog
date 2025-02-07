package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.PostManager;
import org.example.manager.UserManager;
import org.example.mapper.CommentMapper;
import org.example.mapper.PostMapper;
import org.example.model.Comment;
import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.example.repo.CommentRepository;
import org.example.repo.PostRepository;
import org.example.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final UserManager userManager;
    private final PostManager postManager;
    private final CommentRepository commentRepository;

    @Override
    public PostReadDto savePost(PostCreateDto postCreateDto) {
        userManager.findUserById(postCreateDto.authorId());
        Post post = postMapper.map(postCreateDto);
        return postMapper.map(postRepository.save(post));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Post> findAllPosts(Pageable pageable) {

        Page<Post> posts = postRepository.findAll(pageable);
        return posts;
    }

    @Override
    @Transactional(readOnly = true)//TODO убрать отдельное получение комментариев
    public PostReadDto findPostById(Long postId) {
        Post post = postManager.findPostById(postId);
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return postMapper.map(post, commentMapper.map(comments));
    }
}
