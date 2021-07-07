package org.gatex.repository;

import org.gatex.entity.Assessment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface McqTestRepository extends MongoRepository<Assessment, String>{

}
