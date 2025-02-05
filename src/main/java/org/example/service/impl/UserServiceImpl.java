package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.ConflictException;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.example.model.dto.UserDto;
import org.example.repo.UserRepository;
import org.example.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    //private final UserMapper userMapper;

    @Override //TODO
    public UserDto saveUser(UserDto userDto) {
        existsByName(userDto.name());
        existsByEmail(userDto.email());
        //User user = userMapper.map(userDto);
        return null;
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
