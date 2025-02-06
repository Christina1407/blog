package org.example.service;

import org.example.model.User;
import org.example.model.dto.UserCreateDto;
import org.example.model.dto.UserReadDto;

import java.util.List;

public interface UserService {

    UserReadDto saveUser(UserCreateDto userCreateDto);

    List<User> getAllUsers();
}
