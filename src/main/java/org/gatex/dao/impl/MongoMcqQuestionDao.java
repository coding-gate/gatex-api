package org.gatex.dao.impl;

import com.mongodb.client.result.DeleteResult;
import org.gatex.dao.McqQuestionDao;
import org.gatex.entity.McqQuestion;
import org.gatex.exception.RecordNotFoundException;
import org.gatex.model.ValueLabel;
import org.gatex.repository.McqQuestionRepository;
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
public class MongoMcqQuestionDao implements McqQuestionDao {

	private McqQuestionRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoMcqQuestionDao(McqQuestionRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public McqQuestion getById(String mcqQuestionId) {
		return repository.findById(mcqQuestionId)
				.orElseThrow(() -> new RecordNotFoundException("McqQuestion " + mcqQuestionId + " not found"));
	}

	@Override
	public String save(McqQuestion question) {
		ValueLabel[] tags=question.getTags();
		List<String> tagValues = Arrays.stream(tags).map(ValueLabel::getValue).collect(Collectors.toList());
		question.setTagValues(tagValues);
		question.setCreatedOn(new Date());
		McqQuestion saveRecord= repository.save(question);
		return saveRecord.getId();
	}

	public long delete(String mcqQuestionId) {
		McqQuestion mcqQuestion=new McqQuestion();
		mcqQuestion.setId(mcqQuestionId);
		DeleteResult deleteResult = mongoTemplate.remove(mcqQuestion);
		return deleteResult.getDeletedCount();
	}

	public List<McqQuestion> getAllByUser(String userName){
		Query query = new Query();
		query.addCriteria(Criteria.where("userName").is(userName));
		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);
		return mongoTemplate.find(query,McqQuestion.class);
	}

	@Override
	public List<McqQuestion> search(String text, String lang, String time, String complexity, String type, String[] tags, String userName) {
		Query query = new Query();

		query.addCriteria(Criteria.where("userName").is(userName));

		if(text!=null){
			query.addCriteria(Criteria.where("text").regex(".*"+text+"*."));
		}
		if(type!=null){
			query.addCriteria(Criteria.where("type").is(type));
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

		return mongoTemplate.find(query,McqQuestion.class);
	}

}
