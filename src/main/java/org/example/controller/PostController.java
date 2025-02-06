package org.example.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Post;
import org.example.model.dto.PostCreateDto;
import org.example.model.dto.PostReadDto;
import org.example.repo.PostRepository;
import org.example.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/posts")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostReadDto savePost(@RequestBody @Valid PostCreateDto postCreateDto) {
        log.info("Попытка сохранения нового post {}", postCreateDto);
        return postService.savePost(postCreateDto);
    }

    @PostMapping("/image")//TODO переделать
    public void loadImage(@RequestParam("file") MultipartFile file,
                     @RequestParam("postId") Long postId) throws IOException {
        Post byId = postRepository.findById(2L).get();
        byId.setImage(file.getBytes());
        postRepository.save(byId);
    }

    @GetMapping("/image")//TODO переделать
    public void getImage(@RequestParam("postId") Long postId, HttpServletResponse response) throws IOException {
        byte[] imageBytes = postRepository.findById(2L).get().getImage();

        if (imageBytes != null) {
            response.setContentType("image/jpeg"); // Укажите правильный MIME-тип для вашего изображения
            response.getOutputStream().write(imageBytes);
            response.getOutputStream().flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
