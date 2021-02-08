package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.McqQuestionDao;
import org.gatex.entity.McqQuestion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/mcqQuestions")
public class McqQuestionController {

	private final McqQuestionDao mcqQuestionDao;

	public McqQuestionController(McqQuestionDao mcqQuestionDao) {
		this.mcqQuestionDao = mcqQuestionDao;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody McqQuestion mcqQuestion, Principal principal)  {
		mcqQuestion.setUserName(principal.getName());
		String userId= mcqQuestionDao.save(mcqQuestion);
		return new ResponseEntity<>(userId, HttpStatus.OK); 
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id", required=true) String id){
		return new ResponseEntity<>(mcqQuestionDao.delete(id),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<McqQuestion> get(@PathVariable(name="id", required=true) String id){
		return new ResponseEntity<>(mcqQuestionDao.getById(id), HttpStatus.OK);
	}

	@GetMapping("/byUser")
	public ResponseEntity<List<McqQuestion>> getByUser(Principal principal){
		return new ResponseEntity<>(mcqQuestionDao.getAllByUser(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<McqQuestion>> search(
			@RequestParam(name="text", required=false) String text,
			@RequestParam(name="lang", required=false) String lang,
			@RequestParam(name="time", required=false) String time,
			@RequestParam(name="complexity", required=false) String complexity,
			@RequestParam(name="type", required=false) String type,
			@RequestParam(name="tags", required=false) String[] tags,
			Principal principal) {
		return new ResponseEntity<>(mcqQuestionDao.search(text,lang,time,complexity,type,tags, principal.getName()), HttpStatus.OK);
	}


}
