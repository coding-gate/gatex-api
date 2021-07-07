package org.gatex.dao;

import org.gatex.entity.McqAnswer;

import java.util.List;

public interface McqAnswerDao {
    McqAnswer getById(String id);
    String save(McqAnswer mcqAnswer);
    List<McqAnswer> getAllByTest(String testId);
    long delete(String id);
}
