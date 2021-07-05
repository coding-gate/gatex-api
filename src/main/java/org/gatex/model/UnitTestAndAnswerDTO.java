package org.gatex.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class UnitTestAndAnswerDTO {
	String answerCode;
	String unitTestCode;
}
