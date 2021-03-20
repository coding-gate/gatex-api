package org.gatex.dao.impl;

import com.mongodb.client.result.DeleteResult;
import org.gatex.dao.McqTestDao;
import org.gatex.entity.McqTest;
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
public class MongoMcqTestDao implements McqTestDao {

	private McqTestRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoMcqTestDao(McqTestRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public McqTest getById(String id) {
		return repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("McqTest " + id + " not found"));
	}

	@Override
	public String save(McqTest test) {
		test.setCreatedOn(new Date());
		McqTest saveRecord= repository.save(test);
		return saveRecord.getId();
	}

	public String lockTest(String id) {
		McqTest test=repository.findById(id)
				.orElseThrow(() -> new RecordNotFoundException("McqTest " + id + " not found"));
		test.setCreatedOn(new Date());
		test.setLocked(true);
		McqTest saveRecord= repository.save(test);
		return saveRecord.getId();
	}

	public long delete(String id) {
		McqTest mcqTest=new McqTest();
		mcqTest.setId(id);
		DeleteResult deleteResult = mongoTemplate.remove(mcqTest);
		return deleteResult.getDeletedCount();
	}

	public List<McqTest> getAllByUser(String userName){
		Query query = new Query();
		query.addCriteria(Criteria.where("userName").is(userName));
		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);
		return mongoTemplate.find(query,McqTest.class);
	}

	@Override
	public List<McqTest> search(String title, String language, String timeLimit, Boolean isLocked, String userName) {
		Query query = new Query();

		query.addCriteria(Criteria.where("userName").is(userName));

		if(title!=null){
			query.addCriteria(Criteria.where("title").regex(".*"+title+"*."));
		}
		if(language!=null){
			query.addCriteria(Criteria.where("language.value").is(language));
		}
		if(timeLimit!=null){
			query.addCriteria(Criteria.where("timeLimit").is(timeLimit));
		}

		if(isLocked!=null){
			query.addCriteria(Criteria.where("isLocked").is(isLocked));
		}

		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);

		return mongoTemplate.find(query,McqTest.class);
	}

	public List<McqTest> getAllUnlockByUser(String userName){
		Query query = new Query();
		query.addCriteria(Criteria.where("userName").is(userName));
		query.addCriteria(Criteria.where("isLocked").is(false));
		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);
		return mongoTemplate.find(query,McqTest.class);
	}

	public List<McqTest> getAllLockByUser(String userName){
		Query query = new Query();
		query.addCriteria(Criteria.where("userName").is(userName));
		query.addCriteria(Criteria.where("isLocked").is(true));
		Sort sorting = Sort.by("createdOn").descending();
		query.with(sorting);
		return mongoTemplate.find(query,McqTest.class);
	}

}
