package org.gatex.dao;

import org.gatex.entity.McqQuestion;

import java.util.List;

public interface McqQuestionDao {
    McqQuestion getById(String id);
    String save(McqQuestion question);
    List<McqQuestion> getAllByUser(String userName);
    long delete(String id);
    List<McqQuestion> search(String text, String lang, String time, String complexity, String type, String[] tags, String userName);
}
