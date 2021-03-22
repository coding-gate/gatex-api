package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.McqAnswerDao;
import org.gatex.entity.McqAnswer;
import org.gatex.model.McqExamAnswer;
import org.gatex.service.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/mcqAnswer")
public class McqAnswerController {

	private final McqAnswerDao mcqAnswerDao;
	private final ScoreService scoreService;

	public McqAnswerController(McqAnswerDao mcqAnswerDao, ScoreService scoreService) {
		this.mcqAnswerDao = mcqAnswerDao;
		this.scoreService = scoreService;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody McqAnswer mcqAnswer, Principal principal)  {
		mcqAnswer.setUserName(principal.getName());
		List<McqExamAnswer> mcqExamAnswers = mcqAnswer.getMcqExamAnswers();
		double score = scoreService.getScore(mcqExamAnswers);
		mcqAnswer.setScore(score);
		String id= mcqAnswerDao.save(mcqAnswer);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id") String id){
		return new ResponseEntity<>(mcqAnswerDao.delete(id),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<McqAnswer> get(@PathVariable(name="id") String id){
		return new ResponseEntity<>(mcqAnswerDao.getById(id), HttpStatus.OK);
	}

	@GetMapping("/test/{id}")
	public ResponseEntity<List<McqAnswer>> getAllByTest(@PathVariable(name="id") String id) {
		return new ResponseEntity<>(mcqAnswerDao.getAllByTest(id), HttpStatus.OK);
	}


}
