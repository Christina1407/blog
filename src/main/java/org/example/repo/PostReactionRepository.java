package org.example.repo;

import org.example.model.PostReaction;
import org.springframework.data.repository.CrudRepository;

public interface PostReactionRepository extends CrudRepository<PostReaction, Long> {
}
