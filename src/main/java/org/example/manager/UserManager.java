package org.example.manager;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.repo.interfaces.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserManager {
    private final UserRepository userRepository;

    public void findUserById(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("User with id =  %d was not found", userId)));
    }
}
