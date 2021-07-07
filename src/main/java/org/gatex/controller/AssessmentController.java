package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.AssessmentDao;
import org.gatex.entity.Assessment;
import org.gatex.model.McqExamQuestion;
import org.gatex.service.McqTestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/assessment")
public class AssessmentController {

	private final AssessmentDao assessmentDao;

	private final McqTestService mcqTestService;

	public AssessmentController(AssessmentDao assessmentDao, McqTestService mcqTestService) {
		this.assessmentDao = assessmentDao;
		this.mcqTestService = mcqTestService;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody Assessment assessment, Principal principal)  {
		assessment.setUserName(principal.getName());
		String userId= assessmentDao.save(assessment);
		return new ResponseEntity<>(userId, HttpStatus.OK); 
	}

	@PutMapping("lock/{id}")
	public ResponseEntity<String> lockTest(@PathVariable(name="id") String id){
		return new ResponseEntity<>(assessmentDao.lockTest(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id") String id){
		return new ResponseEntity<>(assessmentDao.delete(id),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<Assessment> get(@PathVariable(name="id") String id){
		return new ResponseEntity<>(assessmentDao.getById(id), HttpStatus.OK);
	}

	@GetMapping("questions/{id}")
	public ResponseEntity<List<McqExamQuestion>> getQuestions(@PathVariable(name="id") String id){
		return new ResponseEntity<>(mcqTestService.getQuestions(id), HttpStatus.OK);
	}


	@GetMapping("/codeUnlocked")
	public ResponseEntity<List<Assessment>> codeAssessment(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="language", required=false) String language,
			@RequestParam(name="timeLimit", required=false) String timeLimit,
			Principal principal) {
		return new ResponseEntity<>(assessmentDao.search(title,language,timeLimit, false, "code", principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/mcqUnlocked")
	public ResponseEntity<List<Assessment>> mcqAssessment(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="language", required=false) String language,
			@RequestParam(name="timeLimit", required=false) String timeLimit,
			Principal principal) {
		return new ResponseEntity<>(assessmentDao.search(title,language,timeLimit, false, "mcq", principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/codeLocked")
	public ResponseEntity<List<Assessment>> codeLockAssessment(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="language", required=false) String language,
			@RequestParam(name="timeLimit", required=false) String timeLimit,
			Principal principal) {
		return new ResponseEntity<>(assessmentDao.search(title,language,timeLimit, true, "code", principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/mcqLocked")
	public ResponseEntity<List<Assessment>> mcqLockAssessment(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="language", required=false) String language,
			@RequestParam(name="timeLimit", required=false) String timeLimit,
			Principal principal) {
		return new ResponseEntity<>(assessmentDao.search(title,language,timeLimit, true, "mcq", principal.getName()), HttpStatus.OK);
	}

}
