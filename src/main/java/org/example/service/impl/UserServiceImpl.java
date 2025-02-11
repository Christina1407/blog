package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ConflictException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserReadDto;
import org.example.repo.interfaces.UserRepository;
import org.example.service.interfaces.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserReadDto saveUser(UserCreateDto userCreateDto) {
        existsByName(userCreateDto.name());
        existsByEmail(userCreateDto.email());
        User user = userMapper.map(userCreateDto);
        return userMapper.map(userRepository.save(user));

    }

    @Override//TODO переделать
    public List<User> getAllUsers() {
        List<User> res = new ArrayList<>();
        userRepository.findAll()
                .forEach(res::add);
        return res;
    }

    private void existsByName(String userName) {
        if (userRepository.existsByName(userName)) {
            throw new ConflictException("Constraint unique_user_name", "Integrity constraint has been violated.");
        }
    }

    private void existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new ConflictException("Constraint unique_user_email", "Integrity constraint has been violated.");
        }
    }
}
