package org.gatex.repository;

import org.gatex.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TagRepository extends MongoRepository<Tag, String>{

}
