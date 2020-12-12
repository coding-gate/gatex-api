package org.gatex.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {
	@NotEmpty(message="User Id can not be empty")
	String id;
	@NotEmpty(message="Password can not be empty")
	@Size(min = 6, message = "Password should be more than 6 char long")
	String password;
	@NotEmpty(message="Name can not be empty")
	String name;
	@NotEmpty(message="Email Id can not be empty")
	String email;
	@NotEmpty(message="Roles can not be empty")
	String[] roles;
}
