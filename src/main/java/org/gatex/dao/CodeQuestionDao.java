package org.gatex.dao;

import org.gatex.entity.CodeQuestion;

import java.util.List;

public interface CodeQuestionDao {
    CodeQuestion getById(String id);
    String save(CodeQuestion question);
    List<CodeQuestion> getAllByUser(String userName);
    long delete(String id);
    List<CodeQuestion> search(String text, String lang, String time, String complexity, String[] tags, String userName);
}
