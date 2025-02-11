package org.example.repo.interfaces;

import org.example.model.Tag;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Long> {
    @Query("SELECT name FROM tag WHERE name IN (:tags)")
    List<String> findExistingTags(@Param("tags") List<String> tags);

    List<Tag> findByNameIn(List<String> tags);

    List<Tag> findByIdIn(List<Long> ids);
}
