package org.example.repo.impl;

import lombok.RequiredArgsConstructor;
import org.example.model.PostTag;
import org.example.model.Tag;
import org.example.repo.interfaces.BatchTagRepository;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BatchTagRepositoryImpl implements BatchTagRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void insertTags(List<Tag> tags) {
        String sql = "INSERT INTO tag (name) VALUES (:name)";
        SqlParameterSource[] batch = tags.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }

    @Override
    public void insertPostTags(List<PostTag> postTags) {
        String sql = "INSERT INTO post_tag (post_id, tag_id) VALUES (:postId, :tagId)";
        SqlParameterSource[] batch = postTags.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }
}
