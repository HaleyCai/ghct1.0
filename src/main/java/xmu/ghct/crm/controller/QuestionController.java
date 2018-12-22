package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.entity.Question;
import xmu.ghct.crm.service.QuestionService;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;

@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
                    method = RequestMethod.GET)
    public List<QuestionVO> getAllQuestion(@RequestBody Map<String,Object> inMap){
        return questionService.getAllQuestion(
                (BigInteger)inMap.get("seminarId"),
                (BigInteger)inMap.get("klassId"));
    }

    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
                    method = RequestMethod.POST)
    public boolean postQuestion(@RequestBody Map<String,Object> inMap)
    {
        int flag= questionService.postQuestion(inMap);
        if(flag>0)return true;
        else return false;
    }

    @RequestMapping(value="/question/{questionId}",method = RequestMethod.PUT)
    public boolean updateQuestionScoreByQuestionId(@RequestBody Map<String,Object> inMap){
        return questionService.updateQuestionScoreByQuestionId(
                (BigInteger)inMap.get("questionId"));
    }
}
