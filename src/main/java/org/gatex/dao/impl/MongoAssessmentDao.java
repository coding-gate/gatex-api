package org.gatex.dao.impl;

import com.mongodb.client.result.DeleteResult;
import org.gatex.dao.AssessmentDao;
import org.gatex.entity.Assessment;
import org.gatex.exception.RecordNotFoundException;
import org.gatex.repository.McqTestRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class MongoAssessmentDao implements AssessmentDao {

	private McqTestRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoAssessmentDao(McqTestRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public Assessment getById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Assessment " + id + " not found"));
	}

	@Override
	public String save(Assessment test) {
		test.setCreatedOn(new Date());
		Assessment saveRecord= repository.save(test);
		return saveRecord.getId();
	}

	public String lockTest(String id) {
		Assessment assessment=repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("Assessment " + id + " not found"));
		assessment.setCreatedOn(new Date());
		assessment.setLocked(true);
		Assessment saveRecord= repository.save(assessment);
		return saveRecord.getId();
	}

	public long delete(String id) {
		Assessment assessment =new Assessment();
		assessment.setId(id);
		DeleteResult deleteResult = mongoTemplate.remove(assessment);
		return deleteResult.getDeletedCount();
	}





	@Override
	public List<Assessment> search(String title, String language, String timeLimit, Boolean isLocked, String type, String userName) {
		Query query = new Query();

		query.addCriteria(Criteria.where("userName").is(userName));

		if(title!=null){
			query.addCriteria(Criteria.where("title").regex(".*"+title+"*."));
		}

		if(type!=null){
			query.addCriteria(Criteria.where("type").regex(".*"+type+"*."));
		}

		if(language!=null){
			query.addCriteria(Criteria.where("language.value").is(language));
		}
		if(timeLimit!=null){
			query.addCriteria(Criteria.where("timeLimit").is(timeLimit));
		}

		if(isLocked!=null){
			query.addCriteria(Criteria.where("locked").is(isLocked));
		}

		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);

		return mongoTemplate.find(query, Assessment.class);
	}


}
