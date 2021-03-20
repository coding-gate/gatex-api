package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.McqTestDao;
import org.gatex.entity.McqTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/mcqTest")
public class McqTestController {

	private final McqTestDao mcqTestDao;

	public McqTestController(McqTestDao mcqTestDao) {
		this.mcqTestDao = mcqTestDao;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody McqTest mcqTest, Principal principal)  {
		mcqTest.setUserName(principal.getName());
		String userId= mcqTestDao.save(mcqTest);
		return new ResponseEntity<>(userId, HttpStatus.OK); 
	}

	@PutMapping("lock/{id}")
	public ResponseEntity<String> lockTest(@PathVariable(name="id") String id){
		return new ResponseEntity<>(mcqTestDao.lockTest(id), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Long> delete(@PathVariable(name="id") String id){
		return new ResponseEntity<>(mcqTestDao.delete(id),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<McqTest> get(@PathVariable(name="id") String id){
		return new ResponseEntity<>(mcqTestDao.getById(id), HttpStatus.OK);
	}

	@GetMapping("/byUser")
	public ResponseEntity<List<McqTest>> getByUser(Principal principal){
		return new ResponseEntity<>(mcqTestDao.getAllByUser(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/byUser/allLocked")
	public ResponseEntity<List<McqTest>> getAllLockedByUser(Principal principal){
		return new ResponseEntity<>(mcqTestDao.getAllLockByUser(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/byUser/allUnlocked")
	public ResponseEntity<List<McqTest>> getAllUnlockedByUser(Principal principal){
		return new ResponseEntity<>(mcqTestDao.getAllUnlockByUser(principal.getName()), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<McqTest>> search(
			@RequestParam(name="title", required=false) String title,
			@RequestParam(name="language", required=false) String language,
			@RequestParam(name="timeLimit", required=false) String timeLimit,
			@RequestParam(name="isLocked", required=false) Boolean isLocked,
			Principal principal) {
		return new ResponseEntity<>(mcqTestDao.search(title,language,timeLimit, isLocked, principal.getName()), HttpStatus.OK);
	}


}
