package org.example.repo.interfaces;

import org.example.model.PostTag;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PostTagRepository extends CrudRepository<PostTag, Long> {
    @Query("select post_id from post_tag where tag_id = :tagId")
    List<Long> findPostIdsByTagId(@Param("tagId") Long tagId);

    Set<PostTag> findByPostId(Long postId);
}
