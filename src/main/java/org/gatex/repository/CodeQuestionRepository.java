package org.gatex.repository;

import org.gatex.entity.CodeQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeQuestionRepository extends MongoRepository<CodeQuestion, String>{

}
