package org.gatex.repository;

import org.gatex.entity.CodeAnswer;
import org.gatex.entity.McqAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CodeAnswerRepository extends MongoRepository<CodeAnswer, String>{

}
