package org.gatex.dao;

import org.gatex.entity.CodeAnswer;

import java.util.List;

public interface CodeAnswerDao {
    CodeAnswer getById(String id);
    String save(CodeAnswer mcqAnswer);
    List<CodeAnswer> getAllByTest(String testId);
    long delete(String id);
}
