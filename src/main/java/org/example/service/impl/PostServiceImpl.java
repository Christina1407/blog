package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Post;
import org.example.repo.PostRepository;
import org.example.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override//TODO переделать
    public Post savePost(Long userId, Post post) {
        post.setAuthor_id(userId);
        postRepository.save(post);
        return post;
    }
}
