package org.gatex.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CodeExamAnswer {
    String questionId;
    String answerCode;
    Result result;
}
