package org.gatex.dao;

import org.gatex.entity.McqTest;

import java.util.List;

public interface McqTestDao {
    McqTest getById(String id);
    String save(McqTest question);
    String lockTest(String id);
    List<McqTest> getAllByUser(String userName);
    List<McqTest> getAllUnlockByUser(String userName);
    List<McqTest> getAllLockByUser(String userName);
    long delete(String id);
    List<McqTest> search(String title, String language, String timeLimit, Boolean isLocked, String type, String userName);
}
