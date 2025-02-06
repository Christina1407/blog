package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.UserManager;
import org.example.mapper.PostMapper;
import org.example.model.Post;
import org.example.model.User;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
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
    private final PostMapper postMapper;
    private final UserManager userManager;

    @Override
    public PostReadDto savePost( PostCreateDto postCreateDto) {
        userManager.findUserById(postCreateDto.authorId());
        Post post = postMapper.map(postCreateDto);
        return postMapper.map(postRepository.save(post));
    }
}
