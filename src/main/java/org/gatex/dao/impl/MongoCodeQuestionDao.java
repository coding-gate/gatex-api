package org.gatex.dao.impl;

import com.mongodb.client.result.DeleteResult;
import org.gatex.dao.CodeQuestionDao;
import org.gatex.entity.CodeQuestion;
import org.gatex.exception.RecordNotFoundException;
import org.gatex.model.ValueLabel;
import org.gatex.repository.CodeQuestionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component

public class MongoCodeQuestionDao implements CodeQuestionDao {

	private CodeQuestionRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoCodeQuestionDao(CodeQuestionRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public CodeQuestion getById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("McqQuestion " + id + " not found"));
	}

	@Override
	public String save(CodeQuestion question) {
		ValueLabel[] tags=question.getTags();
		List<String> tagValues = Arrays.stream(tags).map(ValueLabel::getValue).collect(Collectors.toList());
		question.setTagValues(tagValues);
		question.setCreatedOn(new Date());
		CodeQuestion saveRecord= repository.save(question);
		return saveRecord.getId();
	}

	public long delete(String id) {
		CodeQuestion mcqQuestion=new CodeQuestion();
		mcqQuestion.setId(id);
		DeleteResult deleteResult = mongoTemplate.remove(mcqQuestion);
		return deleteResult.getDeletedCount();
	}

	public List<CodeQuestion> getAllByUser(String userName){
		Query query = new Query();
		query.addCriteria(Criteria.where("userName").is(userName));
		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);
		return mongoTemplate.find(query, CodeQuestion.class);
	}

	@Override
	public List<CodeQuestion> search(String text, String lang, String time, String complexity, String[] tags, String userName) {
		Query query = new Query();

		query.addCriteria(Criteria.where("userName").is(userName));

		if(text!=null){
			query.addCriteria(Criteria.where("text").regex(".*"+text+"*."));
		}

		if(lang!=null){
			query.addCriteria(Criteria.where("lang.value").is(lang));
		}
		if(time!=null){
			query.addCriteria(Criteria.where("time.value").is(time));
		}
		if(complexity!=null) {
			query.addCriteria(Criteria.where("complexity.value").is(complexity));
		}
		if(tags!=null){
			query.addCriteria(Criteria.where("tagValues").all(tags));
		}

		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);

		return mongoTemplate.find(query, CodeQuestion.class);
	}

}
