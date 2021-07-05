package org.gatex.service;

import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.McqQuestionDao;
import org.gatex.entity.McqQuestion;
import org.gatex.model.CodeExamAnswer;
import org.gatex.model.McqExamAnswer;
import org.gatex.model.Result;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ScoreService {
    private final McqQuestionDao mcqQuestionDao;

    public ScoreService(McqQuestionDao mcqQuestionDao) {
        this.mcqQuestionDao = mcqQuestionDao;
    }

    private List getCorrectAnswerList(String id){
        List ans=new ArrayList();
        McqQuestion question = mcqQuestionDao.getById(id);
        List<Object[]> options = question.getOptions();
        for(int i=0; i<options.size(); i++){
            if((boolean)options.get(i)[1]){
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

    public double getMcqScore(List<McqExamAnswer> mcqExamAnswers){
        Double allScore = mcqExamAnswers.stream()
                .map(e -> calculateEachScore(getCorrectAnswerList(e.getQuestionId()), e.getAnswerOption()))
                .reduce(0.0, Double::sum);
        return allScore/mcqExamAnswers.size();
    }

    private double getScore(Result r){
        double score = 0;
        if(r.getTotal()>0){
            score= r.getPassed()/r.getTotal();
        }
        return score;
    }


    public double getCodeScore(List<CodeExamAnswer> codeExamAnswers){

        return codeExamAnswers.stream()
                .map(CodeExamAnswer::getResult).
                map(this::getScore)
                .reduce(0.0, Double::sum);
    }

}
