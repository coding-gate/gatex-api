package org.gatex.dao;

import org.gatex.entity.Assessment;

import java.util.List;

public interface AssessmentDao {
    Assessment getById(String id);
    String save(Assessment question);
    String lockTest(String id);
    long delete(String id);
    List<Assessment> search(String title, String language, String timeLimit, Boolean isLocked, String type, String userName);
}
