package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.TagDao;
import org.gatex.entity.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/tags")
public class TagController {

	private final TagDao tagDao;

	public TagController(TagDao tagDao) {
		this.tagDao=tagDao;
	}

    @GetMapping("/{id}")
	public ResponseEntity<Tag> get(@PathVariable(name="id", required=true) String id){
		return new ResponseEntity<>(tagDao.getById(id), HttpStatus.OK);
	}
}
