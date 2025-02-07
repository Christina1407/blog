package org.example.mapper;

import org.example.model.User;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserReadDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserReadDto map(User user) {
        return getBuild(user);
    }

    public User map(UserCreateDto userCreateDto) {
        return getBuild(userCreateDto);
    }

    private UserReadDto getBuild(User user) {
        return UserReadDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    private User getBuild(UserCreateDto userCreateDto) {
        return User.builder()
                .name(userCreateDto.name())
                .email(userCreateDto.email())
                .build();
    }
}
