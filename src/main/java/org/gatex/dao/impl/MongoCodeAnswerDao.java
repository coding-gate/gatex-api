package org.gatex.dao.impl;

import com.mongodb.client.result.DeleteResult;
import org.gatex.dao.CodeAnswerDao;
import org.gatex.entity.CodeAnswer;
import org.gatex.exception.RecordNotFoundException;
import org.gatex.repository.CodeAnswerRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MongoCodeAnswerDao implements CodeAnswerDao {

	private CodeAnswerRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoCodeAnswerDao(CodeAnswerRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public CodeAnswer getById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("CodeAnswer " + id + " not found"));
	}

	@Override
	public String save(CodeAnswer ans) {
		ans.setCreatedOn(new Date());
		CodeAnswer saveRecord= repository.save(ans);
		return saveRecord.getId();
	}

	@Override
	public List<CodeAnswer> getAllByTest(String testId) {
		Query query = new Query();
		if(testId!=null){
			query.addCriteria(Criteria.where("testId").is(testId));
		}
		Sort sorting = Sort.by("score").descending();
		query.with(sorting);

		return mongoTemplate.find(query, CodeAnswer.class);
	}

	public long delete(String id) {
		CodeAnswer codeAnswer=new CodeAnswer();
		codeAnswer.setId(id);
		DeleteResult deleteResult = mongoTemplate.remove(codeAnswer);
		return deleteResult.getDeletedCount();
	}


}
