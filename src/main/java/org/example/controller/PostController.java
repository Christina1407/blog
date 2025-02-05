package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Post;
import org.example.repo.PostRepository;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.io.IOException;

@RestController
@RequestMapping(path = "/{userId}/posts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post savePost(@PathVariable("userId") @Min(1) Long userId,
                          @RequestBody @Valid Post post) {
        log.info("Попытка сохранения нового post {}", post);
        return postService.savePost(userId, post);
    }

    @PostMapping("/test")//TODO переделать
    public void test(@RequestParam("file") MultipartFile file) throws IOException {
        Post byId = postRepository.findById(2L).get();
        byId.setImage(file.getBytes());
        postRepository.save(byId);
    }
}
