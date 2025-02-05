package org.example.repo;

import org.example.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    boolean existsByName(String userName);

    boolean existsByEmail(String email);
}
