package org.gatex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.gatex.dao.CodeQuestionDao;
import org.gatex.entity.CodeQuestion;
import org.gatex.model.CmdOutput;
import org.gatex.model.CodeExamAnswer;
import org.gatex.model.Result;
import org.gatex.model.UnitTestAndAnswerDTO;
import org.gatex.util.UnittestServiceUtil;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UnittestService {

    private final CodeQuestionDao codeQuestionDao;
    private final Environment env;
    private final ObjectMapper objectMapper;

    public UnittestService(CodeQuestionDao codeQuestionDao, Environment env, ObjectMapper objectMapper){
        this.codeQuestionDao=codeQuestionDao;
        this.env = env;
        this.objectMapper=objectMapper;
    }


    public ResponseEntity<CmdOutput> executeTest(CodeExamAnswer codeExamAnswer){
        CodeQuestion codeQuestion = codeQuestionDao.getById(codeExamAnswer.getQuestionId());

        UnitTestAndAnswerDTO unitTestAndAns = new UnitTestAndAnswerDTO();
        unitTestAndAns.setAnswerCode(codeExamAnswer.getAnswerCode());
        unitTestAndAns.setUnitTestCode(codeQuestion.getUnittestCode());

        return UnittestServiceUtil.executeUnittest(env, codeQuestion.getLang().getValue(), unitTestAndAns);
    }

    private Result calculateEachResult(CmdOutput cmdOutPut){
        Result result=new Result();
        log.info("Status {}", cmdOutPut.getStatus());

        if(cmdOutPut.getStatus()==0) {
            String testOutPut = cmdOutPut.getOutputMsg();
            log.info("TestOutPut:::: {}", testOutPut);
            try {
                List<Object[]> testOutPutObj = objectMapper.readValue(testOutPut, new TypeReference<List<Object[]>>() {
                });
                log.info("testOutPutObj.size()={}", testOutPutObj.size());
                int passed = (int) testOutPutObj.stream().filter(e -> (boolean) e[1]).count();
                int total = testOutPutObj.size();

                result.setPassed(passed);
                result.setTotal(total);
            }catch(JsonProcessingException e){
                log.info(e.getMessage());
            }

        }
        return result;
    }


    public void calculateResult(List<CodeExamAnswer> codeExamAnswers) {
        for (CodeExamAnswer codeExamAnswer : codeExamAnswers) {
            ResponseEntity<CmdOutput> CmdOutput = executeTest(codeExamAnswer);
            Result r=calculateEachResult(CmdOutput.getBody());
            codeExamAnswer.setResult(r);
        }
    }


}
