package xmu.ghct.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xmu.ghct.crm.VO.QuestionListVO;
import xmu.ghct.crm.VO.QuestionVO;
import xmu.ghct.crm.exception.NotFoundException;
import xmu.ghct.crm.service.QuestionService;

import java.math.BigInteger;
import java.util.Map;
import java.util.List;
@CrossOrigin
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService;

    /**
     *前端传入klassSeminarId和attendanceId,教师提问界面右边显示所有提问学生
     * @param inMap
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
            method = RequestMethod.GET)
    public List<QuestionListVO> getAllQuestion(@RequestBody Map<String,Object> inMap){
        return questionService.getAllQuestion(
                new BigInteger(inMap.get("klassSeminarId").toString()),
                new BigInteger(inMap.get("attendanceId").toString()));
    }

    /**
     * 教师点击下个提问时，修改当前提问为已抽到
     * @param inMap
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
            method = RequestMethod.PUT)
    public boolean updateQuestionSelected(@RequestBody Map<String,Object> inMap){
        return questionService.updateQuestionSelected(
                new BigInteger(inMap.get("questionId").toString()));
    }

    /**
     * 学生提问界面，被抽取到提问，展示提问人信息
     */
    @RequestMapping(value="/question/{questionId}",method = RequestMethod.GET)
    public QuestionListVO getOneQuestion(@RequestBody Map<String,Object> inMap){
        return questionService.getOneQuestion(
                new BigInteger(inMap.get("questionId").toString()));
    }

    /**
     * 前端传入studentId，klassSeminarId和attendanceId，发布提问
     * @param inMap
     * @return
     */
    @RequestMapping(value="/seminar/{seminarId}/klass/{klassId}/question",
            method = RequestMethod.POST)
    public boolean postQuestion(@RequestBody Map<String,Object> inMap)
    {
        return questionService.postQuestion(
                new BigInteger(inMap.get("studentId").toString()),
                new BigInteger(inMap.get("klassSeminarId").toString()),
                new BigInteger(inMap.get("attendanceId").toString()));

    }


    /**
     * 给提问打分
     * @param inMap
     * @return
     */
    @RequestMapping(value="/question/{questionId}",method = RequestMethod.PUT)
    public boolean updateQuestionScore(@RequestBody Map<String,Object> inMap) throws NotFoundException {
        return questionService.updateQuestionScore(
                new BigInteger(inMap.get("questionId").toString()),
                new BigInteger(inMap.get("klassSeminarId").toString()),
                new Double(inMap.get("questionScore").toString()));
    }

}