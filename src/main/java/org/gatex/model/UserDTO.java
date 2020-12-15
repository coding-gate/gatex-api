package org.gatex.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDTO {
	@NotEmpty(message="User Id is mandatory")
	String id;
	@NotEmpty(message="Password is mandatory")
	String password;
	@NotEmpty(message="Name is mandatory")
	String name;
	@NotEmpty(message="Email is mandatory")
	String email;
	String key;
	@NotEmpty(message="Type is mandatory")
	String type;
}
