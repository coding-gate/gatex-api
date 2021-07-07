package org.gatex.service;

import org.gatex.dao.McqQuestionDao;
import org.gatex.dao.AssessmentDao;
import org.gatex.entity.McqQuestion;
import org.gatex.entity.Assessment;
import org.gatex.model.McqExamQuestion;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class McqTestService {
    private final AssessmentDao assessmentDao;
    private final McqQuestionDao mcqQuestionDao;

    public McqTestService(AssessmentDao assessmentDao, McqQuestionDao mcqQuestionDao) {
        this.assessmentDao = assessmentDao;
        this.mcqQuestionDao = mcqQuestionDao;
    }



    public List<McqExamQuestion> getQuestions(String testId) {

        List<McqExamQuestion> examQuestion=new ArrayList<>();

        Assessment test = assessmentDao.getById(testId);
        List<McqQuestion> questions = test.getSelectedQuestions().stream().map(mcqQuestionDao::getById).collect(Collectors.toList());

        for (McqQuestion question : questions) {
            McqExamQuestion q = new McqExamQuestion();
            BeanUtils.copyProperties(question, q);
            List<Object> options = question.getOptions().stream().map(e -> e[0]).collect(Collectors.toList());
            q.setOptionTexts(options);
            examQuestion.add(q);
        }
       return examQuestion;
    }

}
