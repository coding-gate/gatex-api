package org.gatex.controller;

import org.gatex.model.UserDTO;
import org.gatex.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseEntity<String> add(@Valid @RequestBody UserDTO usrDto) {
		String userId=userService.save(usrDto);
		return new ResponseEntity<>(userId, HttpStatus.OK); 
	}

}
