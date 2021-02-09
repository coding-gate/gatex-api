package org.gatex.dao.impl;

import org.gatex.dao.TagDao;
import org.gatex.entity.Tag;
import org.gatex.exception.RecordNotFoundException;
import org.gatex.model.ValueLabel;
import org.gatex.repository.TagRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class MongoTagDao implements TagDao {

	private TagRepository repository;
	private MongoTemplate mongoTemplate;

	public MongoTagDao(TagRepository repository, MongoTemplate mongoTemplate) {
		this.repository = repository;
		this.mongoTemplate=mongoTemplate;
	}

	@Override
	public Tag getById(String Id) {
		return repository.findById(Id)
				.orElse(new Tag());
	}

	@Override
	public void save(Tag tag) {
		Optional<Tag> fetchTag=repository.findById(tag.getTagFor());
		if(fetchTag.isPresent()){
			Set<ValueLabel> entries = fetchTag.get().getTagEntries();
			int oldSize = entries.size();
			entries.addAll(tag.getTagEntries());
			int newSize = entries.size();
			if(newSize>oldSize){
				tag.setTagEntries(entries);
				repository.save(tag);
			}
		}else{
			repository.save(tag);
		}
	}


}
