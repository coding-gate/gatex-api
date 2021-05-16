package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.CodeQuestionDao;
import org.gatex.dao.TagDao;
import org.gatex.entity.CodeQuestion;
import org.gatex.entity.Tag;
import org.gatex.model.ValueLabel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/codeQuestions")
public class CodeQuestionController {

	private final CodeQuestionDao codeQuestionDao;
	private final TagDao tagDao;

	public CodeQuestionController(CodeQuestionDao codeQuestionDao, TagDao tagDao) {
		this.codeQuestionDao = codeQuestionDao;
		this.tagDao=tagDao;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody CodeQuestion mcqQuestion, Principal principal)  {
		mcqQuestion.setUserName(principal.getName());
		Tag tag=new Tag();
		tag.setTagFor(mcqQuestion.getLang().getValue());
		Set<ValueLabel> entries = Arrays.stream(mcqQuestion.getTags()).collect(Collectors.toSet());
		tag.setTagEntries(entries);
		tagDao.save(tag);
		String userId= codeQuestionDao.save(mcqQuestion);
		return new ResponseEntity<>(userId, HttpStatus.OK); 
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id") String id){
		return new ResponseEntity<>(codeQuestionDao.delete(id),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<CodeQuestion> get(@PathVariable(name="id") String id){
		return new ResponseEntity<>(codeQuestionDao.getById(id), HttpStatus.OK);
	}

	@GetMapping("/byUser")
	public ResponseEntity<List<CodeQuestion>> getByUser(Principal principal){
		return new ResponseEntity<>(codeQuestionDao.getAllByUser(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<CodeQuestion>> search(
			@RequestParam(name="text", required=false) String text,
			@RequestParam(name="lang", required=false) String lang,
			@RequestParam(name="time", required=false) String time,
			@RequestParam(name="complexity", required=false) String complexity,
			@RequestParam(name="tags", required=false) String[] tags,
			Principal principal) {
		return new ResponseEntity<>(codeQuestionDao.search(text,lang,time,complexity,tags, principal.getName()), HttpStatus.OK);
	}


}
