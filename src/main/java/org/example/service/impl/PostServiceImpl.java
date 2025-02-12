package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.manager.PostManager;
import org.example.manager.UserManager;
import org.example.mapper.PostMapper;
import org.example.mapper.PostReactionMapper;
import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostEditDto;
import org.example.model.dto.PostReactionDto;
import org.example.model.dto.PostReadDto;
import org.example.repo.interfaces.PostReactionRepository;
import org.example.repo.interfaces.PostRepository;
import org.example.repo.interfaces.PostTagRepository;
import org.example.service.interfaces.PostService;
import org.example.service.interfaces.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PostReactionMapper reactionMapper;
    private final UserManager userManager;
    private final PostManager postManager;
    private final PostReactionRepository postReactionRepository;
    private final TagService tagService;
    private final PostTagRepository postTagRepository;

    @Override
    public PostReadDto savePost(PostCreateDto postCreateDto) {
        userManager.findUserById(postCreateDto.authorId());
        Post post = postMapper.map(postCreateDto);
        postRepository.save(post);
        tagService.insertNotExistedTags(postCreateDto.tags());
        tagService.addTagsToPost(postCreateDto.tags(), post.getId());
        return postMapper.map(postManager.findPostById(post.getId()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PostReadDto> findAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<Post> posts = postPage.stream().
                toList();
        return new PageImpl<>(postMapper.map(posts), pageable, postPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public PostReadDto findPostById(Long postId) {
        Post post = postManager.findPostById(postId);
        return postMapper.map(post);
    }

    @Override
    public Page<PostReadDto> findPostsByTag(String tagName, Pageable pageable) {
        Long tagId = tagService.getTagId(tagName);
        List<Long> postIds = postTagRepository.findPostIdsByTagId(tagId);
        Page<Post> postPage = postRepository.findByIdIn(postIds, pageable);
        List<Post> posts = postPage.stream().
                toList();
        return new PageImpl<>(postMapper.map(posts), pageable, postPage.getTotalElements());
    }

    @Override
    public void addReaction(PostReactionDto reactionDto) {
        //  userManager.findUserById(reactionDto.userId());
        postManager.findPostById(reactionDto.postId());
        postReactionRepository.save(reactionMapper.map(reactionDto));
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postManager.findPostById(postId);
        postRepository.delete(post);
    }

    @Override
    public PostReadDto editPost(PostEditDto postEditDto, Long postId) {
        Post post = postManager.findPostById(postId);
        Optional.ofNullable(postEditDto.text())
                .ifPresent(post::setText);
        Optional.ofNullable(postEditDto.title())
                .ifPresent(post::setTitle);
        Optional.ofNullable(postEditDto.tags())
                .ifPresent(tags -> {
                    postTagRepository.deleteAll(post.getPostTags());
                    tagService.insertNotExistedTags(postEditDto.tags());
                    tagService.addTagsToPost(postEditDto.tags(), postId);
                    post.setPostTags(postTagRepository.findByPostId(postId));
                });
        return postMapper.map(postRepository.save(post));
    }


    @Override
    @SneakyThrows
    public void changeImage(Long postId, MultipartFile image) {
        Post post = postManager.findPostById(postId);
        post.setImage(image.getBytes());
        postRepository.save(post);
    }


}
