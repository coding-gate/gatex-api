package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.CodeAnswerDao;
import org.gatex.entity.CodeAnswer;
import org.gatex.model.CmdOutput;
import org.gatex.model.CodeExamAnswer;
import org.gatex.service.ScoreService;
import org.gatex.service.UnittestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/codeAnswer")
public class CodeAnswerController {

	private final CodeAnswerDao codeAnswerDao;
	private final ScoreService scoreService;
	private final UnittestService unittestService;

	public CodeAnswerController(CodeAnswerDao codeAnswerDao, ScoreService scoreService, UnittestService unittestService) {
		this.codeAnswerDao = codeAnswerDao;
		this.scoreService = scoreService;
		this.unittestService=unittestService;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody CodeAnswer codeAnswer, Principal principal)  {
		codeAnswer.setUserName(principal.getName());
		List<CodeExamAnswer> codeExamAnswers = codeAnswer.getCodeExamAnswers();
		unittestService.calculateResult(codeExamAnswers);
		double score = scoreService.getCodeScore(codeExamAnswers);
		codeAnswer.setScore(score);
		String id= codeAnswerDao.save(codeAnswer);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}

	@PostMapping("/unittest")
	public ResponseEntity<CmdOutput> unittest(@Valid @RequestBody CodeExamAnswer codeExamAnswer)  {
		return unittestService.executeTest(codeExamAnswer);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id") String id){
		return new ResponseEntity<>(codeAnswerDao.delete(id),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<CodeAnswer> get(@PathVariable(name="id") String id){
		return new ResponseEntity<>(codeAnswerDao.getById(id), HttpStatus.OK);
	}

	@GetMapping("/assessment/{id}")
	public ResponseEntity<List<CodeAnswer>> getAllByTest(@PathVariable(name="id") String id) {
		return new ResponseEntity<>(codeAnswerDao.getAllByTest(id), HttpStatus.OK);
	}


}
