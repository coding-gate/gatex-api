package org.gatex.dao;

import org.gatex.entity.Tag;

public interface TagDao {
    Tag getById(String tagId);
    void save(Tag tag);
}
