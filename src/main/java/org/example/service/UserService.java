package org.example.service;

import org.example.model.User;
import org.example.model.dto.UserDto;

public interface UserService {

    UserDto saveUser(UserDto userDto);

}
