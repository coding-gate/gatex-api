package org.gatex.repository;

import org.gatex.entity.McqQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface McqQuestionRepository extends MongoRepository<McqQuestion, String>{

}
