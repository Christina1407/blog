package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserReadDto;
import org.example.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserReadDto saveUser(@RequestBody @Valid UserCreateDto userCreateDto) {
        log.info("Попытка сохранения нового пользователя {}", userCreateDto);
        return userService.saveUser(userCreateDto);
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Попытка получения всех пользователей");
        return userService.getAllUsers();
    }
}
