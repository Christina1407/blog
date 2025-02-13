package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostEditDto;
import org.example.model.dto.PostReactionDto;
import org.example.model.dto.PostReadDto;
import org.example.repo.interfaces.PostRepository;
import org.example.service.interfaces.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public PostReadDto savePost(@ModelAttribute PostCreateDto postCreateDto){
        log.info("Попытка сохранения нового post {}", postCreateDto);
        return postService.savePost(postCreateDto);
    }

    @DeleteMapping("{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("postId") @Min(1) Long postId) {
        log.info("Попытка удаления post id = {}", postId);
        //TODO кто удаляет пост? Сделать, когда будет авторизация
        postService.deletePost(postId);
    }

    @PatchMapping("{postId}") //на фронте редактирование происходит при нажатии на сам текст title и text (так же как при редактировании комментария),
    // Ctrl+Enter сохраняется
    public PostReadDto editPost(@PathVariable("postId") @Min(1) Long postId,
                                      @RequestBody @Valid PostEditDto postEditDto) {
        log.info("Редактирование поста {} id = {}", postEditDto, postId);
        return postService.editPost(postEditDto, postId);
    }

    @GetMapping
    public Page<PostReadDto> getAllPosts(Pageable pageable) {
        log.info("Get posts pageable = {}", pageable);
        return postService.findAllPosts(pageable);
    }

    @GetMapping("/{postId}")
    public PostReadDto getPostById(@PathVariable("postId") @Min(1) Long postId) {
        log.info("Get post id = {}", postId);
        return postService.findPostById(postId);
    }
    @GetMapping("/tags/{tag}")
    public Page<PostReadDto> getPostsByTag(@PathVariable("tag") String tagName,
                                     Pageable pageable) {
        log.info("Get posts tag = {}", tagName);
        return postService.findPostsByTag(tagName, pageable);
    }

    @PutMapping(value = "/{postId}/image", consumes = "multipart/form-data")
    public void changeImage(@PathVariable("postId") Long postId,
                            @RequestParam("file") MultipartFile image) {
        postService.changeImage(postId, image);
    }

    @GetMapping("/image")
    public void getImage(@RequestParam("postId") Long postId, HttpServletResponse response) {
        postService.getImage(postId, response);
    }
    @PostMapping("/reactions")
    public void addReaction(@RequestBody PostReactionDto reactionDto) {
        postService.addReaction(reactionDto);
    }

}
