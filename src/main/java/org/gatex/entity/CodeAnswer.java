package org.gatex.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gatex.model.CodeExamAnswer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="CodeAnswer")
public class CodeAnswer {
    @Id
    String id;
    String testId;
    String userName;
    Double score;
    List<CodeExamAnswer> codeExamAnswers;
    Date createdOn;
}
