package org.gatex.repository;

import org.gatex.entity.McqAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface McqAnswerRepository extends MongoRepository<McqAnswer, String>{

}
