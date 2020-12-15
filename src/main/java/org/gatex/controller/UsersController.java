package org.gatex.controller;

import lombok.extern.slf4j.Slf4j;
import org.gatex.model.UserDTO;
import org.gatex.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/users")
public class UsersController {

	private final UserService userService;

	public UsersController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public ResponseEntity<String> add(@Valid @RequestBody UserDTO usrDto)  {
		String userId=userService.save(usrDto);
		return new ResponseEntity<>(userId, HttpStatus.OK); 
	}

    @GetMapping("/adminAccountStatus")
	public ResponseEntity<String> getAdminAccountStatus(){
		return new ResponseEntity<>(userService.isAdminExists(), HttpStatus.OK);
	}

}
