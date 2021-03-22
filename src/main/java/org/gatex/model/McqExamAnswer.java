package org.gatex.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class McqExamAnswer {
    @Id
    String questionId;
    int[] answerOption;
}
