package org.gatex.service;

import org.gatex.dao.McqQuestionDao;
import org.gatex.entity.McqQuestion;
import org.gatex.model.McqExamAnswer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScoreService {
    private final McqQuestionDao mcqQuestionDao;

    public ScoreService(McqQuestionDao mcqQuestionDao) {
        this.mcqQuestionDao = mcqQuestionDao;
    }

    private List getCorrectAnswerList(String id){
        List ans=new ArrayList();
        McqQuestion question = mcqQuestionDao.getById(id);
        List<String[]> options = question.getOptions();
        for(int i=0; i<options.size(); i++){
            if(Boolean.parseBoolean(options.get(i)[1])){
                ans.add(i);
            }
        }
        return ans;
    }

    private double calculateEachScore(List correctAns, int[] userAnswer){
        int userAnswerLength = userAnswer.length;
        int correctCount = 0;
        for(int i=0; i<userAnswerLength; i++){
            if(correctAns.contains(userAnswer[i])){
                correctCount++;
            }
        }
        return correctCount/userAnswerLength;
    }

    private double calculate(String id){
        McqQuestion question = mcqQuestionDao.getById(id);
        question.getOptions().stream().map(e->Boolean.parseBoolean(e[1])).filter(e->e.booleanValue()).collect(Collectors.toList());

        return 0;
    }

    public double getScore(List<McqExamAnswer> mcqExamAnswers){
        Double allScore = mcqExamAnswers.stream()
                .map(e -> calculateEachScore(getCorrectAnswerList(e.getQuestionId()), e.getAnswerOption()))
                .reduce(0.0, Double::sum);
        return allScore/mcqExamAnswers.size();
    }

}
