package org.gatex.dao.impl;

import com.mongodb.client.result.DeleteResult;
import org.gatex.dao.McqAnswerDao;
import org.gatex.entity.McqAnswer;
import org.gatex.exception.RecordNotFoundException;
import org.gatex.repository.McqAnswerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MongoMcqAnswerDao implements McqAnswerDao {

	private McqAnswerRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoMcqAnswerDao(McqAnswerRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public McqAnswer getById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("McqAnswer " + id + " not found"));
	}

	@Override
	public String save(McqAnswer ans) {
		ans.setCreatedOn(new Date());
		McqAnswer saveRecord= repository.save(ans);
		return saveRecord.getId();
	}

	@Override
	public List<McqAnswer> getAllByTest(String testId) {
		Query query = new Query();
		if(testId!=null){
			query.addCriteria(Criteria.where("testId").is(testId));
		}
		Sort sorting = Sort.by("score").descending();
		query.with(sorting);

		return mongoTemplate.find(query, McqAnswer.class);
	}

	public long delete(String id) {
		McqAnswer mcqAnswer=new McqAnswer();
		mcqAnswer.setId(id);
		DeleteResult deleteResult = mongoTemplate.remove(mcqAnswer);
		return deleteResult.getDeletedCount();
	}


}
