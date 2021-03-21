package org.gatex.service;

import org.gatex.dao.McqQuestionDao;
import org.gatex.dao.McqTestDao;
import org.gatex.entity.McqQuestion;
import org.gatex.entity.McqTest;
import org.gatex.model.McqExamQuestion;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class McqTestService {
    private final McqTestDao mcqTestDao;
    private final McqQuestionDao mcqQuestionDao;

    public McqTestService(McqTestDao mcqTestDao, McqQuestionDao mcqQuestionDao) {
        this.mcqTestDao = mcqTestDao;
        this.mcqQuestionDao = mcqQuestionDao;
    }



    public List<McqExamQuestion> getQuestions(String testId) {

        List<McqExamQuestion> examQuestion=new ArrayList<>();

        McqTest test = mcqTestDao.getById(testId);
        List<McqQuestion> questions = test.getSelectedQuestions().stream().map(mcqQuestionDao::getById).collect(Collectors.toList());

        for (McqQuestion question : questions) {
            McqExamQuestion q = new McqExamQuestion();
            BeanUtils.copyProperties(question, q);
            List<String> options = question.getOptions().stream().map(e -> e[0]).collect(Collectors.toList());
            q.setOptionTexts(options);
            examQuestion.add(q);
        }
       return examQuestion;
    }

}
